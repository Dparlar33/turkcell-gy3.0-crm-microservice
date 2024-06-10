package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.CatalogMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.CatalogRepository;
import com.turkcell.crm.catalogService.entity.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CatalogBusinessRulesTest {

    @Mock
    private CatalogRepository catalogRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private CatalogBusinessRules businessRules;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsCatalogExistById_WhenCatalogExists() {
        // Arrange

        Catalog existingCatalog = new Catalog();
        existingCatalog.setId(1);
        when(catalogRepository.findById(existingCatalog.getId())).thenReturn(Optional.of(existingCatalog));

        // Act
        Catalog result = businessRules.isCatalogExistById(existingCatalog.getId());

        // Assert
        assertNotNull(result);
        assertEquals(existingCatalog, result);
    }

    @Test
    public void testIsCatalogExistById_WhenCatalogDoesNotExist() {
        // Arrange

        Catalog existingCatalog = new Catalog();
        existingCatalog.setId(1);
        when(catalogRepository.findById(existingCatalog.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessException.class, () -> businessRules.isCatalogExistById(existingCatalog.getId()));
    }

    @Test
    public void testIsCatalogAlreadyDeleted_WhenCatalogNotDeleted() {
        // Arrange

        Catalog existingCatalog = new Catalog();
        existingCatalog.setId(1);
        when(catalogRepository.findById(existingCatalog.getId())).thenReturn(Optional.of(existingCatalog));

        // Act
        Catalog result = businessRules.isCatalogAlreadyDeleted(existingCatalog.getId());

        // Assert
        assertNotNull(result);
        assertEquals(existingCatalog, result);
    }

    @Test
    public void testIsCatalogAlreadyDeleted_WhenCatalogAlreadyDeleted() {
        // Arrange

        Catalog deletedCatalog = new Catalog();
        deletedCatalog.setId(1);
        deletedCatalog.setDeletedDate(LocalDateTime.now());

        when(catalogRepository.findById(deletedCatalog.getId())).thenReturn(Optional.of(deletedCatalog));
        when(messageService.getMessage(CatalogMessages.CATALOG_ALREADY_DELETED))
                .thenReturn("Catalog already deleted");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> businessRules.isCatalogAlreadyDeleted(deletedCatalog.getId()));
        assertEquals("Catalog already deleted", exception.getMessage());
    }
}