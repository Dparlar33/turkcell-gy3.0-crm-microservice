package com.turkcell.crm.catalogService.entity;

import com.turkcell.crm.catalogService.core.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalogs")
public class Catalog extends BaseEntity {

    private String catalogName;

    @OneToMany(mappedBy = "catalog")
    private List<Product> products;
}
