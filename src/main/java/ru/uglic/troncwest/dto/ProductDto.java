package ru.uglic.troncwest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.uglic.troncwest.model.Product;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;

    private ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductDto asDto(Product entity) {
        return entity != null ? new ProductDto(entity.getId(), entity.getName()) : null;
    }
}
