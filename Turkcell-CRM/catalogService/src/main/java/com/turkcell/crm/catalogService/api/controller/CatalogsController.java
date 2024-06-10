package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.CatalogService;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.CreateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.request.catalog.UpdateCatalogRequest;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.CreatedCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetAllCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.GetByIdCatalogResponse;
import com.turkcell.crm.catalogService.business.dtos.response.catalog.UpdatedCatalogResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogservice/api/v1/catalogs/catalog")
@AllArgsConstructor
public class CatalogsController {
    private CatalogService catalogService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedCatalogResponse add(@Valid @RequestBody CreateCatalogRequest createCatalogRequest) {

        return this.catalogService.add(createCatalogRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllCatalogResponse> getAll() {
        return this.catalogService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdCatalogResponse getById(@PathVariable int id) {
        return this.catalogService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedCatalogResponse update(@Valid @RequestBody UpdateCatalogRequest updateCatalogRequest) {

        return this.catalogService.update(updateCatalogRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        this.catalogService.delete(id);
    }
}
