package com.devsuperior.dsvendas.controllers;

import java.util.List;

import com.devsuperior.dsvendas.dtos.ErrorMessage;
import com.devsuperior.dsvendas.dtos.PageableSaleDTO;
import com.devsuperior.dsvendas.dtos.SaleDTO;
import com.devsuperior.dsvendas.dtos.SaleSuccessDTO;
import com.devsuperior.dsvendas.dtos.SaleSumDTO;
import com.devsuperior.dsvendas.services.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/sales")
@Api(value = "/sales", tags = "Sales")
public class SaleController {
    
    @Autowired
    private SaleService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns a list of sales", notes = "Returns a list of all sales.", response = PageableSaleDTO.class, produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = PageableSaleDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    public Page<SaleDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);   
    }

    @GetMapping(value = "/amount-by-seller")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns sales amount grouped by seller", notes = "Returns a list of sales amount grouped by seller", response = SaleSumDTO.class, responseContainer = "List", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SaleSumDTO[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    public List<SaleSumDTO> amountGroupedBySeller() {
        return service.amountGroupedBySeller();
    }

    @GetMapping(value = "/success")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns sales success grouped by seller", notes = "Returns a list of all sales success grouped by seller", response = SaleSuccessDTO.class, responseContainer = "List", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SaleSuccessDTO[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    public List<SaleSuccessDTO> successGroupedBySeller() {
        return service.successGroupedBySeller();
    }
}
