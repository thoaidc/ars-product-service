package com.ars.productservice.entity;

import com.ars.productservice.dto.response.category.CategoryDTO;
import com.dct.config.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "category")
@SqlResultSetMappings(
    {
        @SqlResultSetMapping(
            name = "categoryGetWithPaging",
            classes = {
                @ConstructorResult(
                    targetClass = CategoryDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "createdBy", type = String.class),
                        @ColumnResult(name = "createdDate", type = Instant.class)
                    }
                ),
            }
        )
    }
)
@SuppressWarnings("unused")
public class Category extends AbstractAuditingEntity {

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String code;

    @Column
    private String description;

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
