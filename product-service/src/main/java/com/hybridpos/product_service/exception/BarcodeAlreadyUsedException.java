package com.hybridpos.product_service.exception;

public class BarcodeAlreadyUsedException extends RuntimeException {
    public BarcodeAlreadyUsedException(String message) {
        super(message);
    }
}
