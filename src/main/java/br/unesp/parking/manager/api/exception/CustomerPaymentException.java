package br.unesp.parking.manager.api.exception;

public class CustomerPaymentException extends RuntimeException {
    public CustomerPaymentException(String msg) {
        super(msg);
    }
}
