package com.turkcell.turkcellcrm.customerService.entity;

import com.turkcell.turkcellcrm.customerService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City extends BaseEntity {

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "city")
    private List<Address> addresses;
}
