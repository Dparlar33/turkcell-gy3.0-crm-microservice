package com.turkcell.crm.salesService.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductConfig{
    private int productId;
    private String productTypeKey;
    private String productTypeValue;
}
