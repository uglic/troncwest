package ru.uglic.troncwest.exception;

import lombok.Getter;

@Getter
public class IllegalArgumentByIdException extends IllegalArgumentException {
    private final String message;
    private final long productId;
    private final long stockId;
    private final long customerId;
    private final long quantity;
    private final Long balanceBefore;

    public IllegalArgumentByIdException(String message, long productId, long stockId, long customerId, long quantity) {
        this(message, productId, stockId, customerId, quantity, null);
    }

    public IllegalArgumentByIdException(String message, long productId, long stockId, long customerId, long quantity, Long balanceBefore) {
        super(String.format("error.products.reserve.%s p=%d s=%d c=%d quantity=%d%s",
                message, productId, stockId, customerId, quantity,
                balanceBefore != null ? String.format(" balanceBefore=%d", balanceBefore) : ""));
        this.message = "error.products.reserve." + message;
        this.productId = productId;
        this.stockId = stockId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.balanceBefore = balanceBefore;
    }
}
