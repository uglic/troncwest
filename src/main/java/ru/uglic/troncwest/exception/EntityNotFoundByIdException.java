package ru.uglic.troncwest.exception;

import javax.persistence.EntityNotFoundException;

public class EntityNotFoundByIdException extends EntityNotFoundException {
    public EntityNotFoundByIdException(long id, Class<?> clazz) {
        super(String.format("entity.not-found.by-id id=%d class=%s", id, clazz.getCanonicalName()));
    }
}
