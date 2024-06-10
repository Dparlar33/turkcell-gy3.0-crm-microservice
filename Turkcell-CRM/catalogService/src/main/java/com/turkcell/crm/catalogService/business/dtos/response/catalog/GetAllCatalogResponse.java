package com.turkcell.crm.catalogService.business.dtos.response.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCatalogResponse {

    private int id;
    private String catalogName;
}
