package com.turkcell.crm.accountService.business.rules;

import com.turkcell.crm.accountService.api.client.CustomerClient;
import com.turkcell.crm.accountService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.accountService.dataAccess.AccountRepository;
import com.turkcell.crm.accountService.entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AccountBusinessRulesTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerClient customerClient;

    private AccountBusinessRules accountBusinessRules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isAccountExistById_WhenAccountExists_ShouldReturnAccount() {
        // Arrange

        Account account = new Account();
        account.setId(1);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // Act
        Account result = accountBusinessRules.isAccountExistById(account.getId());

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    void isAccountExistById_WhenAccountDoesNotExist_ShouldThrowException() {
        // Arrange
        Account account = new Account();
        account.setId(1);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BusinessException.class, () -> accountBusinessRules.isAccountExistById(account.getId()));
    }

    @Test
    void isAccountAlreadyDeleted_WhenAccountNotDeleted_ShouldReturnAccount() {
        // Arrange

        Account account = new Account();
        account.setId(1);
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // Act
        Account result = accountBusinessRules.isAccountAlreadyDeleted(account.getId());

        // Assert
        assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    void isAccountAlreadyDeleted_WhenAccountIsDeleted_ShouldThrowException() {
        // Arrange

        Account account = new Account();
        account.setId(1);
        account.setDeletedDate(LocalDateTime.now());
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        // Act and Assert
        assertThrows(BusinessException.class, () -> accountBusinessRules.isAccountAlreadyDeleted(account.getId()));
    }

    @Test
    void isCustomerExistById_WhenCustomerExists_ShouldNotThrowException() {
        // Arrange

        Account account = new Account();
        account.setId(1);

        when(customerClient.getCustomer(account.getId())).thenReturn(true);

        // Act and Assert
        assertDoesNotThrow(() -> accountBusinessRules.isCustomerExistById(account.getId()));
    }

    @Test
    void isCustomerExistById_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Arrange

        Account account = new Account();
        account.setId(1);
        when(customerClient.getCustomer(account.getId())).thenReturn(false);

        // Act and Assert
        assertThrows(BusinessException.class, () -> accountBusinessRules.isCustomerExistById(account.getId()));
    }
}