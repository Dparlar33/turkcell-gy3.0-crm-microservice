package com.turkcell.crm.catalogService.entity;

import com.turkcell.crm.catalogService.core.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_properties")
public class ProductProperty extends BaseEntity {
    private String key;
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}



