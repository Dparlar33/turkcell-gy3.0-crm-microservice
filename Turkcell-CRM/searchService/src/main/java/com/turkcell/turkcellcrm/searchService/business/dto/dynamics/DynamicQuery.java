package com.turkcell.turkcellcrm.searchService.business.dto.dynamics;

import java.util.List;

public record DynamicQuery(
        List<DynamicFilter> filters,
        List<DynamicSort> sorts
) {
}
