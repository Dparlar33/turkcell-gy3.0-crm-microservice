package com.turkcell.crm.accountService.entities;


import com.turkcell.crm.accountService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    private String accountNumber;
    private String accountName;
    private int customerId;
    private boolean status;
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
}
