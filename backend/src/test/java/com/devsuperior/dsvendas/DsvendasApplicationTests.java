package com.devsuperior.dsvendas;

import static org.assertj.core.api.Assertions.assertThat;

import com.devsuperior.dsvendas.controllers.SaleController;
import com.devsuperior.dsvendas.controllers.SellerController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DsvendasApplicationTests {

	@Autowired
	private SellerController sellerController;

	@Autowired
	private SaleController saleController;

	@Test
	void contextLoads() {
		assertThat(sellerController).isNotNull();
		assertThat(saleController).isNotNull();
	}

}
