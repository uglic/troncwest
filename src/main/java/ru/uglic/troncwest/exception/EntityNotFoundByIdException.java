package ru.uglic.troncwest.exception;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

@Getter
public class EntityNotFoundByIdException extends EntityNotFoundException {
    private final String message;
    private final long id;
    private final Class<?> clazz;

    public EntityNotFoundByIdException(String message, long id, Class<?> clazz) {
        super(String.format("entity.not-found.%s id=%d class=%s", message, id, clazz.getCanonicalName()));
        this.message = "entity.not-found." + message;
        this.id = id;
        this.clazz = clazz;
    }
}
