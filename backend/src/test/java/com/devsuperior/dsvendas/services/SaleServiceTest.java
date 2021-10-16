package com.devsuperior.dsvendas.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.devsuperior.dsvendas.dtos.SaleDTO;
import com.devsuperior.dsvendas.dtos.SaleSuccessDTO;
import com.devsuperior.dsvendas.dtos.SaleSumDTO;
import com.devsuperior.dsvendas.entities.Sale;
import com.devsuperior.dsvendas.entities.Seller;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.repositories.SaleRepository;
import com.devsuperior.dsvendas.repositories.SellerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SaleServiceTest {

    private static final List<Seller> SELLERS = Arrays.asList(
        new Seller(1L, "Vergil"),
        new Seller(2L, "Dante"),
        new Seller(2L, "Maria")
    );
    private static final List<Sale> SALES = Arrays.asList(
        new Sale(1L, 10, 10, 1000.00, LocalDate.of(2021, 1, 1), new Seller(1L, "Dante")),
        new Sale(2L, 20, 20, 2000.00, LocalDate.of(2021, 1, 2), new Seller(2L, "Vergil")),
        new Sale(3L, 30, 30, 2000.00, LocalDate.of(2021, 1, 3), new Seller(3L, "Maria"))
    );
    private static final List<SaleSumDTO> AMOUNT_GROUPED_BY_SELLER = Arrays.asList(
      new SaleSumDTO(new Seller(1L, "Dante"), 1500.0),  
      new SaleSumDTO(new Seller(2L, "Vergil"), 2000.0),  
      new SaleSumDTO(new Seller(3L, "Maria"), 3000.0)  
    );

    private static final List<SaleSuccessDTO> SUCCESS_GROUPED_BY_SELLER = Arrays.asList(
        new SaleSuccessDTO(new Seller(1L, "Dante"), 100L, 50L),
        new SaleSuccessDTO(new Seller(2L, "Vergil"), 300L, 100L),
        new SaleSuccessDTO(new Seller(3L, "Maria"), 500L, 300L)
    );

    private static final Page<Sale> PAGEABLE_SALES = new PageImpl<>(SALES);

    private Page<SaleDTO> salesDTO;

    private List<SaleSumDTO> salesSumDTO;

    private List<SaleSuccessDTO> salesSuccessDTO;
    
    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private Page<Mock> pageSale;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAPageableListOfSalesWithContent() {
        givenSellerRepositoryPreloadsInMemoryAListOfSellers();
        givenSaleRepositoryReturnsAPageableListOfSalesWithContent();
        whenWeCallSaleServiceFindAll();
        thenWeExpectToHaveAPageableListOfSalesWithContent();
        thenWeExpectSellerRepositoryFindAllToBeCalledOnce();
        thenWeExpectSaleRepositoryFindAllToBeCalledOnce();
    }

    @Test
    public void shouldReturnAPageableListOfSalesWithoutContent() {
        givenSellerRepositoryPreloadsInMemoryAListOfSellers();
        givenSaleRepositoryReturnsAPageableListOfSalesWithoutContent();
        whenWeCallSaleServiceFindAll();
        thenWeExpectToHaveAListOfSalesWithoutContent();
        thenWeExpectSellerRepositoryFindAllToBeCalledOnce();
        thenWeExpectSaleRepositoryFindAllToBeCalledOnce();
    }

    @Test
    public void shouldThrownInternalServerErrorWhenCallSaleServiceFindAll() {
        givenSellerRepositoryThrowsAGenericError();
        thenWeExpectAnInternalServerErrorExceptionFromSaleServiceFindAll();
        thenWeExpectSellerRepositoryFindAllToBeCalledOnce();
        thenWeExpectSaleRepositoryFindAllToHaveNotBeenCalled();
    }

    @Test
    public void shouldReturnAListOfSalesAmountGroupedBySeller() {
        givenSaleRepositoryReturnsAListOfSalesAmountGroupedBySeller();
        whenWeCallSaleServiceAmountGroupedBySeller();
        thenWeExpectToAListOfSalesAmountGroupedBySeller();
        thenWeExpectSaleRepositoryAmountGroupedBySellerToBeCalledOnce();
    }

    @Test
    public void shouldThrowInternalServerErrorExceptionWhenCallSaleServiceAmountGroupedBySeller() {
        givenSaleRepositoryAmountGroupedBySellerThrowsAGenericException();
        thenWeExpectAnInternalServerErrorExceptionFromSaleServiceAmountGroupedBySeller();
        thenWeExpectSaleRepositoryAmountGroupedBySellerToBeCalledOnce();
    }

    @Test
    public void shouldReturnAListOfSaleSuccessGroupedBySeller() {
        givenSaleRepositoryReturnsAListOfSalesSuccessGroupedBySeller();
        whenWeCallSaleServerSuccessGroupedByBeller();
        thenWeExpectAListOfSaleSuccessGroupedBySeller();
        thenWeExpectSaleRepositorySuccessGroupedBySellerToBeCalledOnce();
    }

    @Test
    public void shouldThrowInternalServerErrorExceptionWhenCallSaleServiceSuccessGroupedBySeller() {
        givenSaleRepositorySuccessGroupedBySellerThrowsAGenericException();
        thenWeExpectAnInternalServerErrorExceptionFromSaleServiceSuccessGroupedBySeller();
        thenWeExpectSaleRepositorySuccessGroupedBySellerToBeCalledOnce();
    }

    private void givenSaleRepositorySuccessGroupedBySellerThrowsAGenericException() {
        when(saleRepository.successGroupedBySeller()).thenThrow(new RuntimeException("Generic Exception"));
    }

    private void givenSaleRepositoryReturnsAListOfSalesSuccessGroupedBySeller() {
        when(saleRepository.successGroupedBySeller()).thenReturn(SUCCESS_GROUPED_BY_SELLER);
    }

    private void givenSaleRepositoryAmountGroupedBySellerThrowsAGenericException() {
        when(saleRepository.amountGroupedBySeller()).thenThrow(new RuntimeException("Generic Exception"));
    }

    private void givenSaleRepositoryReturnsAListOfSalesAmountGroupedBySeller() {
        when(saleRepository.amountGroupedBySeller()).thenReturn(AMOUNT_GROUPED_BY_SELLER);
    }

    private void givenSellerRepositoryThrowsAGenericError() {
        when(sellerRepository.findAll()).thenThrow(new RuntimeException("Generic Exception"));
    }

    private void givenSaleRepositoryReturnsAPageableListOfSalesWithoutContent() {
        List<Sale> emptyList = new ArrayList<>();
        Page<Sale> emptyContentPage = new PageImpl<>(emptyList);
        when(saleRepository.findAll(any(Pageable.class))).thenReturn(emptyContentPage);
    }

    private void givenSellerRepositoryPreloadsInMemoryAListOfSellers() {
        when(sellerRepository.findAll()).thenReturn(SELLERS);
    }
    
    private void givenSaleRepositoryReturnsAPageableListOfSalesWithContent() {
        when(saleRepository.findAll(any(Pageable.class))).thenReturn(PAGEABLE_SALES);
    }
    
    private void whenWeCallSaleServiceFindAll() {
        Pageable pageRequest = PageRequest.of(0, 1);
        salesDTO = saleService.findAll(pageRequest);
    }

    private void whenWeCallSaleServiceAmountGroupedBySeller() {
        salesSumDTO = saleService.amountGroupedBySeller();
    }

    private void whenWeCallSaleServerSuccessGroupedByBeller() {
        salesSuccessDTO = saleService.successGroupedBySeller();
    }
    
    private void thenWeExpectToHaveAPageableListOfSalesWithContent() {
        assertNotNull(salesDTO);
        assertEquals(PAGEABLE_SALES.getSize(), salesDTO.getSize());
        assertEquals(PAGEABLE_SALES.getTotalElements(), salesDTO.getTotalElements());
        
    }

    private void thenWeExpectToHaveAListOfSalesWithoutContent() {
        assertNotNull(salesDTO);
        assertEquals(0, salesDTO.getSize());
        assertEquals(0, salesDTO.getTotalElements());
        assertEquals(0, salesDTO.getContent().size());
    }

    private void thenWeExpectSaleRepositoryFindAllToBeCalledOnce() {
        ArgumentCaptor<Pageable> pageSpecificationArgument = ArgumentCaptor.forClass(Pageable.class);
        verify(saleRepository, times(1)).findAll(pageSpecificationArgument.capture());
    }

    private void thenWeExpectSellerRepositoryFindAllToBeCalledOnce() {
        verify(sellerRepository, times(1)).findAll();
    }

    private void thenWeExpectAnInternalServerErrorExceptionFromSaleServiceFindAll() {
        Pageable pageRequest = PageRequest.of(0, 1);

        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> 
        saleService.findAll(pageRequest));
        assertEquals("Generic Exception", exception.getHttpDescription());
        assertEquals(500, exception.getStatus());
        assertEquals("Internal Server Error", exception.getHttpError());
    }

    private void thenWeExpectSaleRepositoryFindAllToHaveNotBeenCalled() {
        verify(saleRepository, never()).findAll();
    }

    private void thenWeExpectToAListOfSalesAmountGroupedBySeller() {
        assertNotNull(salesSumDTO);
        assertEquals(3, salesSumDTO.size());
        assertEquals(AMOUNT_GROUPED_BY_SELLER, salesSumDTO);
    }

    private void thenWeExpectSaleRepositoryAmountGroupedBySellerToBeCalledOnce() {
        verify(saleRepository, times(1)).amountGroupedBySeller();
    }

    private void thenWeExpectAnInternalServerErrorExceptionFromSaleServiceAmountGroupedBySeller() {
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, 
            () -> saleService.amountGroupedBySeller());
        assertEquals("Generic Exception", exception.getHttpDescription());
        assertEquals(500, exception.getStatus());
        assertEquals("Internal Server Error", exception.getHttpError());
    }

    private void thenWeExpectAListOfSaleSuccessGroupedBySeller() {
        assertNotNull(salesSuccessDTO);
        assertEquals(3, salesSuccessDTO.size());
        assertEquals(SUCCESS_GROUPED_BY_SELLER, salesSuccessDTO);
    }

    private void thenWeExpectSaleRepositorySuccessGroupedBySellerToBeCalledOnce() {
        verify(saleRepository, times(1)).successGroupedBySeller();
    }

    private void thenWeExpectAnInternalServerErrorExceptionFromSaleServiceSuccessGroupedBySeller() {
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, 
            () -> saleService.successGroupedBySeller());
        assertEquals("Generic Exception", exception.getHttpDescription());
        assertEquals(500, exception.getStatus());
        assertEquals("Internal Server Error", exception.getHttpError());
    }
}
