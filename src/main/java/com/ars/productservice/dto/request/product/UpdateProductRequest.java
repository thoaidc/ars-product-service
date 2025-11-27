package com.ars.productservice.dto.request.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UpdateProductRequest {
    @NotNull
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @Size(max = 1000)
    private String description;
    private boolean customizable;
    private MultipartFile thumbnail;
    private MultipartFile originalImage;
    private List<ProductImage> productImages = new ArrayList<>();
    private List<@NotNull Integer> categoryIds = new ArrayList<>();
    private List<@NotNull Integer> productGroupIds = new ArrayList<>();
    private List<@Valid Option> options = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCustomizable() {
        return customizable;
    }

    public void setCustomizable(boolean customizable) {
        this.customizable = customizable;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public MultipartFile getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(MultipartFile originalImage) {
        this.originalImage = originalImage;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Integer> getProductGroupIds() {
        return productGroupIds;
    }

    public void setProductGroupIds(List<Integer> productGroupIds) {
        this.productGroupIds = productGroupIds;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static class ProductImage {
        private Integer id;
        private MultipartFile image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public MultipartFile getImage() {
            return image;
        }

        public void setImage(MultipartFile image) {
            this.image = image;
        }
    }

    public static class Option {
        private Integer id;
        @NotBlank
        @Size(max = 100)
        private String name;
        private List<@Valid OptionValue> images = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<OptionValue> getImages() {
            return images;
        }

        public void setImages(List<OptionValue> images) {
            this.images = images;
        }

        public static class OptionValue {
            private Integer id;
            private MultipartFile image;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public MultipartFile getImage() {
                return image;
            }

            public void setImage(MultipartFile image) {
                this.image = image;
            }
        }
    }
}
