package com.smartosc.training.exceptions;

/**
 * products
 *
 * @author thanhttt
 * @created_at 08/06/2020 - 9:57 AM
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
