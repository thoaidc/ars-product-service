package com.ars.productservice.dto.request.product;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CreateProductRequest {
    private Integer shopId;
    private String name;
    private String code;
    private BigDecimal price;
    private String description;
    private boolean customizable;
    private String keyword;
    private MultipartFile thumbnail;
    private MultipartFile originalImage;
    private List<Integer> categoryIds = new ArrayList<>();
    private List<Integer> productGroupIds = new ArrayList<>();
    private List<VariantRequest> variants = new ArrayList<>();
    private List<OptionRequest> options = new ArrayList<>();

    public static class VariantRequest {
        private MultipartFile thumbnail;
        private MultipartFile originalImage;
        private String name;
        private BigDecimal price;
        private Integer attributeId;
        private List<Integer> productOptionIds = new ArrayList<>();

        public MultipartFile getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(MultipartFile thumbnail) {
            this.thumbnail = thumbnail;
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
        private String name;
        private String type;
        private Float topPercentage;
        private Float leftPercentage;
        private Float widthPercentage;
        private Float heightPercentage;
        private String description;
        private String data;
        private List<OptionAttribute> attributes = new ArrayList<>();

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public List<OptionAttribute> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<OptionAttribute> attributes) {
            this.attributes = attributes;
        }

        public static class OptionAttribute {
            private MultipartFile image;
            private String text;

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
