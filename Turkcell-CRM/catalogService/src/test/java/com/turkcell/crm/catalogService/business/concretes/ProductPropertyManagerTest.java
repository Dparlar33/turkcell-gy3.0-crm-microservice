package com.turkcell.crm.catalogService.business.concretes;

import com.turkcell.crm.catalogService.business.abstracts.ProductPropertyService;
import com.turkcell.crm.catalogService.business.abstracts.ProductService;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.CreateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.UpdateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.response.ProductPropertyResponseDto;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.CreatedProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetAllProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetByIdProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.UpdatedProductProductResponse;
import com.turkcell.crm.catalogService.business.messages.ProductPropertyMessages;
import com.turkcell.crm.catalogService.business.rules.ProductPropertyBusinessRules;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.catalogService.dataAccess.ProductPropertyRepository;
import com.turkcell.crm.catalogService.entity.Product;
import com.turkcell.crm.catalogService.entity.ProductProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductPropertyManagerTest {

    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ProductPropertyRepository productPropertyRepository;
    @Mock
    private ProductPropertyBusinessRules productPropertyBusinessRules;
    @Mock
    private ProductService productService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ProductPropertyManager productPropertyManager;

    @Test
    void testAdd() {

        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(0);

        ProductProperty savedProperty = new ProductProperty();
        savedProperty.setId(1);

        CreateProductPropertyRequest createProductPropertyRequest = new CreateProductPropertyRequest();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createProductPropertyRequest, ProductProperty.class)).thenReturn(productProperty);

        when(productPropertyRepository.save(productProperty)).thenReturn(savedProperty);

        CreatedProductPropertyResponse createdProductPropertyResponse = new CreatedProductPropertyResponse();
        createdProductPropertyResponse.setId(1);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(savedProperty, CreatedProductPropertyResponse.class)).thenReturn(createdProductPropertyResponse);

        CreatedProductPropertyResponse result = productPropertyManager.add(createProductPropertyRequest);

        assertEquals(createdProductPropertyResponse, result);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(createProductPropertyRequest, ProductProperty.class);
        verify(productPropertyRepository).save(productProperty);
        verify(modelMapperService).forResponse();
        verify(modelMapper).map(savedProperty, CreatedProductPropertyResponse.class);
    }

    @Test
    void testGetAll() {

        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(1);
        productProperty.setKey("Renk");
        productProperty.setValue("Kırmızı");

        ProductProperty productProperty1 = new ProductProperty();
        productProperty1.setId(2);
        productProperty1.setKey("Renk");
        productProperty1.setValue("Mavi");

        List<ProductProperty> productProperties = Arrays.asList(productProperty, productProperty1);

        when(productPropertyRepository.findByDeletedDateIsNull()).thenReturn(productProperties);

        GetAllProductPropertyResponse response1 = new GetAllProductPropertyResponse();
        response1.setId(1);
        response1.setKey("Renk");
        response1.setValue("Kırmızı");

        GetAllProductPropertyResponse response2 = new GetAllProductPropertyResponse();
        response2.setId(2);
        response2.setKey("Renk");
        response2.setValue("Mavi");

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(productProperty, GetAllProductPropertyResponse.class)).thenReturn(response1);
        when(modelMapperService.forResponse().map(productProperty1, GetAllProductPropertyResponse.class)).thenReturn(response2);

        List<GetAllProductPropertyResponse> result = productPropertyManager.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Kırmızı", result.get(0).getValue());
        assertEquals(2, result.get(1).getId());
        assertEquals("Mavi", result.get(1).getValue());

        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllProductPropertyResponse.class));
    }

    @Test
    public void testGetAll_whenProductPropertyAlreadyDeleted_thenThrowException() {

        when(productPropertyRepository.findByDeletedDateIsNull()).thenThrow(new BusinessException("PRODUCT PROPERTY ALREADY DELETED"));

        assertThrows(BusinessException.class, () -> productPropertyManager.getAll());

        verify(productPropertyRepository).findByDeletedDateIsNull();
        verify(modelMapperService, never()).forResponse();
    }

    @Test
    void testUpdate() {

        UpdateProductPropertyRequest updateProductPropertyRequest = new UpdateProductPropertyRequest();
        updateProductPropertyRequest.setId(1);

        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(1);

        ProductProperty savedProperty = new ProductProperty();
        savedProperty.setId(1);
        savedProperty.setUpdatedDate(LocalDateTime.now());

        UpdatedProductProductResponse response = new UpdatedProductProductResponse();

        when(productPropertyBusinessRules.isProductPropertyAlreadyDeleted(updateProductPropertyRequest.getId())).thenReturn(productProperty);

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateProductPropertyRequest, ProductProperty.class)).thenReturn(productProperty);

        when(productPropertyRepository.save(productProperty)).thenReturn(savedProperty);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(savedProperty, UpdatedProductProductResponse.class)).thenReturn(response);

        UpdatedProductProductResponse result = productPropertyManager.update(updateProductPropertyRequest);

        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(productProperty.getId());
        verify(productPropertyRepository).save(productProperty);

        assertEquals(response, result);
    }

    @Test
    public void testUpdate_ThrowsExceptionWhenProductPropertyAlreadyDeleted() {

        UpdateProductPropertyRequest request = new UpdateProductPropertyRequest();
        request.setId(1);

        when(productPropertyBusinessRules.isProductPropertyAlreadyDeleted(request.getId())).thenThrow(new BusinessException(ProductPropertyMessages.PRODUCT_PROPERTY_ALREADY_DELETED));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            productPropertyManager.update(request);

        });

        assertEquals(ProductPropertyMessages.PRODUCT_PROPERTY_ALREADY_DELETED, exception.getMessage());

        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(request.getId());
        verify(productPropertyRepository, never()).save(any(ProductProperty.class));
    }

    @Test
    void testGetById() {

        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(1);

        Optional<ProductProperty> optionalProductProperty = Optional.of(productProperty);

        when(productPropertyRepository.findById(productProperty.getId())).thenReturn(optionalProductProperty);

        when(productPropertyBusinessRules.isProductPropertyAlreadyDeleted(productProperty.getId())).thenReturn(productProperty);
        when(productPropertyBusinessRules.isProductPropertyExistById(productProperty.getId())).thenReturn(productProperty);

        GetByIdProductPropertyResponse getByIdProductPropertyResponse = new GetByIdProductPropertyResponse();

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(productProperty, GetByIdProductPropertyResponse.class)).thenReturn(getByIdProductPropertyResponse);

        GetByIdProductPropertyResponse response = productPropertyManager.getById(1);

        verify(productPropertyRepository).findById(1);

        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(1);
        verify(productPropertyBusinessRules).isProductPropertyExistById(1);

        verify(modelMapperService.forResponse()).map(productProperty, GetByIdProductPropertyResponse.class);

        assertEquals(getByIdProductPropertyResponse, response);
    }

    @Test
    public void testGetById_ThrowsExceptionWhenProductPropertyAlreadyDeleted() {

        int productId = 1;

        doThrow(new BusinessException(ProductPropertyMessages.PRODUCT_PROPERTY_ALREADY_DELETED))
                .when(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(productId);

        assertThrows(BusinessException.class, () -> productPropertyManager.getById(productId));

        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(productId);
        verify(productPropertyRepository, never()).findById(anyInt());
        verify(productPropertyBusinessRules, never()).isProductPropertyExistById(anyInt());
        verify(modelMapperService, never()).forResponse();
    }

    @Test
    void testDelete() {

        int id = 1;
        ProductProperty productProperty = new ProductProperty();
        productProperty.setId(id);

        when(productPropertyBusinessRules.isProductPropertyAlreadyDeleted(id)).thenReturn(productProperty);

        productPropertyManager.delete(id);

        assertNotNull(productProperty.getDeletedDate());
        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(id);
        verify(productPropertyRepository).save(productProperty);
    }

    @Test
    void testDelete_whenProductPropertyAlreadyDeleted_thenThrowException() {

        int producPropertytId = 1;

        when(productPropertyBusinessRules.isProductPropertyAlreadyDeleted(producPropertytId)).thenThrow(new BusinessException("PRODUCT PROPERTY ALREADY DELETED"));

        assertThrows(BusinessException.class, () -> productPropertyManager.delete(producPropertytId));

        verify(productPropertyBusinessRules).isProductPropertyAlreadyDeleted(producPropertytId);
        verify(productPropertyRepository, never()).save(any());
        verify(modelMapperService, never()).forRequest();

    }

    @Test
    void testGetProductPropertyByProductId() {

        List<Integer> productIds = Arrays.asList(1, 2);

        Product product1 = new Product();
        ProductProperty productProperty1 = new ProductProperty();
        productProperty1.setProduct(product1);

        Product product2 = new Product();
        ProductProperty productProperty2 = new ProductProperty();
        productProperty2.setProduct(product2);

        List<ProductProperty> productProperties1 = Arrays.asList(productProperty1);
        List<ProductProperty> productProperties2 = Arrays.asList(productProperty2);

        when(productPropertyRepository.findProductPropertiesByProductId(1)).thenReturn(productProperties1);
        when(productPropertyRepository.findProductPropertiesByProductId(2)).thenReturn(productProperties2);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapper.map(productProperty1, ProductPropertyResponseDto.class)).thenReturn(new ProductPropertyResponseDto());
        when(modelMapper.map(productProperty2, ProductPropertyResponseDto.class)).thenReturn(new ProductPropertyResponseDto());

        List<ProductPropertyResponseDto> result = productPropertyManager.getProductPropertyByProductId(productIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productPropertyRepository, times(1)).findProductPropertiesByProductId(1);
        verify(productPropertyRepository, times(1)).findProductPropertiesByProductId(2);
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(ProductPropertyResponseDto.class));
    }
}