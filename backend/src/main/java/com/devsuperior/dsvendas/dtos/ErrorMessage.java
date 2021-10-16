package com.devsuperior.dsvendas.dtos;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dsvendas.exceptions.HttpError;

public class ErrorMessage {
    private String type;

    private String description;

    private Set<String> errors = new HashSet<>();

    public ErrorMessage() {}

    public ErrorMessage(HttpError error) {
        this.type = error.getHttpError();
        this.description = error.getHttpDescription();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getErrors() {
        return errors;
    }    
}
