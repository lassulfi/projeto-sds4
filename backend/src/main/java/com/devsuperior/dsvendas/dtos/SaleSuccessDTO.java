package com.devsuperior.dsvendas.dtos;

import java.io.Serializable;

import com.devsuperior.dsvendas.entities.Seller;

import io.swagger.annotations.ApiModelProperty;

public class SaleSuccessDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "Seller name", value = "John Doe")
    private String name;

    @ApiModelProperty(name = "Total number of establishments visited by the seller", value = "498")
    private Long visited;

    @ApiModelProperty(name = "Total number of deals by the seller", value = "622")
    private Long deals;
    
    public SaleSuccessDTO() {
    }

    public SaleSuccessDTO(Seller seller, Long visited, Long deals) {
        name = seller.getName();
        this.visited = visited;
        this.deals = deals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVisited() {
        return visited;
    }

    public void setVisited(Long visited) {
        this.visited = visited;
    }

    public Long getDeals() {
        return deals;
    }

    public void setDeals(Long deals) {
        this.deals = deals;
    }
}
