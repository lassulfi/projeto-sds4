package com.devsuperior.dsvendas.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import com.devsuperior.dsvendas.entities.Sale;

import io.swagger.annotations.ApiModelProperty;

public class SaleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="Sale ID", example = "99")
    private Long id;
    
    @ApiModelProperty(value="number of visits made by a seller", example = "1000")
    private Integer visited;
    
    @ApiModelProperty(value = "number of deals closed by a seller", example = "50")
    private Integer deals;
    
    @ApiModelProperty(value = "total amount sold", example = "550.00")
    private Double amount;

    @ApiModelProperty(value = "Date of the sale", example = "01-01-2021")
    private LocalDate date;

    private SellerDTO seller;

    public SaleDTO() {
    }

    public SaleDTO(Long id, Integer visited, Integer deals, Double amount, LocalDate date, SellerDTO seller) {
        this.id = id;
        this.visited = visited;
        this.deals = deals;
        this.amount = amount;
        this.date = date;
        this.seller = seller;
    }

    public SaleDTO(Sale entity) {
        this.id = entity.getId();
        this.visited = entity.getVisited();
        this.deals = entity.getDeals();
        this.amount = entity.getAmount();
        this.date = entity.getDate();
        this.seller = new SellerDTO(entity.getSeller());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public Integer getDeals() {
        return deals;
    }

    public void setDeals(Integer deals) {
        this.deals = deals;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
