package com.devsuperior.dsvendas.dtos;

import java.io.Serializable;

import com.devsuperior.dsvendas.entities.Seller;

import io.swagger.annotations.ApiModelProperty;

public class SaleSumDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "Seller name", value = "John Doe")
    private String name;

    @ApiModelProperty(name = "Sum of all sales by seller", value = "1599.99")
    private Double sum;
    
    public SaleSumDTO() {
    }

    public SaleSumDTO(Seller seller, Double sum) {
        this.name = seller.getName();
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
