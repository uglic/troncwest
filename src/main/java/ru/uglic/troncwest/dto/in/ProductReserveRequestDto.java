package ru.uglic.troncwest.dto.in;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.uglic.troncwest.model.Customer;
import ru.uglic.troncwest.model.Product;
import ru.uglic.troncwest.model.Stock;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class ProductReserveRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private final Long productId;

    @NotNull
    @Positive
    private final Long stockId;

    @NotNull
    @Positive
    private final Long customerId;

    @Positive
    private final long quantity;

    private ProductReserveRequestDto(Long productId, Long stockId, Long customerId, long quantity) {
        this.productId = productId;
        this.stockId = stockId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

    public static ProductReserveRequestDto asDto(Product product, Stock stock, Customer customer, long quantity) {
        return new ProductReserveRequestDto(
                product != null ? product.getId() : null,
                stock != null ? stock.getId() : null,
                customer != null ? customer.getId() : null,
                quantity);
    }
}
