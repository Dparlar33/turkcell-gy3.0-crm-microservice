package com.turkcell.crm.accountService.business.abstracts;

import com.turkcell.crm.accountService.business.dtos.request.accountType.CreateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.request.accountType.UpdateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.response.accountType.CreatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetAllAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetByIdAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.UpdatedAccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    CreatedAccountTypeResponse add(CreateAccountTypeRequest createAccountTypeRequest);
    List<GetAllAccountTypeResponse> getAll();
    GetByIdAccountTypeResponse getById(int id);
    UpdatedAccountTypeResponse update(UpdateAccountTypeRequest updateAccountTypeRequest);
    void delete(int id);
}
