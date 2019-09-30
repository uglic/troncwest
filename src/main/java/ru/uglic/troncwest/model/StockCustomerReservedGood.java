package ru.uglic.troncwest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "stock_customer_reserved_goods", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"goods_id", "stock_id", "customer_id"}, name = "good_stock_customer_idx")})
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class StockCustomerReservedGood extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", nullable = false)
    @NotNull
    private Good good;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @NotNull
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private Customer customer;

    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    private long quantity;
}
