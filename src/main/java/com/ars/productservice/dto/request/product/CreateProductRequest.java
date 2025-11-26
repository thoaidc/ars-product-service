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

@SuppressWarnings("unused")
public class CreateProductRequest {
    @NotNull
    private Integer shopId;

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
    private List<@NotNull Integer> categoryIds = new ArrayList<>();
    private List<@NotNull Integer> productGroupIds = new ArrayList<>();
    private List<@Valid VariantRequest> variants = new ArrayList<>();
    private List<@Valid OptionRequest> options = new ArrayList<>();

    public static class VariantRequest {
        private MultipartFile thumbnail;
        private MultipartFile originalImage;
        @NotBlank
        @Size(max = 255)
        private String name;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal price;

        @NotNull
        private Integer attributeId;
        private List<@NotNull Integer> productOptionIds = new ArrayList<>();

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(Integer attributeId) {
            this.attributeId = attributeId;
        }

        public List<Integer> getProductOptionIds() {
            return productOptionIds;
        }

        public void setProductOptionIds(List<Integer> productOptionIds) {
            this.productOptionIds = productOptionIds;
        }
    }

    public static class OptionRequest {
        private Integer id;
        @NotBlank
        @Size(max = 100)
        private String name;
        private List<@Valid OptionAttribute> attributes = new ArrayList<>();

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

        public List<OptionAttribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<OptionAttribute> attributes) {
            this.attributes = attributes;
        }

        public static class OptionAttribute {
            private MultipartFile image;

            public MultipartFile getImage() {
                return image;
            }

            public void setImage(MultipartFile image) {
                this.image = image;
            }
        }
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public List<VariantRequest> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantRequest> variants) {
        this.variants = variants;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }
}
