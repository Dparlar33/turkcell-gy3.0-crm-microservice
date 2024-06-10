package com.turkcell.crm.accountService.api.controller;

import com.turkcell.crm.accountService.business.abstracts.AccountService;
import com.turkcell.crm.accountService.business.abstracts.AccountTypeService;
import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.accountType.CreateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.request.accountType.UpdateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.CreatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetAllAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetByIdAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.UpdatedAccountTypeResponse;
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
class AccountTypesControllerTest {
    @Mock
    private AccountTypeService accountTypeService;

    @InjectMocks
    private AccountTypesController accountTypesController;

    @Test
    void add() {
        CreateAccountTypeRequest request = new CreateAccountTypeRequest();
        CreatedAccountTypeResponse expectedResponse = new CreatedAccountTypeResponse();
        Mockito.when(accountTypeService.add(request)).thenReturn(expectedResponse);

        CreatedAccountTypeResponse response = accountTypesController.add(request);

        assertEquals(expectedResponse, response);
        verify(accountTypeService, times(1)).add(request);
    }

    @Test
    void getAll() {
        List<GetAllAccountTypeResponse> expectedResponse = Arrays.asList(new GetAllAccountTypeResponse(), new GetAllAccountTypeResponse());
        when(accountTypeService.getAll()).thenReturn(expectedResponse);

        List<GetAllAccountTypeResponse> actualResponse = accountTypesController.getAll();

        assertEquals(expectedResponse.size(),actualResponse.size());
        verify(accountTypeService,times(1)).getAll();
    }

    @Test
    void getById() {
        int id=1;
        GetByIdAccountTypeResponse expectedResponse =new GetByIdAccountTypeResponse();
        when(accountTypeService.getById(id)).thenReturn(expectedResponse);

        GetByIdAccountTypeResponse actualResponse=accountTypesController.getById(id);

        assertEquals(expectedResponse,actualResponse);
        verify(accountTypeService,times(1)).getById(id);
    }

    @Test
    void update() {
        UpdateAccountTypeRequest updateAccountTypeRequest=new UpdateAccountTypeRequest();
        UpdatedAccountTypeResponse expectedResponse =new UpdatedAccountTypeResponse();
        when(accountTypeService.update(updateAccountTypeRequest)).thenReturn(expectedResponse);

        UpdatedAccountTypeResponse response=accountTypesController.update(updateAccountTypeRequest);

        assertEquals(expectedResponse,response);
        verify(accountTypeService,times(1)).update(updateAccountTypeRequest);
    }

    @Test
    void delete() {
            int id=1;
            accountTypeService.delete(id);

            verify(accountTypeService,times(1)).delete(id);
    }
}