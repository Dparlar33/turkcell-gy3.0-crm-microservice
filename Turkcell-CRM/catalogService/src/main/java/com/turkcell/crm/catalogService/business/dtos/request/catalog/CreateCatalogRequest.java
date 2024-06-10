package com.turkcell.crm.catalogService.business.dtos.request.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCatalogRequest {
    private String catalogName;
}
