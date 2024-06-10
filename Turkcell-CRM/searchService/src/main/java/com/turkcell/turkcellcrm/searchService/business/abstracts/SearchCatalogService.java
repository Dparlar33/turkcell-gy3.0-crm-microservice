package com.turkcell.turkcellcrm.searchService.business.abstracts;

import com.turkcell.turkcellcrm.common.events.catalog.CatalogCreatedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCatalogResponse;

import java.util.List;

public interface SearchCatalogService {

    void add(CatalogCreatedEvent catalogCreatedEvent);

    List<GetAllCatalogResponse> getAllCatalog();

    void update(CatalogUpdatedEvent catalogUpdatedEvent);

    void delete(int id);
}
