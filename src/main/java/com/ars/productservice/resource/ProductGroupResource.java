package com.ars.productservice.resource;

import com.ars.productservice.dto.request.product.SaveProductGroupRequest;
import com.ars.productservice.dto.request.product.SearchProductGroupRequest;
import com.ars.productservice.service.ProductGroupService;
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
public class ProductGroupResource {
    private final ProductGroupService productGroupService;

    public ProductGroupResource(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @GetMapping("/p/v1/products/groups")
    public BaseResponseDTO getAllProductGroups(@ModelAttribute SearchProductGroupRequest requestDTO) {
        return productGroupService.getAllWithPaging(requestDTO);
    }

    @GetMapping("/p/v1/products/groups/{id}")
    public BaseResponseDTO getProductGroupById(@PathVariable @NotNull Integer id) {
        return productGroupService.getDetail(id);
    }

    @PostMapping("/v1/products/groups")
    public BaseResponseDTO createProductGroup(@RequestBody SaveProductGroupRequest request) {
        return productGroupService.save(request);
    }

    @PutMapping("/v1/products/groups")
    public BaseResponseDTO updateProductGroup(@RequestBody SaveProductGroupRequest request) {
        return productGroupService.save(request);
    }

    @DeleteMapping("/v1/products/groups/{id}")
    public BaseResponseDTO deleteProductGroup(@PathVariable @NotNull Integer id) {
        return productGroupService.deleteById(id);
    }
}
