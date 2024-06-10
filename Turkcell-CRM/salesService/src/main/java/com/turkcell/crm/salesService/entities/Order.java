package com.turkcell.crm.salesService.entities;

import com.turkcell.crm.salesService.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "orders")
public class Order extends BaseEntity {

    @Field(name = "totalPrice")
    private double totalPrice;

    @Field(name = "addressId")
    private int addressId;

    @Field(name = "accountId")
    private int accountId;

    @Field(name = "orderItems")
    private List<OrderItem> orderItems;

    @Field(name = "customerId")
    private int customerId;
}
