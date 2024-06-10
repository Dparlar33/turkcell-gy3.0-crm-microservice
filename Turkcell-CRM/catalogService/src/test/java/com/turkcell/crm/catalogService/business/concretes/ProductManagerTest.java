package com.turkcell.crm.catalogService.business.concretes;

import com.turkcell.crm.catalogService.business.dtos.request.product.CreateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.product.UpdateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.response.product.CreatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetAllProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetByIdProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;
import com.turkcell.crm.catalogService.business.messages.CatalogMessages;
import com.turkcell.crm.catalogService.business.messages.ProductMessages;
import com.turkcell.crm.catalogService.business.rules.ProductBusinessRules;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.catalogService.dataAccess.ProductRepository;
import com.turkcell.crm.catalogService.entity.Catalog;
import com.turkcell.crm.catalogService.entity.Product;
import com.turkcell.crm.catalogService.kafka.producers.ProductProducer;
import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductDeletedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductManagerTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    ModelMapperService modelMapperService;


    @Mock
    ProductProducer productProducer;


    @Mock
    ProductBusinessRules productBusinessRules;

    @InjectMocks
    ProductManager productManager;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add() {

        Product product = new Product();
        product.setId(0);
        product.setName("Iphone");

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName("Iphone");

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Iphone");

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createProductRequest, Product.class)).thenReturn(product);

        when(productRepository.save(product)).thenReturn(savedProduct);

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        productCreatedEvent.setProductId(1);
        productCreatedEvent.setName("Iphone");

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(savedProduct, ProductCreatedEvent.class)).thenReturn(productCreatedEvent);


        CreatedProductResponse createdProductResponse = new CreatedProductResponse();
        createdProductResponse.setId(1);
        createdProductResponse.setName("Iphone");

        when(modelMapper.map(savedProduct, CreatedProductResponse.class)).thenReturn(createdProductResponse);

        CreatedProductResponse result = productManager.add(createProductRequest);
        assertEquals(createdProductResponse, result);

        verify(modelMapperService, times(2)).forRequest();
        verify(modelMapper).map(createProductRequest, Product.class);
        verify(productRepository).save(product);
        verify(modelMapper).map(savedProduct, ProductCreatedEvent.class);
        verify(productProducer).sendCreatedMessage(productCreatedEvent);
        verify(modelMapperService).forResponse();
        verify(modelMapper).map(savedProduct, CreatedProductResponse.class);

    }

    @Test
    public void testUpdate() {

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setId(1);
        Product product = new Product();
        product.setId(1);
        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setUpdatedDate(LocalDateTime.now());
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        UpdatedProductResponse updatedProductResponse = new UpdatedProductResponse();


        when(productBusinessRules.isProductAlreadyDeleted(updateProductRequest.getId())).thenReturn(product);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateProductRequest, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(modelMapper.map(savedProduct, ProductUpdatedEvent.class)).thenReturn(productUpdatedEvent);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(savedProduct, UpdatedProductResponse.class)).thenReturn(updatedProductResponse);


        UpdatedProductResponse result = productManager.update(updateProductRequest);


        verify(productBusinessRules).isProductAlreadyDeleted(product.getId());
        verify(productRepository).save(product);
        verify(productProducer).sendUpdatedMessage(productUpdatedEvent);


        assertEquals(updatedProductResponse, result);
    }

    @Test
    public void testUpdate_ThrowsExceptionWhenProductAlreadyDeleted() {

        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(1);

        when(productBusinessRules.isProductAlreadyDeleted(request.getId())).thenThrow(new BusinessException(ProductMessages.PRODUCT_ALREADY_DELETED));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            productManager.update(request);

        });

        assertEquals(ProductMessages.PRODUCT_ALREADY_DELETED, exception.getMessage());

        verify(productBusinessRules).isProductAlreadyDeleted(request.getId());
        verify(productRepository, never()).save(any(Product.class));
        verify(productProducer, never()).sendUpdatedMessage(any(ProductUpdatedEvent.class));
    }


    @Test
    void getById() {


        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        Optional<Product> optionalProduct = Optional.of(product);

        when(productRepository.findById(1)).thenReturn(optionalProduct);

        when(productBusinessRules.isProductAlreadyDeleted(product.getId())).thenReturn(product);
        when(productBusinessRules.isProductExistById(product.getId())).thenReturn(product);

        GetByIdProductResponse response = new GetByIdProductResponse();
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(product, GetByIdProductResponse.class)).thenReturn(response);

        GetByIdProductResponse actualResponse = productManager.getById(1);

        verify(productRepository).findById(1);

        verify(productBusinessRules).isProductAlreadyDeleted(1);
        verify(productBusinessRules).isProductExistById(1);

        verify(modelMapperService.forResponse()).map(product, GetByIdProductResponse.class);

        assertEquals(response, actualResponse);
    }

    @Test
    public void testGetById_ThrowsExceptionWhenProductAlreadyDeleted(){


        int productId = 1;

        doThrow(new BusinessException(ProductMessages.PRODUCT_ALREADY_DELETED)).when(productBusinessRules).isProductAlreadyDeleted(productId);


        assertThrows(BusinessException.class, () -> productManager.getById(productId));


        verify(productBusinessRules).isProductAlreadyDeleted(productId);
        verify(productRepository, never()).findById(anyInt());
        verify(productBusinessRules, never()).isProductExistById(anyInt());
        verify(modelMapperService, never()).forResponse();

    }

    @Test
    public void testGetAll() {

        Product product = new Product();
        product.setId(1);
        product.setName("Ürün 1");
        product.setCatalog(new Catalog());
        product.setDescription("Ürün açıklaması");
        product.setPrice(12);
        product.setUnitInStock(10);

        Product product1 = new Product();
        product1.setId(2);
        product1.setName("Ürün 2");
        product1.setCatalog(new Catalog());
        product1.setDescription("Ürün açıklaması 2");
        product1.setPrice(123);
        product1.setUnitInStock(101);

        List<Product> products = Arrays.asList(product, product1);

        when(productRepository.findByDeletedDateIsNull()).thenReturn(products);

        GetAllProductResponse response1 = new GetAllProductResponse();
        response1.setId(1);
        response1.setName("Ürün 1");

        GetAllProductResponse response2 = new GetAllProductResponse();
        response2.setId(2);
        response2.setName("Ürün 2");

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(product, GetAllProductResponse.class)).thenReturn(response1);
        when(modelMapperService.forResponse().map(product1, GetAllProductResponse.class)).thenReturn(response2);

        List<GetAllProductResponse> result = productManager.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Ürün 1", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Ürün 2", result.get(1).getName());

        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllProductResponse.class));
    }

    @Test
    public void testGetAll_whenProductAlreadyDeleted_thenThrowException() {

        when(productRepository.findByDeletedDateIsNull()).thenThrow(new BusinessException("PRODUCT ALREADY DELETED"));

        assertThrows(BusinessException.class, () -> productManager.getAll());

        verify(productRepository).findByDeletedDateIsNull();
        verify(modelMapperService, never()).forResponse();
    }


    @Test
    public void testDelete() {

        Product product = new Product();
        product.setId(1);
        product.setDeletedDate(LocalDateTime.now());

        ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();

        when(productBusinessRules.isProductAlreadyDeleted(product.getId())).thenReturn(product);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(product, ProductDeletedEvent.class)).thenReturn(productDeletedEvent);

        productManager.delete(product.getId());

        verify(productRepository).save(product);
        verify(modelMapperService.forRequest()).map(product, ProductDeletedEvent.class);
        verify(productProducer).sendDeletedMessage(any(ProductDeletedEvent.class));
        verify(productBusinessRules).isProductAlreadyDeleted(product.getId());

        assertEquals(LocalDateTime.now().getDayOfYear(), product.getDeletedDate().getDayOfYear());

    }
    @Test
    void testDelete_whenProductAlreadyDeleted_thenThrowException() {

        int productId = 1;

        when(productBusinessRules.isProductAlreadyDeleted(productId)).thenThrow(new BusinessException(ProductMessages.PRODUCT_ALREADY_DELETED));

        assertThrows(BusinessException.class, () -> productManager.delete(productId));

        verify(productBusinessRules).isProductAlreadyDeleted(productId);
        verify(productRepository, never()).save(any());
        verify(modelMapperService, never()).forRequest();
        verify(productProducer, never()).sendDeletedMessage(any());
    }
}