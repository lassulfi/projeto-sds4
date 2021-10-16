package com.devsuperior.dsvendas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.devsuperior.dsvendas.controllers.SaleController;
import com.devsuperior.dsvendas.dtos.SaleDTO;
import com.devsuperior.dsvendas.dtos.SaleSuccessDTO;
import com.devsuperior.dsvendas.dtos.SaleSumDTO;
import com.devsuperior.dsvendas.dtos.SellerDTO;
import com.devsuperior.dsvendas.entities.Seller;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.services.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SaleController.class)
public class SaleControllerTest {
    
    private static final String FIND_ALL_URL = "/sales";

    private static final String AMOUNT_BY_SELLER_URL = "/sales/amount-by-seller";

    private static final String SUCCESS_GROUPED_BY_SELLER_URL = "/sales/success";

    private static final String APPLICATION_JSON = "application/json";


    private static final List<SaleDTO> SALES_DTO = Arrays.asList(
        new SaleDTO(1L, 10, 10, 1000.00, LocalDate.of(2021, 1, 1), new SellerDTO(1L, "Dante")),
        new SaleDTO(2L, 20, 20, 2000.00, LocalDate.of(2021, 1, 2), new SellerDTO(2L, "Vergil")),
        new SaleDTO(3L, 30, 30, 2000.00, LocalDate.of(2021, 1, 3), new SellerDTO(3L, "Maria"))
    );

    private static final Page<SaleDTO> PAGEABLE_SALES = new PageImpl<>(SALES_DTO);

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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;    

    private MvcResult mvcResult;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        mapper = new ObjectMapper()
            .findAndRegisterModules()
            .setDateFormat(dateFormat);
    }

    @Test
    public void getPageableListOfSalesReturnsWithSuccess() throws Exception {
        givenSaleServiceFindAllReturnsAPageableListOfSales();
        whenWeCallFindAllOperation();
        thenWeExpectA200Status();
        thenWeExpectAPageableListOfSales();
    }

    @Test
    public void getPageableListOfSalesThrowsAnInternalServerError() throws Exception {
        givenSaleServiceFindAllThrowsAnInternalServerErrorException();
        whenWeCallFindAllOperation();
        thenWeExpectA500Status();
    }

    @Test
    public void getAmountBySellerReturnsWithSuccess() throws Exception {
        givenSaleServiceAmountBySellerReturnsAListOfSalesAmountGroupedBySeller();
        whenWeCallAmountBySellerOperation();
        thenWeExpectA200Status();
        thenWeExpectAListOfSalesAmountGroupedBySeller();
    }

    @Test
    public void getAmountGroupedBySellerThrowsAnInternalServerError() throws Exception {
        givenSaleServiceAmountBySellerThrowsInternalServerErrorException();
        whenWeCallAmountBySellerOperation();
        thenWeExpectA500Status();
    }

    @Test
    public void getSalesSuccessGroupedBySellerReturnsWithSuccess() throws Exception {
        givenSaleServiceSuccessGroupedBySellerReturnsAListOfSalesSuccessGroupedBySeller();
        whenWeCallSuccessGroupedBySellerOperation();
        thenWeExpectA200Status();
        thenWeExpectAListOfSalesSuccessGroupedBySeller();
    }

    @Test
    public void getSalesSuccessGroupedBySellerThrowsInternalServerError() throws Exception {
        givenSaleServiceSuccessGroupedBySellerThrowsAnInternalServerErrorException();
        whenWeCallSuccessGroupedBySellerOperation();
        thenWeExpectA500Status();
    }

    private void givenSaleServiceSuccessGroupedBySellerThrowsAnInternalServerErrorException() {
        when(saleService.successGroupedBySeller()).thenThrow(new InternalServerErrorException("Unknown Error"));
    }

    private void givenSaleServiceSuccessGroupedBySellerReturnsAListOfSalesSuccessGroupedBySeller() {
        when(saleService.successGroupedBySeller()).thenReturn(SUCCESS_GROUPED_BY_SELLER);
    }

    private void givenSaleServiceAmountBySellerThrowsInternalServerErrorException() {
        when(saleService.amountGroupedBySeller())
            .thenThrow(new InternalServerErrorException("Unknown Error"));
    }

    private void givenSaleServiceAmountBySellerReturnsAListOfSalesAmountGroupedBySeller() {
        when(saleService.amountGroupedBySeller()).thenReturn(AMOUNT_GROUPED_BY_SELLER);
    }

    private void givenSaleServiceFindAllThrowsAnInternalServerErrorException() {
        when(saleService.findAll(any(Pageable.class)))
            .thenThrow(new InternalServerErrorException("Unknown Exception"));
    }

    private void givenSaleServiceFindAllReturnsAPageableListOfSales() {
        when(saleService.findAll(any(Pageable.class))).thenReturn(PAGEABLE_SALES);
    }

    private void whenWeCallFindAllOperation() throws Exception {
        Pageable pageRequest = PageRequest.of(0, 1);
        mvcResult = mockMvc.perform(get(FIND_ALL_URL, pageRequest).content(APPLICATION_JSON))
            .andReturn();
    }
    
    private void whenWeCallAmountBySellerOperation() throws Exception {
        mvcResult = mockMvc.perform(get(AMOUNT_BY_SELLER_URL).content(APPLICATION_JSON))
            .andReturn();
    }

    private void whenWeCallSuccessGroupedBySellerOperation() throws Exception {
        mvcResult = mockMvc.perform(get(SUCCESS_GROUPED_BY_SELLER_URL).content(APPLICATION_JSON))
            .andReturn();
    }

    private void thenWeExpectA200Status() {
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    private void thenWeExpectA500Status() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
    }

    private void thenWeExpectAPageableListOfSales() throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        String expectedResponse = mapper.writeValueAsString(PAGEABLE_SALES);
        assertEquals(expectedResponse, response);
    }

    private void thenWeExpectAListOfSalesAmountGroupedBySeller() throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        String expectedResponse = mapper.writeValueAsString(AMOUNT_GROUPED_BY_SELLER);
        assertEquals(expectedResponse, response);
    }

    private void thenWeExpectAListOfSalesSuccessGroupedBySeller() throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        String expectedResponse = mapper.writeValueAsString(SUCCESS_GROUPED_BY_SELLER);
        assertEquals(expectedResponse, response);
    }
}
