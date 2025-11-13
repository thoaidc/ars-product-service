package com.ars.productservice.resource;

import com.ars.productservice.dto.request.attribute.SaveAttributeRequest;
import com.ars.productservice.dto.request.attribute.SearchAttributeRequest;
import com.ars.productservice.service.AttributeService;
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
public class AttributeResource {
    private final AttributeService attributeService;

    public AttributeResource(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/p/v1/products/attributes")
    public BaseResponseDTO getAllAttributes(@ModelAttribute SearchAttributeRequest requestDTO) {
        return attributeService.getAllWithPaging(requestDTO);
    }

    @GetMapping("/p/v1/products/attributes/{id}")
    public BaseResponseDTO getAttributeById(@PathVariable @NotNull Integer id) {
        return attributeService.getDetail(id);
    }

    @PostMapping("/v1/products/attributes")
    public BaseResponseDTO createAttribute(@RequestBody SaveAttributeRequest request) {
        return attributeService.save(request);
    }

    @PutMapping("/v1/products/attributes")
    public BaseResponseDTO updateAttribute(@RequestBody SaveAttributeRequest request) {
        return attributeService.save(request);
    }

    @DeleteMapping("/v1/products/attributes/{id}")
    public BaseResponseDTO deleteAttribute(@PathVariable @NotNull Integer id) {
        return attributeService.deleteById(id);
    }
}
