package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.CatalogService;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.CreateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.UpdateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.CreatedCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetAllCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetByIdCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.UpdatedCatalogResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogsControllerTest {

    @Mock
    private CatalogService catalogService;


    @InjectMocks
    private CatalogsController catalogsController;

    @Test
    public void testAddCatalog() {
        // Arrange
        CreateCatalogRequest request = new CreateCatalogRequest();
        CreatedCatalogResponse expectedResponse = new CreatedCatalogResponse();
        when(catalogService.add(request)).thenReturn(expectedResponse);

        CreatedCatalogResponse response = catalogsController.add(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(catalogService, times(1)).add(request);
    }

    @Test
    public void testGetAllCatalogs() {
        List<GetAllCatalogResponse> expectedResponse = Arrays.asList(new GetAllCatalogResponse(), new GetAllCatalogResponse());
        when(catalogService.getAll()).thenReturn(expectedResponse);

        List<GetAllCatalogResponse> actualResponse = catalogsController.getAll();

        assertEquals(expectedResponse.size(), actualResponse.size());
        verify(catalogService, times(1)).getAll();
    }

    @Test
    public void testGetCatalogById() {
        int id = 1;
        GetByIdCatalogResponse expectedResponse = new GetByIdCatalogResponse();
        when(catalogService.getById(id)).thenReturn(expectedResponse);

        GetByIdCatalogResponse actualResponse = catalogsController.getById(id);

        assertEquals(expectedResponse, actualResponse);
        verify(catalogService, times(1)).getById(id);
    }

    @Test
    public void testUpdateCatalog() {
        // Arrange
        UpdateCatalogRequest updateCatalogRequest = new UpdateCatalogRequest();
        UpdatedCatalogResponse expectedResponse = new UpdatedCatalogResponse();
        when(catalogService.update(updateCatalogRequest)).thenReturn(expectedResponse);

        // Act
        UpdatedCatalogResponse response = catalogsController.update(updateCatalogRequest);

        // Assert
        assertEquals(expectedResponse, response);
        verify(catalogService, times(1)).update(updateCatalogRequest);
    }

    @Test
    public void testDeleteCatalog() {
        int id = 1;
        catalogsController.delete(id);

        verify(catalogService, times(1)).delete(id);
    }
}