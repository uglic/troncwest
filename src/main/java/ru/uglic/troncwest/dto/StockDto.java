package ru.uglic.troncwest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.uglic.troncwest.model.Stock;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class StockDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;

    private StockDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StockDto asDto(Stock entity) {
        return entity != null ? new StockDto(entity.getId(), entity.getName()) : null;
    }
}
