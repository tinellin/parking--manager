package br.unesp.parking.manager.api.exception;

public class CodeUniqueViolationException extends RuntimeException {
    public CodeUniqueViolationException(String msg) {
        super(msg);
    }
}
