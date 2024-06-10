package com.turkcell.turkcellcrm.searchService.business.dto.dynamics;

import com.turkcell.turkcellcrm.searchService.business.dto.enums.FilterOperator;

public record DynamicFilter(
        String field,
        FilterOperator operator,
        String value,
        String valueType
) {
}
