package com.turkcell.turkcellcrm.searchService.business.concretes;

import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicFilter;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicSort;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllProductResponse;
import com.turkcell.turkcellcrm.searchService.business.rules.ProductFilterBusinessRules;
import com.turkcell.turkcellcrm.searchService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchProductRepository;
import com.turkcell.turkcellcrm.searchService.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.sound.sampled.Port;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchProductManagerTest {

    @Mock
    private SearchProductRepository searchProductRepository;
    @Mock
    private ProductFilterBusinessRules productFilterBusinessRules;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchProductManager searchProductManager;


    @Test
    void testAdd() {

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        Product product = new Product();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(productCreatedEvent,Product.class)).thenReturn(product);

        searchProductManager.add(productCreatedEvent);

        verify(modelMapperService.forRequest()).map(productCreatedEvent,Product.class);
        verify(searchProductRepository).save(product);
        assertDoesNotThrow(()->searchProductManager.add(productCreatedEvent));
    }

    @Test
    void testGetAll() {

        List<DynamicFilter> filters = Collections.emptyList();
        List<DynamicSort> sorts = Collections.emptyList();
        DynamicQuery dynamicQuery = new DynamicQuery(filters,sorts);

        Product product1 = new Product();
        Product product2 = new Product();

        List<Product> productList = Arrays.asList(product1, product2);
        GetAllProductResponse response1 = new GetAllProductResponse();
        GetAllProductResponse response2 = new GetAllProductResponse();

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(searchService.dynamicSearch(dynamicQuery, Product.class)).thenReturn(productList);
        when(modelMapper.map(product1, GetAllProductResponse.class)).thenReturn(response1);
        when(modelMapper.map(product2, GetAllProductResponse.class)).thenReturn(response2);

        List<GetAllProductResponse> result = searchProductManager.getAll(dynamicQuery);

        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));

        verify(searchService, times(1)).dynamicSearch(dynamicQuery, Product.class);
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllProductResponse.class));
    }

    @Test
    void testUpdate() {

        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        Product product = new Product();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(productUpdatedEvent, Product.class)).thenReturn(product);

        searchProductManager.update(productUpdatedEvent);

        verify(modelMapperService.forRequest()).map(productUpdatedEvent, Product.class);
        verify(searchProductRepository).save(product);
    }

    @Test
    void testDelete() {

        int productId = 1;
        Product product = new Product();

        when(productFilterBusinessRules.IsProductAlreadyDeleted(productId)).thenReturn(product);

        searchProductManager.delete(productId);

        verify(productFilterBusinessRules).IsProductAlreadyDeleted(productId);
        verify(searchProductRepository).save(product);
    }
}


