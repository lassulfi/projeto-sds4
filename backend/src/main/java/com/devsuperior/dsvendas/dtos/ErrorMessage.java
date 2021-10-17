package com.devsuperior.dsvendas.dtos;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dsvendas.exceptions.HttpError;

import io.swagger.annotations.ApiModelProperty;

public class ErrorMessage {

    @ApiModelProperty(value = "Error type", required = true, example = "Bad Request")
    private String type;

    @ApiModelProperty(value = "Error description", required = true, example = "There are some problems with the request body")
    private String description;

    @ApiModelProperty(value = "List of errors causes", example = "[\"The property 'test' cannot be null.\"]")
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
