package com.turkcell.crm.catalogService.business.dtos.response.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdCatalogResponse {

    private int id;
    private String catalogName;
}
