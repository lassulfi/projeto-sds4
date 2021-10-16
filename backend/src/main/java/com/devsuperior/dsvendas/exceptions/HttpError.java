package com.devsuperior.dsvendas.exceptions;

public interface HttpError {
    int getStatus();
    String getHttpError();
    String getHttpDescription();
}
