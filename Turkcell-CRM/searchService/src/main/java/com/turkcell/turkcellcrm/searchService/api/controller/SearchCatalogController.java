package com.turkcell.turkcellcrm.searchService.api.controller;

import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchCatalogService;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCatalogResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchservice/api/v1/filters/searchcatalog")
@AllArgsConstructor
public class SearchCatalogController {

    private SearchCatalogService searchCatalogService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllCatalogResponse> getAll(){

        return this.searchCatalogService.getAllCatalog();
    }
}
