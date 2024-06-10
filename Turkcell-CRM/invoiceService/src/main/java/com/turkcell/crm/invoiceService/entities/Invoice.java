package com.turkcell.crm.invoiceService.entities;

import com.turkcell.crm.invoiceService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    private int accountId;
    private int customerId;
    private double price;
    private int addressId;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
