package com.turkcell.crm.accountService.business.rules;

import com.turkcell.crm.accountService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.accountService.dataAccess.AccountTypeRepository;
import com.turkcell.crm.accountService.entities.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountTypeBusinessRulesTest {

    @Mock
    private AccountTypeRepository accountTypeRepository;

    private AccountTypeBusinessRules accountTypeBusinessRules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void isAccountTypeExistById_WhenAccountTypeExists_ShouldReturnAccountType() {
        // Arrange

        AccountType accountType = new AccountType();
        accountType.setId(1);
        when(accountTypeRepository.findById(accountType.getId())).thenReturn(Optional.of(accountType));

        // Act
        AccountType result = accountTypeBusinessRules.isAccountTypeExistById(accountType.getId());

        // Assert
        assertNotNull(result);
        assertEquals(accountType, result);
    }

    @Test
    void isAccountTypeExistById_WhenAccountTypeDoesNotExist_ShouldThrowException() {
        // Arrange

        AccountType accountType = new AccountType();
        accountType.setId(1);
        when(accountTypeRepository.findById(accountType.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BusinessException.class, () -> accountTypeBusinessRules.isAccountTypeExistById(accountType.getId()));
    }

    @Test
    void isAccountTypeAlreadyDeleted_WhenAccountTypeNotDeleted_ShouldReturnAccountType() {
        // Arrange
        AccountType accountType = new AccountType();
        accountType.setId(1);

        when(accountTypeRepository.findById(accountType.getId())).thenReturn(Optional.of(accountType));

        // Act
        AccountType result = accountTypeBusinessRules.isAccountTypeAlreadyDeleted(accountType.getId());

        // Assert
        assertNotNull(result);
        assertEquals(accountType, result);
    }

    @Test
    void isAccountTypeAlreadyDeleted_WhenAccountTypeIsDeleted_ShouldThrowException() {
        // Arrange

        AccountType accountType = new AccountType();
        accountType.setId(1);
        accountType.setDeletedDate(LocalDateTime.now());
        when(accountTypeRepository.findById(accountType.getId())).thenReturn(Optional.of(accountType));

        // Act and Assert
        assertThrows(BusinessException.class, () -> accountTypeBusinessRules.isAccountTypeAlreadyDeleted(accountType.getId()));
    }
}
