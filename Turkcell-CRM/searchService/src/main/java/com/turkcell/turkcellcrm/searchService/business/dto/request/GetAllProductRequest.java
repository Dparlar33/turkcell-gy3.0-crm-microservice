package com.turkcell.turkcellcrm.searchService.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductRequest {

    private int catalogId;
    private int productId;
    private String name;

}
