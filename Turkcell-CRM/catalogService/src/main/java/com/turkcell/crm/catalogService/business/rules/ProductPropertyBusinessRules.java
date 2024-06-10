package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.ProductPropertyMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.ProductPropertyRepository;
import com.turkcell.crm.catalogService.entity.ProductProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductPropertyBusinessRules {

    private ProductPropertyRepository productPropertyRepository;
    private MessageService messageService;

    public ProductProperty isProductPropertyExistById(int id){

        Optional<ProductProperty> productProperty = this.productPropertyRepository.findById(id);

        if (productProperty.isEmpty()){
            throw new BusinessException(messageService.getMessage(ProductPropertyMessages.PRODUCT_PROPERTY_NOT_FOUND));
        }

        return productProperty.get();
    }


    public ProductProperty isProductPropertyAlreadyDeleted(int id){

        ProductProperty productProperty = this.isProductPropertyExistById(id);

        if(productProperty.getDeletedDate() != null) {
            throw new BusinessException(messageService.getMessage(ProductPropertyMessages.PRODUCT_PROPERTY_ALREADY_DELETED));
        }

        return productProperty;

    }
}
