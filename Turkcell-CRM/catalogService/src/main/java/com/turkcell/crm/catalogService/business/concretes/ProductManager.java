package com.turkcell.crm.catalogService.business.concretes;

import com.turkcell.crm.catalogService.business.abstracts.ProductService;
import com.turkcell.crm.catalogService.business.dtos.request.product.CreateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.request.product.UpdateProductRequest;
import com.turkcell.crm.catalogService.business.dtos.response.product.CreatedProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetAllProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.GetByIdProductResponse;
import com.turkcell.crm.catalogService.business.dtos.response.product.UpdatedProductResponse;
import com.turkcell.crm.catalogService.business.rules.ProductBusinessRules;
import com.turkcell.crm.catalogService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.catalogService.dataAccess.ProductRepository;
import com.turkcell.crm.catalogService.entity.Product;
import com.turkcell.crm.catalogService.kafka.producers.ProductProducer;
import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductDeletedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {

    private ProductRepository productRepository;
    private ModelMapperService modelMapperService;
    private ProductBusinessRules productBusinessRules;
    private ProductProducer productProducer;

    @Override
    @Transactional
    public CreatedProductResponse add(CreateProductRequest createProductRequest) {

        Product product = this.modelMapperService.forRequest().map(createProductRequest, Product.class);
        product.setId(0);

        Product savedProduct = this.productRepository.save(product);

        ProductCreatedEvent productCreatedEvent = this.modelMapperService.forRequest().map(savedProduct, ProductCreatedEvent.class);

        this.productProducer.sendCreatedMessage(productCreatedEvent);

        return this.modelMapperService.forResponse().
                map(savedProduct, CreatedProductResponse.class);
    }

    @Override
    @Transactional
    public UpdatedProductResponse update(UpdateProductRequest updateProductRequest) {

        this.productBusinessRules.isProductAlreadyDeleted(updateProductRequest.getId());
        //this.productBusinessRules.isProductExistById(updateProductRequest.getId());

        Product product = this.modelMapperService.forRequest().map(updateProductRequest, Product.class);
        product.setUpdatedDate(LocalDateTime.now());

        Product savedProduct = this.productRepository.save(product);

        ProductUpdatedEvent productUpdatedEvent = this.modelMapperService.forRequest().map(savedProduct, ProductUpdatedEvent.class);

        this.productProducer.sendUpdatedMessage(productUpdatedEvent);

        return this.modelMapperService.forResponse().
                map(savedProduct, UpdatedProductResponse.class);
    }

    @Override
    public GetByIdProductResponse getById(int id) {

        this.productBusinessRules.isProductAlreadyDeleted(id);
        this.productBusinessRules.isProductExistById(id);

        Optional<Product> product = this.productRepository.findById(id);

        return this.modelMapperService.forResponse().map(product.get(), GetByIdProductResponse.class);

    }

    @Override
    public List<GetAllProductResponse> getAll() {

        List<Product> products = this.productRepository.findByDeletedDateIsNull();

        List<GetAllProductResponse> getAllProductResponse = products.stream().map(product -> this.modelMapperService.forResponse().
                map(product, GetAllProductResponse.class)).toList();

        return getAllProductResponse;
    }

    @Override
    @Transactional
    public void delete(int id) {

        Product product = this.productBusinessRules.isProductAlreadyDeleted(id);
        product.setDeletedDate(LocalDateTime.now());

        this.productRepository.save(product);

        ProductDeletedEvent productDeletedEvent = this.modelMapperService.forRequest().map(product, ProductDeletedEvent.class);

        this.productProducer.sendDeletedMessage(productDeletedEvent);

    }




}
