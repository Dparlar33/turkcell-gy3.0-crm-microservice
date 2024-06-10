package com.turkcell.crm.catalogService.api.controller;

import com.turkcell.crm.catalogService.business.abstracts.ProductPropertyService;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.CreateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.UpdateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.response.ProductPropertyResponseDto;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.CreatedProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetAllProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetByIdProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.UpdatedProductProductResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogservice/api/v1/catalogs/productproperties")
@AllArgsConstructor
public class ProductPropertiesController {
    private ProductPropertyService productPropertyService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedProductPropertyResponse add(@Valid @RequestBody CreateProductPropertyRequest createProductPropertyRequest){

        return this.productPropertyService.add(createProductPropertyRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllProductPropertyResponse> getAll(){
        return this.productPropertyService.getAll();
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetByIdProductPropertyResponse getById(@PathVariable int id){
        return this.productPropertyService.getById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UpdatedProductProductResponse update(@Valid @RequestBody UpdateProductPropertyRequest updateProductPropertyRequest){

        return this.productPropertyService.update(updateProductPropertyRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        this.productPropertyService.delete(id);
    }


    @GetMapping("/getById")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductPropertyResponseDto> getProductPropertyByProductId(@RequestBody List<Integer> productIds){
        return this.productPropertyService.getProductPropertyByProductId(productIds);
    }
}
