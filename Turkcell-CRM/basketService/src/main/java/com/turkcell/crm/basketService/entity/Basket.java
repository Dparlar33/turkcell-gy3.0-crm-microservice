package com.turkcell.crm.basketService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Basket  implements Serializable {

    private String id;
    private int accountId;
    private int customerId;
    private double totalPrice;
    private List<BasketItem> basketItems;

    public Basket(){
        this.id= UUID.randomUUID().toString();
        this.basketItems = new ArrayList<>();
    }
}
