package com.turkcell.crm.salesService.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetByIdOrderItemResponse {
    private int productId;
    private String productName;
}
