package com.ars.productservice.dto.response.category;

import com.dct.model.dto.response.AuditingDTO;

import java.time.Instant;

public class CategoryDTO extends AuditingDTO {
    private String name;
    private String code;
    private String description;

    public CategoryDTO() {}

    public CategoryDTO(
        Integer id,
        String name,
        String code,
        String description,
        String createdBy,
        Instant createdDate
    ) {
        this.name = name;
        this.code = code;
        this.description = description;
        setId(id);
        setCreatedBy(createdBy);
        setCreatedDate(createdDate);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
