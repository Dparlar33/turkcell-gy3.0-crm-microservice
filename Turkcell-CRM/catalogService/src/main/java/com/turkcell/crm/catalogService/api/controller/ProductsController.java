package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.ProductService;
import com.turkcell.crm.catalogService.business.dtos.request.product.CreateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.product.UpdateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.response.product.CreatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetAllProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetByIdProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/catalogservice/api/v1/catalogs/product")
@AllArgsConstructor
public class ProductsController {

    private ProductService productService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedProductResponse add(@Valid @RequestBody CreateProductRequest createProductRequest) {

        return this.productService.add(createProductRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllProductResponse> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdProductResponse getById(@PathVariable int id) {
        return this.productService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedProductResponse update(@Valid @RequestBody UpdateProductRequest updateProductRequest) {

        return this.productService.update(updateProductRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        this.productService.delete(id);
    }


}
