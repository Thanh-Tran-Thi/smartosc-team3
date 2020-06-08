package com.smartosc.training.exceptions;

/**
 * products
 *
 * @author thanhttt
 * @created_at 08/06/2020 - 9:57 AM
 */
public class ProductDuplicateException extends RuntimeException {
    public ProductDuplicateException(String message) {
        super(message);
    }

    public ProductDuplicateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
