package ru.uglic.troncwest.dto;

import ru.uglic.troncwest.model.Good;

import java.io.Serializable;

public class GoodDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;

    private GoodDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GoodDto asDto(Good entity) {
        return entity != null ? new GoodDto(entity.getId(), entity.getName()) : null;
    }
}
