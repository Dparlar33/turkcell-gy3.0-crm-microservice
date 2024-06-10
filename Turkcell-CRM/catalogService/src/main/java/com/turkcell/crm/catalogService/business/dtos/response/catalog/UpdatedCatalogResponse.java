package com.turkcell.crm.catalogService.business.dtos.response.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatedCatalogResponse {
    private int id;
    private String catalogName;
}
