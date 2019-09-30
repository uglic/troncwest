package ru.uglic.troncwest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.uglic.troncwest.model.Product;
import ru.uglic.troncwest.model.Stock;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
public class CustomerReservedDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ProductDto product;
    private final StockDto stock;
    private final long quantity;

    private CustomerReservedDto(ProductDto product, StockDto stock, long quantity) {
        this.product = product;
        this.stock = stock;
        this.quantity = quantity;
    }

    public static CustomerReservedDto asDto(Product product, Stock stock, long quantity) {
        return new CustomerReservedDto(ProductDto.asDto(product), StockDto.asDto(stock), quantity);
    }
}
