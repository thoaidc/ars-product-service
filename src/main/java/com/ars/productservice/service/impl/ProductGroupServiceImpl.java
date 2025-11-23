package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.product.SaveProductGroupRequest;
import com.ars.productservice.dto.request.product.SearchProductGroupRequest;
import com.ars.productservice.dto.response.product.ProductGroupDTO;
import com.ars.productservice.entity.ProductGroup;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.service.ProductGroupService;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BaseResponseDTO getAllWithPaging(SearchProductGroupRequest requestDTO) {
        Page<ProductGroupDTO> productGroupPage = productGroupRepository.getAllWithPaging(requestDTO);
        return BaseResponseDTO.builder().total(productGroupPage.getTotalElements()).ok(productGroupPage.getContent());
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
    @Transactional
    public BaseResponseDTO save(SaveProductGroupRequest request) {
        ProductGroup category;

        if (Objects.nonNull(request.getId()) && request.getId() > 0) {
            Optional<ProductGroup> productGroupOptional = productGroupRepository.findById(request.getId());

            if (productGroupOptional.isEmpty()) {
                throw new BaseBadRequestException(ENTITY_NAME, "Product group not found!");
            }

            category = productGroupOptional.get();
        } else {
            category = new ProductGroup();
        }

        BeanUtils.copyProperties(request, category, "id");
        return BaseResponseDTO.builder().ok(productGroupRepository.save(category));
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteById(Integer id) {
        productGroupRepository.deleteById(id);
        return BaseResponseDTO.builder().ok();
    }
}
