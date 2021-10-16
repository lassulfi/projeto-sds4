package com.devsuperior.dsvendas.services;

import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.dsvendas.dtos.SellerDTO;
import com.devsuperior.dsvendas.entities.Seller;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.repositories.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    @Transactional(readOnly = true)
    public List<SellerDTO> findAll() {
        List<SellerDTO> sellersDTO;
        try {
            List<Seller> result = repository.findAll();
            sellersDTO = result.stream().map(seller -> new SellerDTO(seller)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Generic Exception");
        }

        return sellersDTO;
    }
}
