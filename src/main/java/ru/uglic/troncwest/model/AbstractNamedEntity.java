package ru.uglic.troncwest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
abstract public class AbstractNamedEntity extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    private static final int MAX_NAME_LEN = 200;

    @Column(name = "name", nullable = false, unique = true, length = MAX_NAME_LEN)
    @NotBlank
    @Length(min = 1, max = MAX_NAME_LEN)
    private String name;
}
