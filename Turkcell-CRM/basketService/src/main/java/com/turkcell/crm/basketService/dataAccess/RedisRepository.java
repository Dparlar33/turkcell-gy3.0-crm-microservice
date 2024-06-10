package com.turkcell.crm.basketService.dataAccess;

import com.turkcell.crm.basketService.entity.Basket;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;



@Repository
public class RedisRepository {

    public static final String Key="BASKET";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String,String,Basket> hashOperations;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations=redisTemplate.opsForHash();
    }

    public Map<String,Basket> getAllItems(){
        return this.hashOperations.entries(Key);
    }

    public void addItem(Basket basket){
        this.hashOperations.put(Key,basket.getId()+"_"+basket.getAccountId(),basket);
    }

    public Basket getBasketByAccountId(int accountId) {
        return hashOperations.entries(Key).values().stream()
                .filter(basket -> accountId == (basket.getAccountId()))
                .findFirst()
                .orElse(null);
    }

    public void deleteItem(String id){
        this.hashOperations.delete(Key,id);
    }
}
