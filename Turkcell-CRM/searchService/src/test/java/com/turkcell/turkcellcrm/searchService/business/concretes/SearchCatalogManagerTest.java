package com.turkcell.turkcellcrm.searchService.business.concretes;

import com.turkcell.turkcellcrm.common.events.catalog.CatalogCreatedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicFilter;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicSort;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCatalogResponse;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllProductResponse;
import com.turkcell.turkcellcrm.searchService.business.rules.CatalogFilterBusinessRules;
import com.turkcell.turkcellcrm.searchService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCatalogRepository;
import com.turkcell.turkcellcrm.searchService.entities.Catalog;
import com.turkcell.turkcellcrm.searchService.entities.Product;
import org.antlr.stringtemplate.language.Cat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SearchCatalogManagerTest {

    @Mock
    CatalogFilterBusinessRules catalogFilterBusinessRules;
    @Mock
    ModelMapperService modelMapperService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    SearchCatalogRepository searchCatalogRepository;
    @Mock
    SearchService searchService;
    @InjectMocks
    SearchCatalogManager searchCatalogManager;

    @Test
    void testAdd() {

        CatalogCreatedEvent catalogCreatedEvent=new CatalogCreatedEvent();
        Catalog catalog =new Catalog();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(catalogCreatedEvent, Catalog.class)).thenReturn(catalog);

        searchCatalogManager.add(catalogCreatedEvent);

        verify(modelMapperService.forRequest()).map(catalogCreatedEvent,Catalog.class);
        verify(searchCatalogRepository).save(catalog);

        assertDoesNotThrow(()->searchCatalogManager.add(catalogCreatedEvent));
    }

    @Test
    void testGetAllCatalog() {
        // Arrange
        Catalog catalog1 = new Catalog();
        Catalog catalog2 = new Catalog();

        List<Catalog> catalogList = Arrays.asList(catalog1, catalog2);
        GetAllCatalogResponse response1 = new GetAllCatalogResponse();
        GetAllCatalogResponse response2 = new GetAllCatalogResponse();

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(searchCatalogRepository.findAll()).thenReturn(catalogList);
        when(modelMapper.map(catalog1, GetAllCatalogResponse.class)).thenReturn(response1);
        when(modelMapper.map(catalog2, GetAllCatalogResponse.class)).thenReturn(response2);

        // Act
        List<GetAllCatalogResponse> result = searchCatalogManager.getAllCatalog();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));

        verify(searchCatalogRepository, times(1)).findAll();
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllCatalogResponse.class));
    }
    @Test
    void testUpdate() {

        CatalogUpdatedEvent catalogUpdatedEvent=new CatalogUpdatedEvent();
        Catalog catalog =new Catalog();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(catalogUpdatedEvent,Catalog.class)).thenReturn(catalog);

        searchCatalogManager.update(catalogUpdatedEvent);

        verify(modelMapperService.forRequest()).map(catalogUpdatedEvent,Catalog.class);
        verify(searchCatalogRepository).save(catalog);

        assertDoesNotThrow(()->searchCatalogManager.update(catalogUpdatedEvent));
    }
    @Test
    void testDelete() {

        int catalogId = 111;
        Catalog catalog = new Catalog();
        when(catalogFilterBusinessRules.IsCatalogAlreadyDeleted(catalogId)).thenReturn(catalog);

        searchCatalogManager.delete(catalogId);

        verify(catalogFilterBusinessRules).IsCatalogAlreadyDeleted(catalogId);
        verify(searchCatalogRepository).save(catalog);

        assertNotNull(catalog.getDeletedDate());
    }
}