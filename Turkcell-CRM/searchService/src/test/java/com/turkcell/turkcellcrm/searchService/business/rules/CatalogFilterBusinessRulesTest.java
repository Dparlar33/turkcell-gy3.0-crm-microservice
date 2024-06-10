package com.turkcell.turkcellcrm.searchService.business.rules;

import com.turkcell.turkcellcrm.searchService.business.messages.CatalogFilterBusinessRulesMessages;
import com.turkcell.turkcellcrm.searchService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCatalogRepository;
import com.turkcell.turkcellcrm.searchService.entities.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogFilterBusinessRulesTest {

    @Mock
    private SearchCatalogRepository searchCatalogRepository;

    @InjectMocks
    private CatalogFilterBusinessRules catalogFilterBusinessRules;

    @BeforeEach
    void setUp() {
        catalogFilterBusinessRules = new CatalogFilterBusinessRules(searchCatalogRepository);
    }

    @Test
    void testIsCatalogAlreadyDeleted_CatalogExistsAndNotDeleted() {

        int catalogId = 100;
        Catalog catalog = new Catalog();

        when(searchCatalogRepository.findCatalogByCatalogId(catalogId)).thenReturn(Optional.of(catalog));

        Catalog result = catalogFilterBusinessRules.IsCatalogAlreadyDeleted(catalogId);

        assertEquals(catalog, result);
        verify(searchCatalogRepository).findCatalogByCatalogId(catalogId);
    }

    @Test
    void testIsCatalogAlreadyDeleted_CatalogDoesNotExist() {

        int catalogId = 200;

        when(searchCatalogRepository.findCatalogByCatalogId(catalogId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            catalogFilterBusinessRules.IsCatalogAlreadyDeleted(catalogId);
        });

        assertEquals(CatalogFilterBusinessRulesMessages.CATALOG_NOT_EXISTS, exception.getMessage());

        verify(searchCatalogRepository).findCatalogByCatalogId(catalogId);
    }

    @Test
    void testIsCatalogAlreadyDeleted_CatalogAlreadyDeleted() {

        int catalogId = 300;

        Catalog catalog = new Catalog();
        catalog.setDeletedDate(LocalDateTime.now());

        when(searchCatalogRepository.findCatalogByCatalogId(catalogId)).thenReturn(Optional.of(catalog));


        BusinessException exception = assertThrows(BusinessException.class, () -> {
            catalogFilterBusinessRules.IsCatalogAlreadyDeleted(catalogId);
        });

        assertEquals(CatalogFilterBusinessRulesMessages.CATALOG_IS_ALREADY_DELETED, exception.getMessage());

        verify(searchCatalogRepository).findCatalogByCatalogId(catalogId);
    }
}