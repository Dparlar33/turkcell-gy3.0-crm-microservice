package com.turkcell.crm.accountService.dataAccess;


import com.turkcell.crm.accountService.entities.Account;
import com.turkcell.crm.accountService.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {

    Optional<AccountType> findAccountTypeById(int id);
    List<AccountType> findByDeletedDateIsNull();

}
