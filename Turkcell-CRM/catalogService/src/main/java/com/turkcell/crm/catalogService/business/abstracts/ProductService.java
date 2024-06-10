package com.turkcell.crm.catalogService.business.abstracts;

import com.turkcell.crm.catalogService.business.dtos.request.product.CreateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.product.UpdateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.response.product.CreatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetAllProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetByIdProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;

import java.util.List;

public interface ProductService {

    CreatedProductResponse add(CreateProductRequest createProductRequest);

    List<GetAllProductResponse> getAll();

    UpdatedProductResponse update(UpdateProductRequest updateProductRequest);

    GetByIdProductResponse getById(int id);

    void delete(int id);

}
