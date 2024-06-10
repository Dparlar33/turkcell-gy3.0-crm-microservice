package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.ProductService;
import com.turkcell.crm.catalogService.business.dtos.request.product.CreateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.product.UpdateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.CreateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.response.product.CreatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetAllProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetByIdProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.CreatedProductPropertyResponse;
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
public class ProductsControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    public void testAddProduct() {
        CreateProductRequest request = new CreateProductRequest();
        CreatedProductResponse expectedResponse = new CreatedProductResponse();
        when(productService.add(request)).thenReturn(expectedResponse);

        // Act
        CreatedProductResponse response = productsController.add(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(productService, times(1)).add(request);
    }

    @Test
    public void testGetAllProducts() {
        List<GetAllProductResponse> expectedResponse = Arrays.asList(new GetAllProductResponse(), new GetAllProductResponse());
        when(productService.getAll()).thenReturn(expectedResponse);

        List<GetAllProductResponse> actualResponse = productsController.getAll();

        assertEquals(expectedResponse.size(), actualResponse.size());
        verify(productService, times(1)).getAll();
    }

    @Test
    public void testGetProductById() {
        int id = 1;
        GetByIdProductResponse expectedResponse = new GetByIdProductResponse();
        when(productService.getById(id)).thenReturn(expectedResponse);

        GetByIdProductResponse actualResponse = productsController.getById(id);

        assertEquals(expectedResponse, actualResponse);
        verify(productService, times(1)).getById(id);
    }

    @Test
    public void testUpdateProduct() {
        UpdateProductRequest request = new UpdateProductRequest();
        UpdatedProductResponse expectedResponse = new UpdatedProductResponse();
        when(productService.update(request)).thenReturn(expectedResponse);

        // Act
        UpdatedProductResponse response = productsController.update(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(productService, times(1)).update(request);
    }

    @Test
    public void testDeleteProduct() {
        int id = 1;
        productsController.delete(id);

        verify(productService, times(1)).delete(id);
    }
}