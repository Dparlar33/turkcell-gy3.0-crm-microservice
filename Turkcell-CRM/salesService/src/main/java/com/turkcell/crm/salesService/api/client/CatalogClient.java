package com.turkcell.crm.salesService.api.client;

import com.turkcell.crm.salesService.business.dto.response.ProductPropertyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "catalogService", url = "http://localhost:10053/catalogservice/api/v1/catalogs/productproperties")
public interface CatalogClient {
    @GetMapping("/getById")
    List<ProductPropertyResponseDto> getProductPropertyIdByProductId(@RequestBody List<Integer> productIds);
}
