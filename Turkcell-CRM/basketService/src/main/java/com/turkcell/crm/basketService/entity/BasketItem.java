package com.turkcell.crm.basketService.entity;

import com.turkcell.crm.basketService.core.entites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem implements Serializable {

    private int productId;
    private String name;
    private double price;
}
