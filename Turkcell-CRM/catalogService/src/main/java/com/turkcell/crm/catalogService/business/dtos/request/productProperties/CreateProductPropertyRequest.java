package com.turkcell.crm.catalogService.business.dtos.request.productProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductPropertyRequest {
    private int productId;
    private String key;
    private String value;
}
