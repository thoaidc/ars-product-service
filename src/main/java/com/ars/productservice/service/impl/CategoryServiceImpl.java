package com.ars.productservice.service.impl;

import com.ars.productservice.dto.request.category.SaveCategoryRequest;
import com.ars.productservice.dto.response.category.CategoryResponseDTO;
import com.ars.productservice.entity.Category;
import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.service.CategoryService;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;
import com.dct.model.exception.BaseBadRequestException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String ENTITY_NAME = "com.ars.productservice.service.impl.CategoryServiceImpl";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BaseResponseDTO getAllWithPaging(BaseRequestDTO requestDTO) {
        Page<CategoryResponseDTO> categoryPage = categoryRepository.getAllWithPaging(requestDTO);
        return BaseResponseDTO.builder().total(categoryPage.getTotalElements()).ok(categoryPage.getContent());
    }

    @Override
    public BaseResponseDTO getDetail(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Category not found!");
        }

        return BaseResponseDTO.builder().ok(categoryOptional.get());
    }

    @Override
    public BaseResponseDTO save(SaveCategoryRequest request) {
        Category category;

        if (Objects.nonNull(request.getId())) {
            Optional<Category> categoryOptional = categoryRepository.findById(request.getId());

            if (categoryOptional.isEmpty()) {
                throw new BaseBadRequestException(ENTITY_NAME, "Category not found!");
            }

            category = categoryOptional.get();
        } else {
            category = new Category();
        }

        BeanUtils.copyProperties(request, category);
        return BaseResponseDTO.builder().ok(categoryRepository.save(category));
    }

    @Override
    public BaseResponseDTO deleteById(Integer id) {
        categoryRepository.deleteById(id);
        return BaseResponseDTO.builder().ok();
    }
}
