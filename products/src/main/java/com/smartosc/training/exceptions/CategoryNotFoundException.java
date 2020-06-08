package com.smartosc.training.exceptions;

/**
 * products
 *
 * @author thanhttt
 * @created_at 08/06/2020 - 9:57 AM
 */
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
