package com.turkcell.turkcellcrm.searchService.business.rules;

import com.turkcell.turkcellcrm.searchService.business.messages.ProductFilterBusinessRulesMessages;
import com.turkcell.turkcellcrm.searchService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchProductRepository;
import com.turkcell.turkcellcrm.searchService.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductFilterBusinessRules {

    private SearchProductRepository searchProductRepository;

    public Product IsProductAlreadyDeleted(int productId){

       Product product = this.searchProductRepository.findProductByProductId(productId).
               orElseThrow(() -> new BusinessException(ProductFilterBusinessRulesMessages.PRODUCT_NOT_EXISTS));

        if(product.getDeletedDate() != null){
            throw new BusinessException(ProductFilterBusinessRulesMessages.PRODUCT_IS_ALREADY_DELETED);
        }

        return product;
    }
}
