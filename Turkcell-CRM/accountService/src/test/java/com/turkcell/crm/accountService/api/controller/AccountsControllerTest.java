package com.turkcell.crm.accountService.api.controller;

import com.turkcell.crm.accountService.business.abstracts.AccountService;
import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountsControllerTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountsController;


    @Test
    public void testAddAccount() {
        CreateAccountRequest request = new CreateAccountRequest();
        CreatedAccountResponse expectedResponse = new CreatedAccountResponse();
        Mockito.when(accountService.add(request)).thenReturn(expectedResponse);

        CreatedAccountResponse response = accountsController.add(request);

        assertEquals(expectedResponse, response);
        verify(accountService, times(1)).add(request);
    }

    @Test
    void getAll() {
        List<GetAllAccountResponse> expectedResponse = Arrays.asList(new GetAllAccountResponse(), new GetAllAccountResponse());
        when(accountService.getAll()).thenReturn(expectedResponse);

        List<GetAllAccountResponse> actualResponse = accountsController.getAll();

        assertEquals(expectedResponse.size(),actualResponse.size());
        verify(accountService,times(1)).getAll();

    }
    @Test
    void getById() {
        int id=1;
        GetByIdAccountResponse expectedResponse =new GetByIdAccountResponse();
        when(accountService.getById(id)).thenReturn(expectedResponse);

        GetByIdAccountResponse actualResponse=accountsController.getById(id);

        assertEquals(expectedResponse,actualResponse);
        verify(accountService,times(1)).getById(id);
    }

    @Test
    void update() {
        UpdateAccountRequest updateAccountRequest=new UpdateAccountRequest();
        UpdatedAccountResponse expectedResponse =new UpdatedAccountResponse();
        when(accountService.update(updateAccountRequest)).thenReturn(expectedResponse);

        UpdatedAccountResponse response=accountsController.update(updateAccountRequest);

        assertEquals(expectedResponse,response);
        verify(accountService,times(1)).update(updateAccountRequest);

    }

    @Test
    void delete() {
        int id=1;
        accountService.delete(id);

        verify(accountService,times(1)).delete(id);
    }
}