package com.turkcell.turkcellcrm.customerService.entity;


import com.turkcell.turkcellcrm.customerService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "customer_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends BaseEntity {

    private String email;
    private String mobilePhoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;


}
