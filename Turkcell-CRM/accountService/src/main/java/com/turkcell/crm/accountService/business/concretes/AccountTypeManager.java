package com.turkcell.crm.accountService.business.concretes;

import com.turkcell.crm.accountService.business.abstracts.AccountTypeService;
import com.turkcell.crm.accountService.business.dtos.request.accountType.CreateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.request.accountType.UpdateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.response.accountType.CreatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetAllAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetByIdAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.UpdatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.rules.AccountTypeBusinessRules;
import com.turkcell.crm.accountService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.accountService.dataAccess.AccountTypeRepository;
import com.turkcell.crm.accountService.entities.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountTypeManager implements AccountTypeService {

    private AccountTypeRepository accountTypeRepository;
    private ModelMapperService modelMapperService;
    private AccountTypeBusinessRules accountTypeBusinessRules;

    @Override
    public CreatedAccountTypeResponse add(CreateAccountTypeRequest createAccountTypeRequest) {

        AccountType accountType =this.modelMapperService.forRequest().map(createAccountTypeRequest, AccountType.class);

        this.accountTypeRepository.save(accountType);

        return this.modelMapperService.forResponse().map(accountType, CreatedAccountTypeResponse.class);
    }

    @Override
    public List<GetAllAccountTypeResponse> getAll() {

        List<AccountType> accountType = this.accountTypeRepository.findByDeletedDateIsNull();

        return accountType.stream().map(accountType1 -> this.modelMapperService.forResponse().
                map(accountType1, GetAllAccountTypeResponse.class)).toList();
    }

    @Override
    public GetByIdAccountTypeResponse getById(int id) {

        this.accountTypeBusinessRules.isAccountTypeAlreadyDeleted(id);
        this.accountTypeBusinessRules.isAccountTypeExistById(id);
        AccountType accountType =this.accountTypeRepository.findById(id).orElse(null);

        GetByIdAccountTypeResponse getByIdAccountTypeResponse=this.modelMapperService.forResponse()
                .map(accountType, GetByIdAccountTypeResponse.class);
        return getByIdAccountTypeResponse;

    }

    @Override
    public UpdatedAccountTypeResponse update(UpdateAccountTypeRequest updateAccountTypeRequest) {

        this.accountTypeBusinessRules.isAccountTypeAlreadyDeleted(updateAccountTypeRequest.getId());
        this.accountTypeBusinessRules.isAccountTypeExistById(updateAccountTypeRequest.getId());

        return this.modelMapperService.forResponse().
                map(this.accountTypeRepository.save(this.modelMapperService.forRequest().
                        map(updateAccountTypeRequest, AccountType.class)), UpdatedAccountTypeResponse.class);
    }

    @Override
    public void delete(int id) {

        AccountType accountType = this.accountTypeBusinessRules.isAccountTypeAlreadyDeleted(id);
        accountType.setDeletedDate(LocalDateTime.now());

        this.accountTypeRepository.save(accountType);
    }

}

