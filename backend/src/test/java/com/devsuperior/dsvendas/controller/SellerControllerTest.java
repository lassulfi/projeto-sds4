package com.devsuperior.dsvendas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import com.devsuperior.dsvendas.controllers.SellerController;
import com.devsuperior.dsvendas.dtos.SellerDTO;
import com.devsuperior.dsvendas.exceptions.InternalServerErrorException;
import com.devsuperior.dsvendas.services.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.HttpStatus;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    private static final String FIND_ALL_URL = "/sellers";

    private static final List<SellerDTO> SELLERS = Arrays.asList(
        new SellerDTO(1L, "Mickey"),
        new SellerDTO(2L, "Donald"),
        new SellerDTO(3L, "Pluto")
    );
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

   private MvcResult mvcResult;

   private ObjectMapper mapper;

   @BeforeEach
   public void setup() {
       mapper = new ObjectMapper();
   }

    @Test
    public void getListOfSellersReturnsWithSuccess() throws Exception {
        givenSellerServiceFindAllReturnsAListOfSellers();
        whenWeCallFindAllOperations();
        thenWeExpectA200Status();
        thenWeExpectAListOfSellers();
    }

    @Test
    public void getListOfSellersReturnsInternalServerError() throws Exception {
        givenSellerServiceThrownsAnInternalServerErrorException();
        whenWeCallFindAllOperations();
        thenWeExpectA500Status();
    }

    private void givenSellerServiceFindAllReturnsAListOfSellers() {
        when(sellerService.findAll()).thenReturn(SELLERS);
    }

    private void givenSellerServiceThrownsAnInternalServerErrorException() {
        when(sellerService.findAll()).thenThrow(new InternalServerErrorException("Unexpected Exception"));
    }

    private void whenWeCallFindAllOperations() throws Exception {
        mvcResult = mockMvc
                        .perform(get(FIND_ALL_URL).content("application/json"))
                        .andReturn();
    }

    private void thenWeExpectA200Status() {
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    private void thenWeExpectA500Status() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
    }

    private void thenWeExpectAListOfSellers() throws UnsupportedEncodingException, JsonProcessingException {
        String response = mvcResult.getResponse().getContentAsString();
        String expectedResponse = mapper.writeValueAsString(SELLERS);
        assertEquals(expectedResponse, response);
    }
}
