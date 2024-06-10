package com.turkcell.crm.catalogService.business.dtos.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private int id;
    private String name;
    private String description;
    private double price;
    private int unitInStock;
    private int catalogId;

}
