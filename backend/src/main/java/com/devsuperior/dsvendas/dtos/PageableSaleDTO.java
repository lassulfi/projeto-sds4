package com.devsuperior.dsvendas.dtos;

import org.springframework.data.domain.Page;

public class PageableSaleDTO {
    
    private Page<SaleDTO> pageable;

    public PageableSaleDTO() {
    }

    public PageableSaleDTO(Page<SaleDTO> pageable) {
        this.pageable = pageable;
    }

    public Page<SaleDTO> getPageable() {
        return pageable;
    }

    public void setPageable(Page<SaleDTO> pageable) {
        this.pageable = pageable;
    }
}
