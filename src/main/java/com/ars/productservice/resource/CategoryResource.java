package com.ars.productservice.resource;

import com.ars.productservice.dto.request.category.SaveCategoryRequest;
import com.ars.productservice.service.CategoryService;
import com.dct.model.dto.request.BaseRequestDTO;
import com.dct.model.dto.response.BaseResponseDTO;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryResource {
    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/p/v1/products/categories")
    public BaseResponseDTO getAllCategories(@ModelAttribute BaseRequestDTO requestDTO) {
        return categoryService.getAllWithPaging(requestDTO);
    }

    @GetMapping("/p/v1/products/categories/{id}")
    public BaseResponseDTO getCategoryById(@PathVariable @NotNull Integer id) {
        return categoryService.getDetail(id);
    }

    @PostMapping("/v1/products/categories")
    public BaseResponseDTO createCategory(@RequestBody SaveCategoryRequest request) {
        return categoryService.save(request);
    }

    @PutMapping("/v1/products/categories")
    public BaseResponseDTO updateCategory(@RequestBody SaveCategoryRequest request) {
        return categoryService.save(request);
    }

    @DeleteMapping("/v1/products/categories/{id}")
    public BaseResponseDTO deleteCategory(@PathVariable @NotNull Integer id) {
        return categoryService.deleteById(id);
    }
}
