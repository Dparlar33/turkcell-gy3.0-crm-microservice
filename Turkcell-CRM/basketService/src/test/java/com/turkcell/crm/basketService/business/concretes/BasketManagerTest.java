package com.turkcell.crm.basketService.business.concretes;

import com.turkcell.crm.basketService.api.client.OrderClient;
import com.turkcell.crm.basketService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.basketService.dataAccess.RedisRepository;
import com.turkcell.crm.basketService.entity.Basket;
import com.turkcell.crm.basketService.entity.BasketItem;
import com.turkcell.turkcellcrm.common.events.basket.CreateBasketItemRequest;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketManagerTest {

    @Mock
    private RedisRepository redisRepository;

    @Mock
    private OrderClient orderClient;

    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BasketManager basketManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_shouldCreateNewBasket_whenBasketIsNull() {

        CreateBasketItemRequest request = new CreateBasketItemRequest();
        request.setAccountId(1);
        request.setProductId(1);
        request.setName("Product 1");
        request.setPrice(100);
        request.setCustomerId(1);

        when(redisRepository.getBasketByAccountId(1)).thenReturn(null);

        basketManager.add(request);

        ArgumentCaptor<Basket> basketCaptor = ArgumentCaptor.forClass(Basket.class);
        verify(redisRepository, times(1)).addItem(basketCaptor.capture());

        Basket capturedBasket = basketCaptor.getValue();
        assertNotNull(capturedBasket);
        assertEquals(1, capturedBasket.getAccountId());
        assertEquals(1, capturedBasket.getBasketItems().size());
        assertEquals(100, capturedBasket.getTotalPrice());
    }
    @Test
    void add_shouldAddBasketItem_whenBasketExists() {

        CreateBasketItemRequest request = new CreateBasketItemRequest();
        request.setAccountId(1);
        request.setProductId(1);
        request.setName("Product 1");
        request.setPrice(100);
        request.setCustomerId(1);

        Basket basket = new Basket();
        basket.setAccountId(1);
        basket.setBasketItems(new ArrayList<>());
        basket.setTotalPrice(0);

        when(redisRepository.getBasketByAccountId(1)).thenReturn(basket);

        basketManager.add(request);

        verify(redisRepository, times(1)).addItem(any(Basket.class));
        assertEquals(1, basket.getBasketItems().size());
        assertEquals(100, basket.getTotalPrice());
    }

    @Test
    void getAllItems_shouldReturnAllItems() {

        Map<String, Basket> baskets = new HashMap<>();
        when(redisRepository.getAllItems()).thenReturn(baskets);

        Map<String, Basket> result = basketManager.getAllItems();

        verify(redisRepository, times(1)).getAllItems();
        assertEquals(baskets, result);
    }

    @Test
    void delete_shouldDeleteItem() {

        String id = "itemId";

        basketManager.delete(id);

        verify(redisRepository, times(1)).deleteItem(id);
    }

    @Test
    void deleteItem_shouldRemoveBasketItem() {

        int productId = 1;
        int accountId = 1;
        Basket basket = new Basket();
        BasketItem basketItem = new BasketItem();
        basketItem.setProductId(productId);
        List<BasketItem> basketItems = new ArrayList<>();
        basketItems.add(basketItem);
        basket.setBasketItems(basketItems);

        when(redisRepository.getBasketByAccountId(accountId)).thenReturn(basket);

        basketManager.deleteItem(productId, accountId);

        verify(redisRepository, times(1)).addItem(basket);
        assertEquals(0, basket.getBasketItems().size());
    }

    @Test
    void basketToOrder_shouldConvertBasketToOrder() {

        int accountId = 1;
        Basket basket = new Basket();
        basket.setAccountId(accountId);
        List<BasketItem> basketItems = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setProductId(1);
        basketItem.setName("Product 1");
        basketItem.setPrice(100);
        basketItems.add(basketItem);
        basket.setBasketItems(basketItems);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        when(redisRepository.getBasketByAccountId(accountId)).thenReturn(basket);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(basket, CreateOrderRequest.class)).thenReturn(createOrderRequest);

        basketManager.basketToOrder(accountId);

        verify(orderClient, times(1)).add(any(CreateOrderRequest.class));
    }
}