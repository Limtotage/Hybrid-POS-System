package com.hybridpos.cash_service.exception;

public class CashRegisterNotFoundException extends RuntimeException {

    public CashRegisterNotFoundException(String message) {
        super(message);
    }
}
