package com.devsuperior.dsvendas.controllers;

import java.util.List;

import com.devsuperior.dsvendas.dtos.ErrorMessage;
import com.devsuperior.dsvendas.dtos.SellerDTO;
import com.devsuperior.dsvendas.services.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/sellers")
@Api(value = "/sellers", tags = "Sellers")
public class SellerController {

    @Autowired
    private SellerService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns a list of sellers", notes = "Returns a list of all sellers", response = SellerDTO.class, responseContainer = "List", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SellerDTO[].class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
    })
    public List<SellerDTO> findAll() {
        return service.findAll();
    }
}
