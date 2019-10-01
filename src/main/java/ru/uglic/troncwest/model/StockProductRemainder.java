package ru.uglic.troncwest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "stock_product_remainders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"stock_id", "product_id"}, name = "stock_product_idx")})
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class StockProductRemainder extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @NotNull
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    private long quantity;
}
