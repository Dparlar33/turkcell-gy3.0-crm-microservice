package com.turkcell.crm.accountService.business.concretes;

import com.turkcell.crm.accountService.business.abstracts.AccountTypeService;
import com.turkcell.crm.accountService.business.dtos.request.account.CreateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.request.account.UpdateAccountRequest;
import com.turkcell.crm.accountService.business.dtos.response.account.CreatedAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetAllAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.GetByIdAccountResponse;
import com.turkcell.crm.accountService.business.dtos.response.account.UpdatedAccountResponse;
import com.turkcell.crm.accountService.business.messages.AccountMessages;
import com.turkcell.crm.accountService.business.rules.AccountBusinessRules;
import com.turkcell.crm.accountService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.accountService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.accountService.dataAccess.AccountRepository;
import com.turkcell.crm.accountService.entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountManagerTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AccountBusinessRules accountBusinessRules;

    @Mock
    private AccountTypeService accountTypeService;

    @InjectMocks
    private AccountManager accountManager;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAdd() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();

        Account account = new Account();

        CreatedAccountResponse createdAccountResponse = new CreatedAccountResponse();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);

        when(modelMapperService.forRequest().map(createAccountRequest, Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(account, CreatedAccountResponse.class)).thenReturn(createdAccountResponse);

        CreatedAccountResponse result = accountManager.add(createAccountRequest);

        verify(accountTypeService).getById(createAccountRequest.getAccountTypeId());
        verify(accountBusinessRules).isCustomerExistById(createAccountRequest.getCustomerId());
        verify(accountRepository).save(account);
        assertEquals(createdAccountResponse, result);
    }

    @Test
    void testUpdate() {
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();

        Account account = new Account();

        UpdatedAccountResponse updatedAccountResponse = new UpdatedAccountResponse();
        when(modelMapperService.forRequest()).thenReturn(modelMapper);

        when(modelMapperService.forRequest().map(updateAccountRequest, Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapperService.forResponse().map(account, UpdatedAccountResponse.class)).thenReturn(updatedAccountResponse);

        UpdatedAccountResponse result = accountManager.update(updateAccountRequest);

        verify(accountBusinessRules).isAccountExistById(updateAccountRequest.getId());
        verify(accountBusinessRules).isAccountAlreadyDeleted(updateAccountRequest.getId());
        verify(accountRepository).save(account);
        assertEquals(updatedAccountResponse, result);
    }

    @Test
    void testUpdate_WhenAccountExistsById_thenThrowException() {

        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setId(1);

        doThrow(new BusinessException(AccountMessages.ACCOUNT_NOT_FOUND)).when(accountBusinessRules).isAccountExistById(request.getId());

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            accountManager.update(request);

        });
        assertEquals(AccountMessages.ACCOUNT_NOT_FOUND, businessException.getMessage());

        verify(accountBusinessRules).isAccountExistById(request.getId());
        verify(accountBusinessRules, never()).isAccountAlreadyDeleted(request.getId());
        verify(accountRepository, never()).save(any());
    }


    @Test
    void testGetById() {

        Account account = new Account();
        account.setId(1);

        Optional<Account> accountOptional = Optional.of(account);

        when(accountRepository.findById(1)).thenReturn(accountOptional);
        when(accountBusinessRules.isAccountAlreadyDeleted(account.getId())).thenReturn(account);
       // when(accountBusinessRules.isAccountExistById(account.getId())).thenReturn(account);

        GetByIdAccountResponse response = new GetByIdAccountResponse();
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(account, GetByIdAccountResponse.class)).thenReturn(response);

        GetByIdAccountResponse result = accountManager.getById(1);

        verify(accountRepository).findById(1);

        verify(accountBusinessRules).isAccountAlreadyDeleted(1);
       // verify(accountBusinessRules).isAccountExistById(1);

        verify(modelMapperService.forResponse()).map(account,GetByIdAccountResponse.class);

        assertEquals(response, result);
    }

    @Test
    void testGetById_WhenAccountAlreadyDeleted_thenThrowException() {

        int accountId = 1;

        doThrow(new BusinessException(AccountMessages.ACCOUNT_NOT_FOUND)).when(accountBusinessRules).isAccountAlreadyDeleted(accountId);


        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            accountManager.getById(accountId);
        });


        assertEquals(AccountMessages.ACCOUNT_NOT_FOUND, businessException.getMessage());
        verify(accountBusinessRules).isAccountAlreadyDeleted(accountId);
        verify(accountBusinessRules, never()).isAccountExistById(accountId);
        verify(accountRepository, never()).findById(accountId);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void testGetAll() {
        List<Account> accounts = List.of(new Account());

        List<GetAllAccountResponse> getAllAccountResponses = List.of(new GetAllAccountResponse());

        when(accountRepository.findByDeletedDateIsNull()).thenReturn(accounts);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapperService.forResponse().map(any(Account.class), eq(GetAllAccountResponse.class)))
                .thenAnswer(invocation -> {
                    Account account = invocation.getArgument(0);

                    GetAllAccountResponse response = new GetAllAccountResponse();
                    return response;
                });

        List<GetAllAccountResponse> result = accountManager.getAll();

        verify(accountRepository).findByDeletedDateIsNull();
        assertEquals(getAllAccountResponses, result);
    }

    @Test
    void testDelete() {
        int accountId = 1;
        Account account = new Account();

        when(accountBusinessRules.isAccountAlreadyDeleted(accountId)).thenReturn(account);

        accountManager.delete(accountId);

        verify(accountBusinessRules).isAccountAlreadyDeleted(accountId);
        verify(accountRepository).save(account);
        assertNotNull(account.getDeletedDate());
    }
    @Test
    void testDelete_WhenAccountAlreadyDeleted_thenThrowException() {

        int accountId = 1;

        doThrow(new BusinessException(AccountMessages.ACCOUNT_DELETION_SUCCESSFUL)).when(accountBusinessRules).isAccountAlreadyDeleted(accountId);

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            accountManager.delete(accountId);
        });

        assertEquals(AccountMessages.ACCOUNT_DELETION_SUCCESSFUL, businessException.getMessage());
        verify(accountBusinessRules).isAccountAlreadyDeleted(accountId);
        verify(accountRepository, never()).save(any());
    }
}



