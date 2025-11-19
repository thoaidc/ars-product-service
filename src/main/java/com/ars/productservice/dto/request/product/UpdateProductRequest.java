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

    @Size(max = 500)
    private String keyword;

    private MultipartFile thumbnail;
    private MultipartFile originalImage;
    private List<@NotNull Integer> categoryIds = new ArrayList<>();
    private List<@NotNull Integer> productGroupIds = new ArrayList<>();
//    private List<@Valid VariantRequest> variants = new ArrayList<>();
    private List<@Valid OptionRequest> options = new ArrayList<>();

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

//    public List<VariantRequest> getVariants() {
//        return variants;
//    }
//
//    public void setVariants(List<VariantRequest> variants) {
//        this.variants = variants;
//    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }

    public static class VariantRequest extends CreateProductRequest.VariantRequest {
        @NotNull
        private Integer id;
        private MultipartFile thumbnail;
        private MultipartFile originalImage;

        @NotBlank
        @Size(max = 255)
        private String name;

        @NotNull
        @DecimalMin("0.0")
        private BigDecimal price;
        private List<@NotNull Integer> productOptionIds = new ArrayList<>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public MultipartFile getThumbnail() {
            return thumbnail;
        }

        @Override
        public void setThumbnail(MultipartFile thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public MultipartFile getOriginalImage() {
            return originalImage;
        }

        @Override
        public void setOriginalImage(MultipartFile originalImage) {
            this.originalImage = originalImage;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public BigDecimal getPrice() {
            return price;
        }

        @Override
        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public List<Integer> getProductOptionIds() {
            return productOptionIds;
        }

        @Override
        public void setProductOptionIds(List<Integer> productOptionIds) {
            this.productOptionIds = productOptionIds;
        }
    }

    public static class OptionRequest {
        private Integer id;
        @NotBlank
        @Size(max = 100)
        private String name;

        @NotBlank
        @Size(max = 50)
        private String type;
        private Float topPercentage;
        private Float leftPercentage;
        private Float widthPercentage;
        private Float heightPercentage;
        @Size(max = 1000)
        private String description;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Float getTopPercentage() {
            return topPercentage;
        }

        public void setTopPercentage(Float topPercentage) {
            this.topPercentage = topPercentage;
        }

        public Float getLeftPercentage() {
            return leftPercentage;
        }

        public void setLeftPercentage(Float leftPercentage) {
            this.leftPercentage = leftPercentage;
        }

        public Float getWidthPercentage() {
            return widthPercentage;
        }

        public void setWidthPercentage(Float widthPercentage) {
            this.widthPercentage = widthPercentage;
        }

        public Float getHeightPercentage() {
            return heightPercentage;
        }

        public void setHeightPercentage(Float heightPercentage) {
            this.heightPercentage = heightPercentage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<OptionAttribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<OptionAttribute> attributes) {
            this.attributes = attributes;
        }

        public static class OptionAttribute {
            private Integer id;
            private MultipartFile image;

            @Size(max = 255)
            private String text;

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

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
