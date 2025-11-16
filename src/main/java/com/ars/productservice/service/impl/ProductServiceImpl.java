package com.ars.productservice.service.impl;

import com.ars.productservice.constants.ProductConstants;
import com.ars.productservice.dto.mapping.ProductCategoryIdPair;
import com.ars.productservice.dto.mapping.ProductProductGroupIdPair;
import com.ars.productservice.dto.request.product.CreateProductRequest;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.request.product.UpdateProductRequest;
import com.ars.productservice.dto.response.product.ProductDTO;
import com.ars.productservice.entity.Category;
import com.ars.productservice.entity.Product;
import com.ars.productservice.entity.ProductGroup;
import com.ars.productservice.entity.ProductOption;
import com.ars.productservice.entity.ProductOptionAttribute;
import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.repository.ProductOptionRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.repository.VariantRepository;
import com.ars.productservice.service.ProductService;
import com.dct.config.common.FileUtils;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ProductServiceImpl";
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductGroupRepository productGroupRepository;
    private final VariantRepository variantRepository;
    private final ProductOptionRepository productOptionRepository;
    private final FileUtils fileUtils = new FileUtils();

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductGroupRepository productGroupRepository,
                              VariantRepository variantRepository,
                              ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productGroupRepository = productGroupRepository;
        this.variantRepository = variantRepository;
        this.productOptionRepository = productOptionRepository;
        this.fileUtils.setPrefixPath(ProductConstants.Upload.prefix);
        this.fileUtils.setUploadDirectory(ProductConstants.Upload.location);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO getAllWithPaging(SearchProductRequest request) {
        Page<ProductDTO> productPage = productRepository.getAllWithPaging(request);
        Set<Integer> productIds = productPage.stream().map(ProductDTO::getId).collect(Collectors.toSet());
        List<ProductCategoryIdPair> productCategoryIdPairs = categoryRepository.findIdsByProductId(productIds);
        List<ProductProductGroupIdPair> productProductGroupIdPairs = productGroupRepository.findIdsByProductId(productIds);
        Map<Integer, List<Integer>> productCategoryIdMap = productCategoryIdPairs.stream()
                .collect(Collectors.groupingBy(
                    ProductCategoryIdPair::getProductId,
                    Collectors.mapping(ProductCategoryIdPair::getCategoryId, Collectors.toList())
                ));

        Map<Integer, List<Integer>> productProductGroupIdMap = productProductGroupIdPairs.stream()
                .collect(Collectors.groupingBy(
                    ProductProductGroupIdPair::getProductId,
                    Collectors.mapping(ProductProductGroupIdPair::getProductGroupId, Collectors.toList())
                ));

        List<ProductDTO> productResponses = productPage.getContent();
        productResponses.forEach(productDTO -> {
            productDTO.setCategoryIds(productCategoryIdMap.get(productDTO.getId()));
            productDTO.setGroupIds(productProductGroupIdMap.get(productDTO.getId()));
        });

        return BaseResponseDTO.builder().total(productPage.getTotalElements()).ok(productResponses);
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
        // Map options
        if (!request.getOptions().isEmpty()) {
            List<ProductOption> options = request.getOptions().stream()
                    .map(option -> mapOptionRequest(option, productSave))
                    .toList();
            productOptionRepository.saveAll(options);
            productSave.setOptions(options);
        }

        return BaseResponseDTO.builder().ok(productSave);
    }

    private ProductOption mapOptionRequest(CreateProductRequest.OptionRequest optionRequest, Product product) {
        ProductOption option = new ProductOption();
        option.setProductId(product.getId());
        option.setName(optionRequest.getName());
        option.setType(optionRequest.getType());
        option.setTopPercentage(optionRequest.getTopPercentage());
        option.setLeftPercentage(optionRequest.getLeftPercentage());
        option.setWidthPercentage(optionRequest.getWidthPercentage());
        option.setHeightPercentage(optionRequest.getHeightPercentage());
        option.setDescription(optionRequest.getDescription());
        option.setData(optionRequest.getData());

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
