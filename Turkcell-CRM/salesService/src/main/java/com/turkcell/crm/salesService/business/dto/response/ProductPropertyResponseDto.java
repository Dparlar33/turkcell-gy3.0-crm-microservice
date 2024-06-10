package com.turkcell.crm.salesService.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductPropertyResponseDto {
    private int productId;
    private String key;
    private String value;
}
