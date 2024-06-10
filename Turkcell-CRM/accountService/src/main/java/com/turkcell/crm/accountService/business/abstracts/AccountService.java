package com.turkcell.crm.accountService.business.abstracts;

import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.order.OrderAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import org.reactivestreams.Publisher;

import java.util.List;

public interface AccountService {
   CreatedAccountResponse add(CreateAccountRequest createAccountRequest);
    GetByIdAccountResponse getById(int id);
    List<GetAllAccountResponse> getAll();
    UpdatedAccountResponse update(UpdateAccountRequest updateAccountRequest);
    void delete(int id);
    void setAccountStatus(OrderAccountResponse orderAccountResponse);
}
