package ru.uglic.troncwest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stocks")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Stock extends AbstractNamedEntity {
    private static final long serialVersionUID = 1L;
}
