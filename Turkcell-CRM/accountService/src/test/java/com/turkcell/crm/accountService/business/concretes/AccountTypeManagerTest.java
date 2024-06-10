package com.turkcell.crm.accountService.business.concretes;

import com.turkcell.crm.accountService.business.dtos.request.accountType.CreateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.request.accountType.UpdateAccountTypeRequest;
import com.turkcell.crm.accountService.business.dtos.response.accountType.CreatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetAllAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.GetByIdAccountTypeResponse;
import com.turkcell.crm.accountService.business.dtos.response.accountType.UpdatedAccountTypeResponse;
import com.turkcell.crm.accountService.business.messages.AccountMessages;
import com.turkcell.crm.accountService.business.rules.AccountTypeBusinessRules;
import com.turkcell.crm.accountService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.accountService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.accountService.dataAccess.AccountTypeRepository;
import com.turkcell.crm.accountService.entities.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hibernate.internal.util.ExceptionHelper.doThrow;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccountTypeManagerTest {

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private AccountTypeBusinessRules accountTypeBusinessRules;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountTypeManager accountTypeManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testAdd() {

        CreateAccountTypeRequest createAccountTypeRequest = new CreateAccountTypeRequest();

        AccountType accountType = new AccountType();

        CreatedAccountTypeResponse createdAccountTypeResponse = new CreatedAccountTypeResponse();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createAccountTypeRequest, AccountType.class)).thenReturn(accountType);
        when(accountTypeRepository.save(accountType)).thenReturn(accountType);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapper.map(accountType, CreatedAccountTypeResponse.class)).thenReturn(createdAccountTypeResponse);

        CreatedAccountTypeResponse result = accountTypeManager.add(createAccountTypeRequest);

        verify(accountTypeRepository).save(accountType);
        assertEquals(createdAccountTypeResponse, result);
    }@Test
    void testUpdate() {

        UpdateAccountTypeRequest updateAccountTypeRequest = new UpdateAccountTypeRequest();

        AccountType accountType = new AccountType();

        UpdatedAccountTypeResponse updatedAccountTypeResponse = new UpdatedAccountTypeResponse();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateAccountTypeRequest, AccountType.class)).thenReturn(accountType);
        when(accountTypeRepository.save(accountType)).thenReturn(accountType);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapper.map(accountType, UpdatedAccountTypeResponse.class)).thenReturn(updatedAccountTypeResponse);

        UpdatedAccountTypeResponse result = accountTypeManager.update(updateAccountTypeRequest);

        verify(accountTypeBusinessRules).isAccountTypeExistById(updateAccountTypeRequest.getId());
        verify(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(updateAccountTypeRequest.getId());
        verify(accountTypeRepository).save(accountType);
        assertEquals(updatedAccountTypeResponse, result);
    }

    @Test
    void testGetAll() {

        List<AccountType> accountTypes = List.of(new AccountType());

        List<GetAllAccountTypeResponse> getAllAccountTypeResponses = List.of(new GetAllAccountTypeResponse());

        when(accountTypeRepository.findByDeletedDateIsNull()).thenReturn(accountTypes);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapper.map(any(AccountType.class), eq(GetAllAccountTypeResponse.class)))
                .thenAnswer(invocation -> {
                    AccountType accountType = invocation.getArgument(0);
                    GetAllAccountTypeResponse response = new GetAllAccountTypeResponse();
                    return response;
                });

        List<GetAllAccountTypeResponse> result = accountTypeManager.getAll();

        verify(accountTypeRepository).findByDeletedDateIsNull();
        assertEquals(getAllAccountTypeResponses, result);
    }


    @Test
    void testGetById() {

        int id = 123;
        AccountType mockAccountType = new AccountType();

        GetByIdAccountTypeResponse expectedResponse = new GetByIdAccountTypeResponse();

        when(accountTypeBusinessRules.isAccountTypeAlreadyDeleted(id)).thenReturn(null);
        when(accountTypeBusinessRules.isAccountTypeExistById(id)).thenReturn(mockAccountType);
        when(accountTypeRepository.findById(id)).thenReturn(Optional.of(mockAccountType));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(mockAccountType, GetByIdAccountTypeResponse.class)).thenReturn(expectedResponse);

        GetByIdAccountTypeResponse response = accountTypeManager.getById(id);

        assertNotNull(response);
        assertSame(expectedResponse, response);
        verify(accountTypeRepository).findById(id);
        verify(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(id);
        verify(accountTypeBusinessRules).isAccountTypeExistById(id);
        verify(modelMapperService.forResponse()).map(mockAccountType, GetByIdAccountTypeResponse.class);
    }

    @Test
    void testDelete() {

        int accountTypeId = 1;
        AccountType accountType = new AccountType();

        when(accountTypeBusinessRules.isAccountTypeAlreadyDeleted(accountTypeId)).thenReturn(accountType);

        accountTypeManager.delete(accountTypeId);

        verify(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(accountTypeId);
        verify(accountTypeRepository).save(accountType);
        assertNotNull(accountType.getDeletedDate());
    }
    @Test
    void testDelete_WhenAccountTypeAlreadyDeleted_thenThrowException() {

        int accountTypeId = 1;

        Mockito.doThrow(new BusinessException(AccountMessages.ACCOUNT_TYPE_NOT_FOUND)).when(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(accountTypeId);

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            accountTypeManager.delete(accountTypeId);
        });

        assertEquals(AccountMessages.ACCOUNT_TYPE_NOT_FOUND, businessException.getMessage());
        verify(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(accountTypeId);
        verify(accountTypeRepository, never()).save(any());
    }

    @Test
    void testGetById_AccountTypeNotFound_thenThrowException() {
        int accountTypeId=1;

        when(accountTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        Mockito.doThrow(new BusinessException(AccountMessages.ACCOUNT_TYPE_NOT_FOUND)).when(accountTypeBusinessRules).isAccountTypeAlreadyDeleted(accountTypeId);

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            accountTypeManager.getById(accountTypeId);
        });

        verify(accountTypeRepository, never()).save(any());
        assertEquals(AccountMessages.ACCOUNT_TYPE_NOT_FOUND, businessException.getMessage());
    }
}