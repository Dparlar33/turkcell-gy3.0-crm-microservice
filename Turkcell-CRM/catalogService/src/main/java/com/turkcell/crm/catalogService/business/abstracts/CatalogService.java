package com.turkcell.crm.catalogService.business.abstracts;

import com.turkcell.crm.catalogService.business.dtos.request.catalog.CreateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.UpdateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.CreatedCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetAllCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetByIdCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.UpdatedCatalogResponse;

import java.util.List;

public interface CatalogService {

    CreatedCatalogResponse add(CreateCatalogRequest createCatalogRequest);

    List<GetAllCatalogResponse> getAll();

    UpdatedCatalogResponse update(UpdateCatalogRequest updateCatalogRequest);

    GetByIdCatalogResponse getById(int id);

    void delete(int id);
}
