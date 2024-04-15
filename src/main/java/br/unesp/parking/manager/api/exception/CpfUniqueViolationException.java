package br.unesp.parking.manager.api.exception;

public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String msg) {
        super(msg);
    }
}
