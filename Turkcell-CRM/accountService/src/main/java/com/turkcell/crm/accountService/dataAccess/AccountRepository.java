package com.turkcell.crm.accountService.dataAccess;


import com.turkcell.crm.accountService.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    List<Account> findByDeletedDateIsNull();
}
