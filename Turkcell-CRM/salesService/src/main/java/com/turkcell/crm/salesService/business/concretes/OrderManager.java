package com.turkcell.crm.salesService.business.concretes;


import com.turkcell.crm.salesService.api.client.AccountClient;
import com.turkcell.crm.salesService.api.client.CustomerClient;
import com.turkcell.crm.salesService.business.abstracts.OrderService;
import com.turkcell.crm.salesService.business.dto.response.GetAllOrderResponse;
import com.turkcell.crm.salesService.business.dto.response.GetByIdOrderResponse;
import com.turkcell.crm.salesService.business.dto.response.OrderAccountResponse;
import com.turkcell.crm.salesService.core.mapping.ModelMapperService;
import com.turkcell.crm.salesService.dataAccess.OrderRepository;
import com.turkcell.crm.salesService.entities.Order;
import com.turkcell.crm.salesService.entities.OrderItem;
import com.turkcell.crm.salesService.kafka.producers.OrderProducer;
import com.turkcell.turkcellcrm.common.events.basket.BasketItemDto;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderManager implements OrderService {

    private OrderRepository orderRepository;
    private CustomerClient customerClient;
    private OrderProducer orderProducer;
    private ModelMapperService modelMapperService;
    private AccountClient accountClient;

    @Override
    @Transactional
    public void add(CreateOrderRequest createOrderRequest) {

        int addressId = this.customerClient.getAddressIdByCustomerId(createOrderRequest.getCustomerId());

        Order order = this.modelMapperService.forResponse().map(createOrderRequest, Order.class);
        order.setAddressId(addressId);

        order.setOrderItems(setOrderItemList(createOrderRequest));
        this.orderRepository.save(order);

        OrderAccountResponse orderAccountResponse = new OrderAccountResponse();
        orderAccountResponse.setOrderId(order.getId());
        orderAccountResponse.setAccountId(order.getAccountId());
        this.accountClient.setAccountStatusByOrderId(orderAccountResponse);

        OrderCreatedEvent orderCreatedEvent = this.modelMapperService.forResponse().map(order, OrderCreatedEvent.class);
        this.orderProducer.sendCreatedMessage(orderCreatedEvent);
    }

    @Override
    public List<GetAllOrderResponse> getAll() {

        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().
                map(order -> this.modelMapperService.forResponse().map(order, GetAllOrderResponse.class)).toList();
    }

    @Override
    public GetByIdOrderResponse getById(String orderId){

        Order order = this.orderRepository.findById(orderId).orElse(null);

        GetByIdOrderResponse getByIdOrderResponse = this.modelMapperService.forResponse().
                map(order, GetByIdOrderResponse.class);

        return getByIdOrderResponse;
    }

    public List<OrderItem> setOrderItemList(CreateOrderRequest createOrderRequest){

        List<OrderItem> orderItemList = new ArrayList<>();
        for(BasketItemDto basketItemDto : createOrderRequest.getBasketItemDtos()){
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setProductId(basketItemDto.getProductId());
            orderItem1.setProductName(basketItemDto.getName());

            orderItemList.add(orderItem1);
        }
        return orderItemList;
    }
}


