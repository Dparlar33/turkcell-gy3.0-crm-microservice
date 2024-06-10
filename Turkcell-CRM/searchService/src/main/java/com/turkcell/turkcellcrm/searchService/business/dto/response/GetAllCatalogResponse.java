package com.turkcell.turkcellcrm.searchService.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCatalogResponse {
    private ObjectId id;
    private int catalogId;
    private String name;
}
