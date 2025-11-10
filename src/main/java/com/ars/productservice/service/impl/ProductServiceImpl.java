package com.ars.productservice.service.impl;

import com.ars.productservice.repository.CategoryRepository;
import com.ars.productservice.repository.ProductGroupRepository;
import com.ars.productservice.repository.ProductRepository;
import com.ars.productservice.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductGroupRepository productGroupRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductGroupRepository productGroupRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productGroupRepository = productGroupRepository;
    }
}
