package com.turkcell.crm.catalogService.business.dtos.response.productProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdProductPropertyResponse {
    private int id;
    private int productId;
    private String productProperty;
    private String productPropertyDetail;
}
