package com.turkcell.turkcellcrm.searchService.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllCatalogRequest {
    private int catalogId;
    private int productId;
    private String productName;
}
