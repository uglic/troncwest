package ru.uglic.troncwest.exception;

public class IllegalArgumentByIdException extends IllegalArgumentException {
    public IllegalArgumentByIdException(long productId, long stockId, long customerId, long quantity) {
        super(String.format("error.products.reserve.too-many p=%d s=%d c=%d quantity=%d",
                productId, stockId, customerId, quantity));
    }
}
