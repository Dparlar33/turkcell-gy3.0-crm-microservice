package com.turkcell.crm.salesService.business.concretes;

import com.turkcell.crm.salesService.api.client.CustomerClient;
import com.turkcell.crm.salesService.business.dto.response.GetAllOrderResponse;
import com.turkcell.crm.salesService.core.mapping.ModelMapperService;
import com.turkcell.crm.salesService.dataAccess.OrderRepository;
import com.turkcell.crm.salesService.entities.Order;
import com.turkcell.crm.salesService.entities.OrderItem;
import com.turkcell.crm.salesService.kafka.producers.OrderProducer;
import com.turkcell.turkcellcrm.common.events.basket.BasketItemDto;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderManagerTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerClient customerClient;


    @Mock
    private OrderProducer orderProducer;

    @Mock
    private ModelMapper modelMapper ;

    @Mock
    private ModelMapperService modelMapperService;

    @InjectMocks
    private OrderManager orderManager;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId(1);
        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setProductId(1);
        basketItemDto.setName("Product 1");
        createOrderRequest.setBasketItemDtos(Collections.singletonList(basketItemDto));

        Order order = new Order();
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createOrderRequest, Order.class)).thenReturn(order);
        when(customerClient.getAddressIdByCustomerId(1)).thenReturn(1);


        orderManager.add(createOrderRequest);

        verify(orderRepository).save(any(Order.class));
        verify(orderProducer).sendCreatedMessage(any(OrderCreatedEvent.class));
        verify(modelMapperService.forRequest(), times(1)).map(any(), eq(Order.class));
    }

    @Test
    void testGetAll() {
        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        GetAllOrderResponse getAllOrderResponse = new GetAllOrderResponse();
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(order, GetAllOrderResponse.class)).thenReturn(getAllOrderResponse);

        List<GetAllOrderResponse> result = orderManager.getAll();

        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }


    @Test
    void testSetOrderItemList() {

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setProductId(1);
        basketItemDto.setName("Product 1");
        createOrderRequest.setBasketItemDtos(Collections.singletonList(basketItemDto));

        List<OrderItem> orderItems = orderManager.setOrderItemList(createOrderRequest);

        assertEquals(1, orderItems.size());
        assertEquals(1, orderItems.get(0).getProductId());
        assertEquals("Product 1", orderItems.get(0).getProductName());
    }
}