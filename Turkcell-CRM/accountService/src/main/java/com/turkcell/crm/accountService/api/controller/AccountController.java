package com.turkcell.crm.accountService.api.controller;

import com.turkcell.crm.accountService.business.abstracts.AccountService;
import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.order.OrderAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import com.turkcell.crm.accountService.business.messages.AccountMessages;
import com.turkcell.crm.accountService.core.business.abstracts.MessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountservice/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;
    private MessageService messageService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedAccountResponse add(@Valid @RequestBody CreateAccountRequest createAccountRequest){

        return this.accountService.add(createAccountRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllAccountResponse> getAll(){
        return this.accountService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdAccountResponse getById(@PathVariable int id){
        return this.accountService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedAccountResponse update(@Valid @RequestBody UpdateAccountRequest updateAccountRequest){

        return this.accountService.update(updateAccountRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable int id){
        this.accountService.delete(id);
        return messageService.getMessage(AccountMessages.ACCOUNT_DELETION_SUCCESSFUL);
    }

    @PostMapping("/setStatus")
    void setAccountStatusByOrderId(@RequestBody OrderAccountResponse orderAccountResponse){
        this.accountService.setAccountStatus(orderAccountResponse);
    }
}
