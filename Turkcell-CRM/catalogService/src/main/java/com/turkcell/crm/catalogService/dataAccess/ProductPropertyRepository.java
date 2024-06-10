package com.turkcell.crm.catalogService.dataAccess;

import com.turkcell.crm.catalogService.entity.ProductProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty,Integer> {

    List<ProductProperty> findByDeletedDateIsNull();
    List<ProductProperty> findProductPropertiesByProductId(int productId);

}
