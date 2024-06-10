package com.turkcell.crm.catalogService.business.concretes;

import com.turkcell.crm.catalogService.business.dtos.request.catalog.CreateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.UpdateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.CreatedCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetAllCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetByIdCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.UpdatedCatalogResponse;
import com.turkcell.crm.catalogService.business.messages.CatalogMessages;
import com.turkcell.crm.catalogService.business.rules.CatalogBusinessRules;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.catalogService.dataAccess.CatalogRepository;
import com.turkcell.crm.catalogService.entity.Catalog;
import com.turkcell.crm.catalogService.kafka.producers.CatalogProducer;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogCreatedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogDeletedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogUpdatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogManagerTest {

    @Mock
    private CatalogRepository catalogRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CatalogBusinessRules catalogBusinessRules;

    @Mock
    private CatalogProducer catalogProducer;

    @InjectMocks
    private CatalogManager catalogManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAdd() {

        CreateCatalogRequest createCatalogRequest = new CreateCatalogRequest();
        Catalog catalog = new Catalog();
        CatalogCreatedEvent catalogCreatedEvent = new CatalogCreatedEvent();
        CreatedCatalogResponse expectedResponse = new CreatedCatalogResponse();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(createCatalogRequest, Catalog.class)).thenReturn(catalog);
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        when(modelMapperService.forRequest().map(catalog, CatalogCreatedEvent.class)).thenReturn(catalogCreatedEvent);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(catalog, CreatedCatalogResponse.class)).thenReturn(expectedResponse);

        CreatedCatalogResponse actualResponse = catalogManager.add(createCatalogRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(catalogProducer, times(1)).sendCreatedMessage(catalogCreatedEvent);
    }

    @Test
    void testGetAll() {

        Catalog catalog1 = new Catalog();
        catalog1.setId(1);
        Catalog catalog2 = new Catalog();
        catalog2.setId(2);

        List<Catalog> catalogs = Arrays.asList(catalog1, catalog2);

        when(catalogRepository.findByDeletedDateIsNull()).thenReturn(catalogs);;

        GetAllCatalogResponse response1 = new GetAllCatalogResponse();
        response1.setId(1);
        GetAllCatalogResponse response2 = new GetAllCatalogResponse();
        response2.setId(2);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(catalog1, GetAllCatalogResponse.class)).thenReturn(response1);
        when(modelMapperService.forResponse().map(catalog2, GetAllCatalogResponse.class)).thenReturn(response2);

        List<GetAllCatalogResponse> actualResponses = catalogManager.getAll();

        assertEquals(2, actualResponses.size());
        assertEquals(1, actualResponses.get(0).getId());
        assertEquals(2, actualResponses.get(1).getId());

        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllCatalogResponse.class));
    }

    @Test
    public void testGetAll_whenCatalogAlreadyDeleted_thenThrowException() {

        when(catalogRepository.findByDeletedDateIsNull()).thenThrow(new BusinessException("PRODUCT ALREADY DELETED"));

        assertThrows(BusinessException.class, () -> catalogManager.getAll());

        verify(catalogRepository).findByDeletedDateIsNull();
        verify(modelMapperService, never()).forResponse();
    }

    @Test
    void testUpdate() {

        UpdateCatalogRequest updateCatalogRequest = new UpdateCatalogRequest();
        updateCatalogRequest.setId(1);
        Catalog catalog = new Catalog();
        catalog.setId(1);
        CatalogUpdatedEvent catalogUpdatedEvent = new CatalogUpdatedEvent();
        UpdatedCatalogResponse updatedCatalogResponse = new UpdatedCatalogResponse();

        when(catalogBusinessRules.isCatalogExistById(catalog.getId())).thenReturn(catalog);

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateCatalogRequest, Catalog.class)).thenReturn(catalog);
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        when(modelMapper.map(catalog, CatalogUpdatedEvent.class)).thenReturn(catalogUpdatedEvent);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(catalog, UpdatedCatalogResponse.class)).thenReturn(updatedCatalogResponse);


        UpdatedCatalogResponse actualResponse = catalogManager.update(updateCatalogRequest);


        assertEquals(updatedCatalogResponse, actualResponse);
        verify(catalogProducer).sendUpdatedMessage(catalogUpdatedEvent);
        verify(modelMapper, times(3)).map(any(), any());
        verify(catalogBusinessRules).isCatalogExistById(catalog.getId());
    }

    @Test
    public void testUpdate_ThrowsExceptionWhenIsCatalogExistById() {

        UpdateCatalogRequest request = new UpdateCatalogRequest();
        request.setId(1);

        when(catalogBusinessRules.isCatalogExistById(request.getId())).thenThrow(new BusinessException("CATALOG ALREADY DELETED"));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            catalogManager.update(request);

        });

        assertEquals("CATALOG ALREADY DELETED", exception.getMessage());

        verify(catalogBusinessRules).isCatalogExistById(request.getId());
        verify(catalogRepository, never()).save(any(Catalog.class));
        verify(catalogProducer, never()).sendUpdatedMessage(any(CatalogUpdatedEvent.class));
    }

    @Test
    void testGetById() {

        int id = 1;
        Catalog catalog = new Catalog();
        GetByIdCatalogResponse expectedResponse = new GetByIdCatalogResponse();

        when(catalogRepository.findById(id)).thenReturn(Optional.of(catalog));

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(catalog, GetByIdCatalogResponse.class)).thenReturn(expectedResponse);


        GetByIdCatalogResponse actualResponse = catalogManager.getById(id);


        assertEquals(expectedResponse, actualResponse);

        verify(modelMapperService.forResponse()).map(any(), eq(GetByIdCatalogResponse.class));
    }

    @Test
    public void testGetById_ThrowsExceptionWhenCatalogAlreadyDeleted(){

        int catalogId = 1;

        doThrow(new BusinessException(CatalogMessages.CATALOG_ALREADY_DELETED)).when(catalogBusinessRules).isCatalogAlreadyDeleted(catalogId);


        assertThrows(BusinessException.class, () -> catalogManager.getById(catalogId));

        verify(catalogBusinessRules).isCatalogAlreadyDeleted(catalogId);
        verify(catalogRepository, never()).findById(anyInt());
        verify(catalogBusinessRules, never()).isCatalogExistById(anyInt());
        verify(modelMapperService, never()).forResponse();

    }

    @Test
    void testDelete() {

        int id = 1;
        Catalog catalog = new Catalog();
        CatalogDeletedEvent catalogDeletedEvent = new CatalogDeletedEvent();

        when(catalogBusinessRules.isCatalogAlreadyDeleted(id)).thenReturn(catalog);
        when(catalogRepository.save(catalog)).thenReturn(catalog);

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(catalog, CatalogDeletedEvent.class)).thenReturn(catalogDeletedEvent);


        catalogManager.delete(id);


        verify(catalogRepository, times(1)).save(catalog);
        verify(catalogProducer, times(1)).sendDeletedMessage(catalogDeletedEvent);
        verify(modelMapperService.forRequest()).map(any(), eq(CatalogDeletedEvent.class));
        verify(catalogBusinessRules).isCatalogAlreadyDeleted(id);
    }

    @Test
    void testDelete_whenProductAlreadyDeleted_thenThrowException() {

        int catalogId = 1;

        when(catalogBusinessRules.isCatalogAlreadyDeleted(catalogId)).thenThrow(new BusinessException(CatalogMessages.CATALOG_ALREADY_DELETED));

        assertThrows(BusinessException.class, () -> catalogManager.delete(catalogId));

        verify(catalogBusinessRules).isCatalogAlreadyDeleted(catalogId);
        verify(catalogRepository, never()).save(any());
        verify(modelMapperService, never()).forRequest();
        verify(catalogProducer, never()).sendDeletedMessage(any());
    }
}
