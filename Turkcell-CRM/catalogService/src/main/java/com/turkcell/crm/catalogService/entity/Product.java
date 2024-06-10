package com.turkcell.crm.catalogService.entity;

import com.turkcell.crm.catalogService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private double price;
    private int unitInStock;

    @OneToMany(mappedBy = "product")
    private List<ProductProperty> properties = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

}




