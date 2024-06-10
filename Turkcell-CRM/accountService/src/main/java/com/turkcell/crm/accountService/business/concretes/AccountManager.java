package com.turkcell.crm.accountService.business.concretes;

import com.turkcell.crm.accountService.business.abstracts.AccountService;
import com.turkcell.crm.accountService.business.abstracts.AccountTypeService;
import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.order.OrderAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import com.turkcell.crm.accountService.business.rules.AccountBusinessRules;
import com.turkcell.crm.accountService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.accountService.dataAccess.AccountRepository;
import com.turkcell.crm.accountService.entities.Account;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountManager implements AccountService {
    private AccountRepository accountRepository;
    private ModelMapperService modelMapperService;
    private AccountBusinessRules accountBusinessRules;
    private AccountTypeService accountTypeService;

    @Override
    public CreatedAccountResponse add(CreateAccountRequest createAccountRequest) {

        this.accountTypeService.getById(createAccountRequest.getAccountTypeId());
        this.accountBusinessRules.isCustomerExistById(createAccountRequest.getCustomerId());

        Account account = this.modelMapperService.forRequest().map(createAccountRequest, Account.class);
        account.setStatus(false);
        account.setOrderId(0);
        this.accountRepository.save(account);

        return this.modelMapperService.forResponse().map(account, CreatedAccountResponse.class);
    }

    @Override
    public GetByIdAccountResponse getById(int id) {

        this.accountBusinessRules.isAccountAlreadyDeleted(id);

        Optional<Account> account = this.accountRepository.findById(id);

        return this.modelMapperService.forResponse()
                .map(account.get(), GetByIdAccountResponse.class);
    }

    @Override
    public List<GetAllAccountResponse> getAll() {

        List<Account> accounts = this.accountRepository.findByDeletedDateIsNull();

        return accounts.stream().map(account -> this.modelMapperService.forResponse().
                map(account, GetAllAccountResponse.class)).toList();
    }

    @Override
    public UpdatedAccountResponse update(UpdateAccountRequest updateAccountRequest) {

        this.accountBusinessRules.isAccountExistById(updateAccountRequest.getId());
        this.accountBusinessRules.isAccountAlreadyDeleted(updateAccountRequest.getId());

        Account account = this.modelMapperService.forRequest().
                map(updateAccountRequest, Account.class);

        return this.modelMapperService.forResponse().
                map(this.accountRepository.save(account), UpdatedAccountResponse.class);
    }


    @Override
    public void delete(int id) {

        Account account = this.accountBusinessRules.isAccountAlreadyDeleted(id);
        account.setDeletedDate(LocalDateTime.now());

        this.accountRepository.save(account);
    }

    @Override
    public void setAccountStatus(OrderAccountResponse orderAccountResponse) {

        this.accountBusinessRules.isAccountExistById(orderAccountResponse.getAccountId());

        Account account = this.accountRepository.findById(orderAccountResponse.getAccountId()).orElse(null);

        account.setOrderId(orderAccountResponse.getOrderId());
        account.setStatus(true);

        this.accountRepository.save(account);
    }

}
