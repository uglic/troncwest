package ru.uglic.troncwest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "stock_reserved_product_remainders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"stock_id", "product_id", "customer_id"}, name = "stock_product_customer_idx")})
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class StockReservedProductRemainder extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @NotNull
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private Customer customer;

    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    private long quantity;
}
