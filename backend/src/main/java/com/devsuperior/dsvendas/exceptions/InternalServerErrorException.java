package com.devsuperior.dsvendas.exceptions;

public class InternalServerErrorException extends RuntimeException implements HttpError{
    private final String description;

    public InternalServerErrorException(String description) {
        super();
        this.description = description;
    }

    @Override
    public int getStatus() {
        return 500;
    }

    @Override
    public String getHttpError() {
        return "Internal Server Error";
    }

    @Override
    public String getHttpDescription() {
        return this.description;
    }
}
