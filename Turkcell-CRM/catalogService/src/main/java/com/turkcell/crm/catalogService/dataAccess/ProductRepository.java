package com.turkcell.crm.catalogService.dataAccess;

import com.turkcell.crm.catalogService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByDeletedDateIsNull();
}
