package com.devsuperior.dsvendas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.devsuperior.dsvendas.dtos.SellerDTO;
import com.devsuperior.dsvendas.entities.Seller;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.repositories.SellerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SellerServiceTest {

    private static final List<Seller> SELLERS = Arrays.asList(
        new Seller(1L, "Vergil"),
        new Seller(2L, "Dante"),
        new Seller(2L, "Maria")
    );

    private List<SellerDTO> sellerDTOs;
    
    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAListOfSellers() {
        givenSellerRepositoryReturnsAListOfSellers();
        whenWeCallSellerServiceFindAll();
        thenWeExpectSellerRepositoryFindAllToHaveBeenCalledOnce();
        thenWeExpectToHaveAListOfSellerDTOs();
    }

    @Test
    public void shouldThrowInternalServerErrorException() {
        givenSellerRepositoryThrowsInternalServerErrorException();
        thenWeExpectSellerRepositoryFindAllToHaveNeverBeenCalled();
        thenWeExpectAInternalServerErrorException();
    }
    
    private void givenSellerRepositoryReturnsAListOfSellers() {
        when(sellerRepository.findAll()).thenReturn(SELLERS);
    }
    
    private void givenSellerRepositoryThrowsInternalServerErrorException() {
        when(sellerRepository.findAll()).thenThrow(new RuntimeException("Generic exception"));
    }
    
    private void whenWeCallSellerServiceFindAll() {
        sellerDTOs = sellerService.findAll();
    }
    
    private void thenWeExpectToHaveAListOfSellerDTOs() {
        assertNotNull(sellerDTOs);
        assertEquals(sellerDTOs.size(), SELLERS.size());
    }
    
    private void thenWeExpectSellerRepositoryFindAllToHaveBeenCalledOnce() {
        verify(sellerRepository, times(1)).findAll();
    }
    
    private void thenWeExpectSellerRepositoryFindAllToHaveNeverBeenCalled() {
        verify(sellerRepository, never()).findAll();
    }
    
    private void thenWeExpectAInternalServerErrorException() {
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> 
        sellerService.findAll());
        assertEquals("Generic Exception", exception.getHttpDescription());
        assertEquals(500, exception.getStatus());
        assertEquals("Internal Server Error", exception.getHttpError());
    }
}
