package com.turkcell.turkcellcrm.searchService.business.dto.dynamics;

import com.turkcell.turkcellcrm.searchService.business.dto.enums.SortDirection;

public record DynamicSort(
        String field,
        SortDirection direction
) {
}
