package com.turkcell.crm.accountService.entities;


import com.turkcell.crm.accountService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_types")
public class AccountType extends BaseEntity {
    private String accountTypeName;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;
}
