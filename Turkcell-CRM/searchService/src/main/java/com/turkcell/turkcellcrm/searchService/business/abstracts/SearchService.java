package com.turkcell.turkcellcrm.searchService.business.abstracts;

import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;

import java.util.List;

public interface SearchService {
    <T> List<T> dynamicSearch(DynamicQuery dynamicQuery, Class<T> type);
}
