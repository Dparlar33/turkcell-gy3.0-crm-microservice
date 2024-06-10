package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.ProductMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.ProductRepository;
import com.turkcell.crm.catalogService.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductBusinessRules {
    private ProductRepository productRepository;
    private MessageService messageService;

    public Product isProductExistById(int id){

        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty()){
            throw new BusinessException(messageService.getMessage(ProductMessages.PRODUCT_NOT_FOUND));
        }

        return product.get();
    }

    public Product isProductAlreadyDeleted(int id){

        Product product = this.isProductExistById(id);

        if(product.getDeletedDate() != null) {
            throw new BusinessException(messageService.getMessage(ProductMessages.PRODUCT_ALREADY_DELETED));
        }

        return product;
    }
}
