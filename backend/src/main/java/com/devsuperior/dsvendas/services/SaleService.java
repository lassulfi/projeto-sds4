package com.devsuperior.dsvendas.services;

import java.util.List;
import com.devsuperior.dsvendas.dtos.SaleDTO;
import com.devsuperior.dsvendas.dtos.SaleSuccessDTO;
import com.devsuperior.dsvendas.dtos.SaleSumDTO;
import com.devsuperior.dsvendas.entities.Sale;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.repositories.SaleRepository;
import com.devsuperior.dsvendas.repositories.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Autowired
    private SellerRepository sellerRepository;

    @Transactional(readOnly = true)
    public Page<SaleDTO> findAll(Pageable pageable) {
        Page<SaleDTO> page;
        try {
            sellerRepository.findAll();
    
            Page<Sale> result = repository.findAll(pageable);
            page = result.map(sale -> new SaleDTO(sale));
        } catch (Exception e) {
            throw new InternalServerErrorException("Generic Exception");
        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<SaleSumDTO> amountGroupedBySeller() {
        List <SaleSumDTO> result;
        try {
            result = repository.amountGroupedBySeller();
        } catch (Exception e) {
            throw new InternalServerErrorException("Generic Exception");
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<SaleSuccessDTO> successGroupedBySeller() {
        List <SaleSuccessDTO> result;
        try {
            result = repository.successGroupedBySeller();
        } catch (Exception e) {
            throw new InternalServerErrorException("Generic Exception");
        }
        return result;
    }
}
