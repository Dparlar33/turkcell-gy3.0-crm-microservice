package com.turkcell.turkcellcrm.searchService.entities;

import com.turkcell.turkcellcrm.searchService.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class Product extends BaseEntity {

    @Field(name = "productId")
    private int productId;

    @Field(name = "catalogId")
    private int catalogId;

    @Field(name = "name")
    private String name;

    @Field(name = "price")
    private double price;
}
