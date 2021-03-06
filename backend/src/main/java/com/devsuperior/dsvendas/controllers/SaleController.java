package com.devsuperior.dsvendas.controllers;

import java.util.List;

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

@RestController
@RequestMapping(value = "/sales")
public class SaleController {
    
    @Autowired
    private SaleService service;

    @GetMapping
    public Page<SaleDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);   
    }

    @GetMapping(value = "/amount-by-seller")
    public List<SaleSumDTO> amountGroupedBySeller() {
        return service.amountGroupedBySeller();
    }

    @GetMapping(value = "/success")
    public List<SaleSuccessDTO> successGroupedBySeller() {
        return service.successGroupedBySeller();
    }
}
