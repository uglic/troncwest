package ru.uglic.troncwest.dto;

import ru.uglic.troncwest.model.Good;
import ru.uglic.troncwest.model.Stock;

import java.io.Serializable;

public class CustomerReservedDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final GoodDto good;
    private final StockDto stock;
    private final long quantity;

    private CustomerReservedDto(GoodDto good, StockDto stock, long quantity) {
        this.good = good;
        this.stock = stock;
        this.quantity = quantity;
    }

    public static CustomerReservedDto asDto(Good good, Stock stock, long quantity) {
        return new CustomerReservedDto(GoodDto.asDto(good), StockDto.asDto(stock), quantity);
    }
}
