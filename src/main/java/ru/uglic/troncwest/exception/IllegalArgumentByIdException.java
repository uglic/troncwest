package ru.uglic.troncwest.exception;

public class IllegalArgumentByIdException extends IllegalArgumentException {
    public IllegalArgumentByIdException(String message, long productId, long stockId, long customerId, long quantity) {
        super(String.format("error.products.reserve.%s p=%d s=%d c=%d quantity=%d",
                message, productId, stockId, customerId, quantity));
    }

    public IllegalArgumentByIdException(String message, long productId, long stockId, long customerId, long quantity, long balanceBefore) {
        super(String.format("error.products.reserve.%s p=%d s=%d c=%d quantity=%d before=%d",
                message, productId, stockId, customerId, quantity, balanceBefore));
    }
}
