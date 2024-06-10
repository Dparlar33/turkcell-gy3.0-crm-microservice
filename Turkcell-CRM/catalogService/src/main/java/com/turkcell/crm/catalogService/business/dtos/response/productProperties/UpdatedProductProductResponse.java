package com.turkcell.crm.catalogService.business.dtos.response.productProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedProductProductResponse {
    private int id;
    private int productId;
    private Map<String, String> properties;
}
