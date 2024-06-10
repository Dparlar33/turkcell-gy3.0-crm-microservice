package com.turkcell.crm.catalogService.business.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPropertyResponseDto {
    private int productId;
    private String key;
    private String value;
}
