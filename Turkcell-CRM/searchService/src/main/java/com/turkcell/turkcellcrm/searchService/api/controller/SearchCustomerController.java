package com.turkcell.turkcellcrm.searchService.api.controller;

import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchCustomerService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/searchservice/api/v1/filters/searchcustomer")
@AllArgsConstructor
public class SearchCustomerController {

    private SearchCustomerService searchCustomerService;

    @PostMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllCustomerResponse> getAll(@RequestBody DynamicQuery dynamicQuery){
        return this.searchCustomerService.getAll(dynamicQuery);
    }
}
