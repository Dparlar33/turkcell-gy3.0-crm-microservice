package com.turkcell.crm.salesService.dataAccess;

import com.turkcell.crm.salesService.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
