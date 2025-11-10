package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.SaveProductGroupRequest;
import com.ars.productservice.entity.ProductGroup;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.service.ProductGroupService;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupRepository productGroupRepository;
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.ProductGroupServiceImpl";

    public ProductGroupServiceImpl(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    @Override
    public BaseResponseDTO getAllWithPaging(BaseRequestDTO requestDTO) {
        return null;
    }

    @Override
    public BaseResponseDTO getDetail(Integer id) {
        Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(id);

        if (productGroupOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "ProductGroup not found!");
        }

        return BaseResponseDTO.builder().ok(productGroupOptional.get());
    }

    @Override
    public BaseResponseDTO save(SaveProductGroupRequest request) {
        ProductGroup category;

        if (Objects.nonNull(request.getId())) {
            Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(request.getId());

            if (productGroupOptional.isEmpty()) {
                throw new BaseBadRequestException(ENTITY_NAME, "Product group not found!");
            }

            category = productGroupOptional.get();
        } else {
            category = new ProductGroup();
        }

        BeanUtils.copyProperties(request, category);
        return BaseResponseDTO.builder().ok(productGroupRepository.save(category));
    }

    @Override
    public BaseResponseDTO deleteById(Integer id) {
        productGroupRepository.deleteById(id);
        return BaseResponseDTO.builder().ok();
    }
}
