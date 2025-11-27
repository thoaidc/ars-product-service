package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.dto.request.product.CreateProductRequest;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.request.product.UpdateProductRequest;
import com.ars.productservice.dto.response.category.CategoryDTO;
import com.ars.productservice.dto.response.product.ProductDTO;
import com.ars.productservice.dto.response.product.ProductGroupDTO;
import com.ars.productservice.dto.response.product.ProductOptionDTO;
import com.ars.productservice.entity.Category;
import com.ars.productservice.entity.Product;
import com.ars.productservice.entity.ProductCategory;
import com.ars.productservice.entity.ProductGroup;
import com.ars.productservice.entity.ProductImage;
import com.ars.productservice.entity.ProductOption;
import com.ars.productservice.entity.ProductOptionValue;
import com.ars.productservice.entity.ProductProductGroup;
import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.repository.ProductCategoryRepository;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.repository.ProductProductGroupRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.service.ProductService;

import com.dct.config.common.FileUtils;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ProductServiceImpl";
    private final FileUtils fileUtils = new FileUtils();
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductGroupRepository productGroupRepository;
    private final ProductProductGroupRepository productProductGroupRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductGroupRepository productGroupRepository,
                              ProductProductGroupRepository productProductGroupRepository,
                              ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productGroupRepository = productGroupRepository;
        this.productProductGroupRepository = productProductGroupRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.fileUtils.setPrefixPath(ProductConstants.Upload.PREFIX);
        this.fileUtils.setUploadDirectory(ProductConstants.Upload.LOCATION);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO getAllWithPaging(SearchProductRequest request) {
        Page<ProductDTO> productPage = productRepository.getAllWithPaging(request);
        return BaseResponseDTO.builder().total(productPage.getTotalElements()).ok(productPage.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO getDetail(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Product not exists");
        }

        Product product = productOptional.get();
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO, "images", "options", "categories", "productGroups");
        productDTO.setImages(product.getImages().stream().map(ProductImage::getImage).toList());

        List<ProductOptionDTO> productOptionDTOS = product.getOptions().stream().map(productOption -> {
            ProductOptionDTO productOptionDTO = new ProductOptionDTO();
            BeanUtils.copyProperties(productOption, productOptionDTO);
            productOptionDTO.setProductId(productId);
            return productOptionDTO;
        }).toList();

        List<CategoryDTO> categoryDTOS = product.getCategories().stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);
            return categoryDTO;
        }).toList();

        List<ProductGroupDTO> productGroupDTOS = product.getProductGroups().stream().map(productGroup -> {
            ProductGroupDTO productGroupDTO = new ProductGroupDTO();
            BeanUtils.copyProperties(productGroup, productGroupDTO);
            return productGroupDTO;
        }).toList();

        productDTO.setCategories(categoryDTOS);
        productDTO.setProductGroups(productGroupDTOS);
        productDTO.setProductOptions(productOptionDTOS);
        return BaseResponseDTO.builder().ok(productDTO);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO create(CreateProductRequest request) {
        Product product = new Product();
        product.setShopId(request.getShopId());
        product.setName(request.getName());
        product.setCode(request.getCode());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCustomizable(request.isCustomizable());
        product.setStatus(ProductConstants.Status.ACTIVE); // default status
        product.setThumbnailUrl(fileUtils.autoCompressImageAndSave(request.getThumbnail()));
        product.setOriginalImage(fileUtils.save(request.getOriginalImage()));

        // Map product images
        List<String> productImageUrls = fileUtils.autoCompressImageAndSave(request.getProductImages());
        List<ProductImage> productImages = productImageUrls.stream().filter(StringUtils::hasText).map(imageUrl -> {
            ProductImage productImage = new ProductImage();
            productImage.setImage(imageUrl);
            productImage.setProduct(product);
            return productImage;
        }).toList();
        product.setImages(productImages);

        // Map categories
        if (!request.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            product.setCategories(categories);
        }

        // Map product groups
        if (!request.getProductGroupIds().isEmpty()) {
            List<ProductGroup> productGroups = productGroupRepository.findAllById(request.getProductGroupIds());
            product.setProductGroups(productGroups);
        }

        // Map product options
        if (!request.getOptions().isEmpty()) {
            List<ProductOption> productOptions = request.getOptions().stream()
                    .map(option -> mapOptionRequest(option, product))
                    .toList();
            product.setOptions(productOptions);
        }

        productRepository.save(product);
        return BaseResponseDTO.builder().ok(product);
    }

    private ProductOption mapOptionRequest(CreateProductRequest.Option optionRequest, Product product) {
        ProductOption option = new ProductOption();
        option.setName(optionRequest.getName());
        option.setProduct(product);
        List<String> imageUrls = fileUtils.autoCompressImageAndSave(optionRequest.getImages());
        List<ProductOptionValue> images = imageUrls.stream().filter(StringUtils::hasText).map(imageUrl -> {
            ProductOptionValue optionValue = new ProductOptionValue();
            optionValue.setImage(imageUrl);
            optionValue.setProductOption(option);
            return optionValue;
        }).toList();
        option.setValues(images);
        return option;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO update(UpdateProductRequest request) {
        Integer productId = request.getId();
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Product not exists");
        }

        Product product = productOptional.get();
        BeanUtils.copyProperties(request, product, "originalImage", "thumbnail", "images", "options");
        String newThumbnailUrl = fileUtils.autoCompressImageAndSave(request.getThumbnail());
        String newOriginalImageUrl = fileUtils.save(request.getOriginalImage());

        if (StringUtils.hasText(newThumbnailUrl)) {
            fileUtils.delete(product.getThumbnailUrl());
            product.setThumbnailUrl(newThumbnailUrl);
        }

        if (StringUtils.hasText(newOriginalImageUrl)) {
            fileUtils.delete(product.getOriginalImage());
            product.setOriginalImage(newOriginalImageUrl);
        }

        updateProductCategories(request);
        updateProductProductGroups(request);
        updateProductImages(product, request);
        updateProductOptions(product, request);
        productRepository.save(product);
        return BaseResponseDTO.builder().ok(product);
    }

    private void updateProductImages(Product product, UpdateProductRequest request) {
        List<ProductImage> oldProductImages = product.getImages();
        List<Integer> oldProductImageIds = request.getProductImages().stream()
                .map(UpdateProductRequest.ProductImage::getId)
                .filter(Objects::nonNull).toList();
        oldProductImages.removeIf(productImage -> oldProductImageIds.stream()
            .noneMatch(oldProductImageId -> Objects.equals(oldProductImageId, productImage.getId()))
        );
        List<ProductImage> newProductImages = request.getProductImages().stream()
                .filter(productImage -> Objects.isNull(productImage.getId()))
                .map(productImage -> {
                    ProductImage newProductImage = new ProductImage();
                    newProductImage.setProduct(product);
                    newProductImage.setImage(fileUtils.autoCompressImageAndSave(productImage.getImage()));
                    return newProductImage;
                }).toList();
        oldProductImages.addAll(newProductImages);
    }

    private void updateProductCategories(UpdateProductRequest request) {
        Integer productId = request.getId();
        List<ProductCategory> oldProductCategories = productCategoryRepository.findAllByProductId(productId);
        List<Integer> oldCategoryIds = oldProductCategories.stream().map(ProductCategory::getCategoryId).toList();
        List<ProductCategory> productCategoriesToDelete = new ArrayList<>();
        List<Integer> categoryIdsUpdated = request.getCategoryIds();

        for (ProductCategory productCategory : oldProductCategories) {
            if (!categoryIdsUpdated.contains(productCategory.getCategoryId())) {
                productCategoriesToDelete.add(productCategory);
            }
        }

        List<ProductCategory> newProductCategories = categoryIdsUpdated.stream()
                .filter(categoryId -> !oldCategoryIds.contains(categoryId))
                .map(categoryId -> {
                    ProductCategory productProductGroup = new ProductCategory();
                    productProductGroup.setProductId(productId);
                    productProductGroup.setCategoryId(categoryId);
                    return productProductGroup;
                })
                .toList();

        productCategoryRepository.saveAll(newProductCategories);
        productCategoryRepository.deleteAll(productCategoriesToDelete);
    }

    private void updateProductProductGroups(UpdateProductRequest request) {
        Integer productId = request.getId();
        List<ProductProductGroup> oldProductProductGroups = productProductGroupRepository.findAllByProductId(productId);
        List<Integer> oldProductProductGroupIds = oldProductProductGroups.stream()
                .map(ProductProductGroup::getProductGroupId)
                .toList();
        List<ProductProductGroup> productProductGroupsToDelete = new ArrayList<>();
        List<Integer> productGroupIdsUpdated = request.getProductGroupIds();

        for (ProductProductGroup productProductGroup : oldProductProductGroups) {
            if (!productGroupIdsUpdated.contains(productProductGroup.getProductGroupId())) {
                productProductGroupsToDelete.add(productProductGroup);
            }
        }

        List<ProductProductGroup> newProductProductGroups = productGroupIdsUpdated.stream()
                .filter(productProductGroupId -> !oldProductProductGroupIds.contains(productProductGroupId))
                .map(productProductGroupId -> {
                    ProductProductGroup productProductGroup = new ProductProductGroup();
                    productProductGroup.setProductId(productId);
                    productProductGroup.setProductGroupId(productProductGroupId);
                    return productProductGroup;
                })
                .toList();

        productProductGroupRepository.saveAll(newProductProductGroups);
        productProductGroupRepository.deleteAll(productProductGroupsToDelete);
    }

    private void updateProductOptions(Product product, UpdateProductRequest request) {
        List<ProductOption> oldProductOptions = product.getOptions();
        Map<Integer, ProductOption> oldProductOptionMap = oldProductOptions.stream()
                .collect(Collectors.toMap(ProductOption::getId, productOption -> productOption));
        List<Integer> oldProductOptionIds = new ArrayList<>();
        List<ProductOption> newProductOptions = new ArrayList<>();

        request.getOptions().forEach(option -> {
            if (Objects.isNull(option.getId())) {
                CreateProductRequest.Option optionRequest = new CreateProductRequest.Option();
                optionRequest.setName(option.getName());
                List<MultipartFile> images = option.getImages().stream()
                        .map(UpdateProductRequest.Option.OptionValue::getImage)
                        .toList();
                optionRequest.setImages(images.toArray(new MultipartFile[0]));
                ProductOption newProductOption = mapOptionRequest(optionRequest, product);
                newProductOptions.add(newProductOption);
            } else {
                oldProductOptionIds.add(option.getId());
                ProductOption oldProductOption = oldProductOptionMap.get(option.getId());

                if (Objects.nonNull(oldProductOption)) {
                    oldProductOption.setName(option.getName());
                    updateProductOptionValues(oldProductOption, option);
                }
            }
        });

        oldProductOptions.removeIf(oldProductOption -> oldProductOptionIds.stream()
            .noneMatch(oldProductOptionId -> Objects.equals(oldProductOptionId, oldProductOption.getId()))
        );
        oldProductOptions.addAll(newProductOptions);
    }

    private void updateProductOptionValues(ProductOption productOption, UpdateProductRequest.Option request) {
        List<ProductOptionValue> oldProductOptionValues = productOption.getValues();
        List<Integer> oldProductOptionValueIds = request.getImages().stream()
                .map(UpdateProductRequest.Option.OptionValue::getId)
                .filter(Objects::nonNull).toList();
        oldProductOptionValues.removeIf(optionValue -> oldProductOptionValueIds.stream()
            .noneMatch(oldProductOptionValueId -> Objects.equals(oldProductOptionValueId, optionValue.getId()))
        );
        List<ProductOptionValue> newProductOptionValues = request.getImages().stream()
                .filter(optionValue -> Objects.isNull(optionValue.getId()))
                .map(optionValue -> {
                    ProductOptionValue newProductOptionValue = new ProductOptionValue();
                    newProductOptionValue.setProductOption(productOption);
                    newProductOptionValue.setImage(fileUtils.autoCompressImageAndSave(optionValue.getImage()));
                    return newProductOptionValue;
                }).toList();
        oldProductOptionValues.addAll(newProductOptionValues);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO delete(Integer productId) {
        if (productRepository.existsById(productId)) {
            productRepository.updateStatusById(productId, ProductConstants.Status.DELETED);
            return BaseResponseDTO.builder().ok();
        }

        throw new BaseBadRequestException(ENTITY_NAME, "Not found product to delete!");
    }
}
