package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.ProductPropertyService;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.CreateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.CreateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.UpdateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.CreatedCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.CreatedProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetAllProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetByIdProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.UpdatedProductProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductPropertiesControllerTest {

    @Mock
    private ProductPropertyService productPropertyService;

    @InjectMocks
    private ProductPropertiesController productPropertiesController;

    @Test
    public void testAddProductProperty() {
        CreateProductPropertyRequest request = new CreateProductPropertyRequest();
        CreatedProductPropertyResponse expectedResponse = new CreatedProductPropertyResponse();
        when(productPropertyService.add(request)).thenReturn(expectedResponse);

        // Act
        CreatedProductPropertyResponse response = productPropertiesController.add(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(productPropertyService, times(1)).add(request);
    }

    @Test
    public void testGetAllProductProperties() {
        List<GetAllProductPropertyResponse> expectedResponse = Arrays.asList(new GetAllProductPropertyResponse(), new GetAllProductPropertyResponse());
        when(productPropertyService.getAll()).thenReturn(expectedResponse);

        List<GetAllProductPropertyResponse> actualResponse = productPropertiesController.getAll();

        assertEquals(expectedResponse.size(), actualResponse.size());
        verify(productPropertyService, times(1)).getAll();
    }

    @Test
    public void testGetProductPropertyById() {
        int id = 1;
        GetByIdProductPropertyResponse expectedResponse = new GetByIdProductPropertyResponse();
        when(productPropertyService.getById(id)).thenReturn(expectedResponse);

        GetByIdProductPropertyResponse actualResponse = productPropertiesController.getById(id);

        assertEquals(expectedResponse, actualResponse);
        verify(productPropertyService, times(1)).getById(id);
    }

    @Test
    public void testUpdateProductProperty() {
        UpdateProductPropertyRequest request = new UpdateProductPropertyRequest();
        UpdatedProductProductResponse expectedResponse = new UpdatedProductProductResponse();
        when(productPropertyService.update(request)).thenReturn(expectedResponse);

        // Act
        UpdatedProductProductResponse response = productPropertiesController.update(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(productPropertyService, times(1)).update(request);
    }

    @Test
    public void testDeleteProductProperty() {
        int id = 1;
        productPropertiesController.delete(id);

        verify(productPropertyService, times(1)).delete(id);
    }
}