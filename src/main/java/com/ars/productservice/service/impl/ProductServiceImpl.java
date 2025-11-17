package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.dto.request.product.CreateProductRequest;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.request.product.UpdateProductRequest;
import com.ars.productservice.dto.response.category.CategoryDTO;
import com.ars.productservice.dto.response.product.ProductDTO;
import com.ars.productservice.dto.response.product.ProductGroupDTO;
import com.ars.productservice.dto.response.product.ProductOptionDTO;
import com.ars.productservice.dto.response.product.VariantDTO;
import com.ars.productservice.entity.Category;
import com.ars.productservice.entity.Product;
import com.ars.productservice.entity.ProductGroup;
import com.ars.productservice.entity.ProductOption;
import com.ars.productservice.entity.ProductOptionAttribute;
import com.ars.productservice.entity.Variant;
import com.ars.productservice.entity.VariantOption;
import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.repository.ProductOptionRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.repository.VariantOptionRepository;
import com.ars.productservice.repository.VariantRepository;
import com.ars.productservice.service.ProductService;
import com.dct.config.common.FileUtils;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ProductServiceImpl";
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductGroupRepository productGroupRepository;
    private final VariantRepository variantRepository;
    private final ProductOptionRepository productOptionRepository;
    private final FileUtils fileUtils = new FileUtils();
    private final VariantOptionRepository variantOptionRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductGroupRepository productGroupRepository,
                              VariantRepository variantRepository,
                              ProductOptionRepository productOptionRepository, VariantOptionRepository variantOptionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productGroupRepository = productGroupRepository;
        this.variantRepository = variantRepository;
        this.productOptionRepository = productOptionRepository;
        this.fileUtils.setPrefixPath(ProductConstants.Upload.prefix);
        this.fileUtils.setUploadDirectory(ProductConstants.Upload.location);
        this.variantOptionRepository = variantOptionRepository;
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
        productDTO.setCode(product.getCode());
        List<VariantDTO> variantDTOS = variantRepository.findAllByProductId(productId);

        List<ProductOptionDTO> productOptionDTOS = product.getOptions().stream().map(productOption -> {
            ProductOptionDTO productOptionDTO = new ProductOptionDTO();
            productOptionDTO.setName(productOption.getName());
            return productOptionDTO;
        }).toList();

        List<CategoryDTO> categoryDTOS = product.getCategories().stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(category.getName());
            return categoryDTO;
        }).toList();

        List<ProductGroupDTO> productGroupDTOS = product.getProductGroups().stream().map(productGroup -> {
            ProductGroupDTO productGroupDTO = new ProductGroupDTO();
            productGroupDTO.setName(productGroup.getName());
            return productGroupDTO;
        }).toList();

        productDTO.setCategories(categoryDTOS);
        productDTO.setProductGroups(productGroupDTOS);
        productDTO.setProductOptions(productOptionDTOS);
        productDTO.setVariants(variantDTOS);
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
        product.setKeyword(request.getKeyword());
        product.setStatus(ProductConstants.Status.ACTIVE); // default status
        product.setThumbnailUrl(fileUtils.autoCompressImageAndSave(request.getThumbnail()));
        product.setOriginalImage(fileUtils.save(request.getOriginalImage()));

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

        Product productSave = productRepository.save(product);
        Map<Integer, Integer> productOptionIdMap = new HashMap<>();

        // Map product options
        if (!request.getOptions().isEmpty()) {
            List<ProductOption> productOptions = request.getOptions().stream()
                    .map(option -> mapOptionRequest(option, productSave))
                    .toList();
            productOptionRepository.saveAll(productOptions);
            productSave.setOptions(productOptions);
            productOptions.forEach(productOption ->
                productOptionIdMap.put(productOption.getRefId(), productOption.getId())
            );
        }

        // Map variants
        if (!request.getVariants().isEmpty()) {
            List<Variant> variants = request.getVariants().stream()
                    .map(variantRequest -> mapVariantRequest(variantRequest, productSave))
                    .toList();
            variantRepository.saveAll(variants);
            List<VariantOption> variantOptions = new ArrayList<>();
            variants.forEach(variant ->
                variant.getProductOptionIds().forEach(productOptionId -> {
                    VariantOption variantOption = new VariantOption();
                    variantOption.setVariantId(variant.getId());
                    variantOption.setProductOptionId(productOptionIdMap.get(productOptionId));
                    variantOptions.add(variantOption);
                })
            );
            variantOptionRepository.saveAll(variantOptions);
        }

        return BaseResponseDTO.builder().ok(productSave);
    }

    private Variant mapVariantRequest(CreateProductRequest.VariantRequest variantRequest, Product product) {
        Variant variant = new Variant();
        variant.setAttributeId(variantRequest.getAttributeId());
        variant.setProductId(product.getId());
        variant.setName(variantRequest.getName());
        variant.setPrice(variantRequest.getPrice());
        variant.setThumbnailUrl(fileUtils.autoCompressImageAndSave(variantRequest.getThumbnail()));
        variant.setOriginalImage(fileUtils.save(variantRequest.getOriginalImage()));
        variant.setProductOptionIds(variantRequest.getProductOptionIds());
        return variant;
    }

    private ProductOption mapOptionRequest(CreateProductRequest.OptionRequest optionRequest, Product product) {
        ProductOption option = new ProductOption();
        option.setProductId(product.getId());
        option.setRefId(optionRequest.getId());
        option.setName(optionRequest.getName());
        option.setType(optionRequest.getType());
        option.setTopPercentage(optionRequest.getTopPercentage());
        option.setLeftPercentage(optionRequest.getLeftPercentage());
        option.setWidthPercentage(optionRequest.getWidthPercentage());
        option.setHeightPercentage(optionRequest.getHeightPercentage());
        option.setDescription(optionRequest.getDescription());

        if (!optionRequest.getAttributes().isEmpty()) {
            List<ProductOptionAttribute> attrs = optionRequest.getAttributes().stream()
                    .map(attrReq -> {
                        ProductOptionAttribute attr = new ProductOptionAttribute();
                        attr.setText(attrReq.getText());
                        attr.setImage(fileUtils.autoCompressImageAndSave(attrReq.getImage()));
                        attr.setProductOption(option);
                        return attr;
                    }).toList();
            option.setAttributes(attrs);
        }

        return option;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO update(UpdateProductRequest request) {
        return null;
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
