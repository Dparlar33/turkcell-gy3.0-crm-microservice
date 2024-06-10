package com.turkcell.turkcellcrm.searchService.entities;

import com.turkcell.turkcellcrm.searchService.core.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "catalogs")
public class Catalog extends BaseEntity {

    @Field(name = "catalodId")
    private int catalogId;

    @Field(name = "name")
    private String name;
}
