package com.turkcell.turkcellcrm.customerService.entity;

import com.turkcell.turkcellcrm.customerService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String street;
    private String flatNumber;
    private String addressDescription;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City city;
}
