package com.ars.productservice.resource;

import com.ars.productservice.dto.request.product.CheckOrderInfoRequestDTO;
import com.ars.productservice.dto.request.product.CreateProductRequest;
import com.ars.productservice.dto.request.product.SearchProductRequest;
import com.ars.productservice.dto.request.product.UpdateProductRequest;
import com.ars.productservice.service.ProductService;
import com.dct.model.dto.response.BaseResponseDTO;

import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/p/v1/products")
    public BaseResponseDTO getAllWithPaging(@ModelAttribute SearchProductRequest request) {
        return productService.getAllWithPaging(request);
    }

    @GetMapping("/p/v1/products/{productId}")
    public BaseResponseDTO getProductDetail(@PathVariable Integer productId) {
        return productService.getDetail(productId);
    }

    @GetMapping("/internal/products/files/download/{productId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer productId) throws IOException {
        String filePathFromDb = productService.getOriginalFilePath(productId);
        File file = new File(filePathFromDb);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Path path = file.toPath();
        String contentType = Optional.ofNullable(Files.probeContentType(path)).orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @PostMapping("/internal/products/check-order-info")
    public BaseResponseDTO checkOrderInfoRequest(@Valid @RequestBody CheckOrderInfoRequestDTO requestDTO) {
        return productService.checkOrderInfo(requestDTO);
    }

    @PostMapping("/v1/products")
    public BaseResponseDTO createProduct(@Valid @ModelAttribute CreateProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseError(bindingResult);
        }

        return productService.create(request);
    }

    @PutMapping("/v1/products")
    public BaseResponseDTO updateProduct(@Valid @ModelAttribute UpdateProductRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return responseError(bindingResult);
        }

        return productService.update(request);
    }

    @DeleteMapping("/v1/products/{productId}")
    public BaseResponseDTO deleteProduct(@PathVariable Integer productId) {
        return productService.delete(productId);
    }

    private BaseResponseDTO responseError(BindingResult bindingResult) {
        String errors = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return BaseResponseDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .success(Boolean.FALSE)
                .message(errors)
                .build();
    }
}
