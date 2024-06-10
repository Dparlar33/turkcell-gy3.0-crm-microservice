package com.turkcell.crm.catalogService.business.concretes;

import com.turkcell.crm.catalogService.business.abstracts.ProductPropertyService;
import com.turkcell.crm.catalogService.business.abstracts.ProductService;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.CreateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.request.productProperties.UpdateProductPropertyRequest;
import com.turkcell.crm.catalogService.business.dtos.response.ProductPropertyResponseDto;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.CreatedProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetAllProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.GetByIdProductPropertyResponse;
import com.turkcell.crm.catalogService.business.dtos.response.productProperties.UpdatedProductProductResponse;
import com.turkcell.crm.catalogService.business.rules.ProductPropertyBusinessRules;
import com.turkcell.crm.catalogService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.catalogService.dataAccess.ProductPropertyRepository;
import com.turkcell.crm.catalogService.entity.ProductProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductPropertyManager implements ProductPropertyService {

    private ModelMapperService modelMapperService;
    private ProductPropertyRepository productPropertyRepository;
    private ProductPropertyBusinessRules productPropertyBusinessRules;
    private ProductService productService;

    @Override
    public CreatedProductPropertyResponse add(CreateProductPropertyRequest createProductPropertyRequest) {

        this.productService.getById(createProductPropertyRequest.getProductId());

        ProductProperty productProperty = this.modelMapperService.forRequest().map(createProductPropertyRequest, ProductProperty.class);
        productProperty.setId(0);

        ProductProperty saveProduct = this.productPropertyRepository.save(productProperty);

        CreatedProductPropertyResponse createdProductPropertyResponse = this.modelMapperService.forResponse()
                .map(saveProduct, CreatedProductPropertyResponse.class);
        return createdProductPropertyResponse;
    }

    @Override
    public List<GetAllProductPropertyResponse> getAll() {

        List<ProductProperty> productProperties = this.productPropertyRepository.findByDeletedDateIsNull();

        List<GetAllProductPropertyResponse> getAllProductPropertyResponse = productProperties.stream()
                .map(productProperty -> this.modelMapperService.forResponse().map(productProperty, GetAllProductPropertyResponse.class)).toList();

        return getAllProductPropertyResponse;
    }

    @Override
    public UpdatedProductProductResponse update(UpdateProductPropertyRequest updateProductPropertyRequest) {

        this.productService.getById(updateProductPropertyRequest.getProductId());
        this.productPropertyBusinessRules.isProductPropertyAlreadyDeleted(updateProductPropertyRequest.getId());
        this.productPropertyBusinessRules.isProductPropertyExistById(updateProductPropertyRequest.getId());

        ProductProperty productProperty = this.modelMapperService.forRequest().map(updateProductPropertyRequest, ProductProperty.class);
        return this.modelMapperService.forResponse().
                map(this.productPropertyRepository.save(productProperty), UpdatedProductProductResponse.class);
    }

    @Override
    public GetByIdProductPropertyResponse getById(int id) {

        this.productPropertyBusinessRules.isProductPropertyAlreadyDeleted(id);
        this.productPropertyBusinessRules.isProductPropertyExistById(id);

        Optional<ProductProperty> productProperty = this.productPropertyRepository.findById(id);

        return this.modelMapperService.forResponse().map(productProperty.get(), GetByIdProductPropertyResponse.class);
    }

    @Override
    public void delete(int id) {

        ProductProperty productProperty = this.productPropertyBusinessRules.isProductPropertyAlreadyDeleted(id);
        productProperty.setDeletedDate(LocalDateTime.now());

        //this.customerProducer.sendDeletedMessage(id);

        this.productPropertyRepository.save(productProperty);
    }

    @Override
    public List<ProductPropertyResponseDto> getProductPropertyByProductId(List<Integer> productIds) {

        List<ProductPropertyResponseDto> productPropertyResponseDto = new ArrayList<>();

        for(Integer productId : productIds) {

            List<ProductProperty> productProperty = this.productPropertyRepository.findProductPropertiesByProductId(productId);

           List<ProductPropertyResponseDto> productPropertyResponseDtos = productProperty.stream().map(productProperty1 ->
                    this.modelMapperService.forResponse().
                            map(productProperty1, ProductPropertyResponseDto.class)).toList();

            productPropertyResponseDto.addAll(productPropertyResponseDtos);
        }

        return productPropertyResponseDto;
    }
}
