package com.turkcell.crm.accountService.business.rules;


import com.turkcell.crm.accountService.api.client.CustomerClient;
import com.turkcell.crm.accountService.business.messages.AccountMessages;
import com.turkcell.crm.accountService.core.business.abstracts.MessageService;
import com.turkcell.crm.accountService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.accountService.dataAccess.AccountRepository;
import com.turkcell.crm.accountService.entities.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AccountBusinessRules {

    private AccountRepository accountRepository;
    private CustomerClient customerClient;
    private MessageService messageService;

    public Account isAccountExistById(int id) {

        Optional<Account> account = this.accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new BusinessException(messageService.getMessage(AccountMessages.ACCOUNT_NOT_FOUND));
        }
        return account.get();
    }


    public Account isAccountAlreadyDeleted(int id) {

        Account account = this.isAccountExistById(id);

        if (account.getDeletedDate() != null) {
            throw new BusinessException(messageService.getMessage(AccountMessages.ACCOUNT_NOT_FOUND));
        }

        return account;
    }

    public void isCustomerExistById(int id) {

        if (!customerClient.getCustomer(id)) {
            throw new BusinessException(messageService.getMessage(AccountMessages.CUSTOMER_ID_NOT_FOUND));
        }
    }

}
