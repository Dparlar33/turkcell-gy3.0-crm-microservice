package com.turkcell.crm.basketService.business.concretes;

import com.turkcell.crm.basketService.api.client.OrderClient;
import com.turkcell.crm.basketService.business.abstracts.BasketService;
import com.turkcell.crm.basketService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.basketService.dataAccess.RedisRepository;
import com.turkcell.crm.basketService.entity.Basket;
import com.turkcell.crm.basketService.entity.BasketItem;
import com.turkcell.turkcellcrm.common.events.basket.BasketItemDto;
import com.turkcell.turkcellcrm.common.events.basket.CreateBasketItemRequest;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BasketManager implements BasketService {

    private RedisRepository redisRepository;
    private OrderClient orderClient;
    private ModelMapperService modelMapperService;

    @Override
    public void add(CreateBasketItemRequest createBasketItemRequests) {

        Basket basket = this.redisRepository.getBasketByAccountId(createBasketItemRequests.getAccountId());

        if (basket == null) {
            basket = new Basket();
            basket.setAccountId(createBasketItemRequests.getAccountId());
        }

        BasketItem basketItem = new BasketItem();
        basketItem.setProductId(createBasketItemRequests.getProductId());
        basketItem.setName(createBasketItemRequests.getName());
        basketItem.setPrice(createBasketItemRequests.getPrice());
        basket.setCustomerId(createBasketItemRequests.getCustomerId());

        basket.setTotalPrice(basket.getTotalPrice() + basketItem.getPrice());

        basket.getBasketItems().add(basketItem);

        this.redisRepository.addItem(basket);
    }

    @Override
    public Map<String,Basket> getAllItems() {
        return this.redisRepository.getAllItems();
    }

    @Override
    public void delete (String id){

        this.redisRepository.deleteItem(id);
    }

    @Override
    public void deleteItem(int productId, int accountId) {

        Basket basket = this.redisRepository.getBasketByAccountId(accountId);
        basket.getBasketItems().remove(productId -1);

        this.redisRepository.addItem(basket);
    }

    @Override
    public void basketToOrder(int accountId){

        Basket basket = this.redisRepository.getBasketByAccountId(accountId);

        CreateOrderRequest createOrderRequest = this.modelMapperService.forResponse().
                map(basket, CreateOrderRequest.class);

        List<BasketItemDto> basketItemDtos = setBasketItems(basket);
        createOrderRequest.setBasketItemDtos(basketItemDtos);

        this.orderClient.add(createOrderRequest);
    }

    public List<BasketItemDto> setBasketItems(Basket basket) {
        List<BasketItemDto>  basketItemDtos = new ArrayList<>();

        for (BasketItem basketItem : basket.getBasketItems()){

            BasketItemDto basketItemDto = new BasketItemDto();
            basketItemDto.setName(basketItem.getName());
            basketItemDto.setPrice(basketItem.getPrice());
            basketItemDto.setProductId(basketItem.getProductId());

            basketItemDtos.add(basketItemDto);
        }
        return basketItemDtos;
    }
}
