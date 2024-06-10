package com.turkcell.turkcellcrm.searchService.api.controller;

import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchProductService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.request.SelectProductRequest;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/searchservice/api/v1/filters/searchproduct")
@AllArgsConstructor
public class SearchProductController {

    private SearchProductService searchProductService;

    @PostMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllProductResponse> getAll(@RequestBody DynamicQuery dynamicQuery){
        return this.searchProductService.getAll(dynamicQuery);
    }

    @PostMapping("/select")
    @ResponseStatus(HttpStatus.OK)
    public void select(@RequestBody SelectProductRequest selectProductRequest){
        this.searchProductService.select(selectProductRequest);
    }
}
