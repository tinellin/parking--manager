package br.unesp.parking.manager.api.exception;

public class LicensePlateUniqueViolationException extends RuntimeException{
    public LicensePlateUniqueViolationException(String message) {
        super(message);
    }
}
