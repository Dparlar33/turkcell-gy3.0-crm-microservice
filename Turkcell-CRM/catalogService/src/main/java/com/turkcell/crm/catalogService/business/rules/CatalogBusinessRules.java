package com.turkcell.crm.catalogService.business.rules;

import com.turkcell.crm.catalogService.business.messages.CatalogMessages;
import com.turkcell.crm.catalogService.core.business.abstracts.MessageService;
import com.turkcell.crm.catalogService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.crm.catalogService.dataAccess.CatalogRepository;
import com.turkcell.crm.catalogService.entity.Catalog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CatalogBusinessRules {

    private CatalogRepository catalogRepository;
    private MessageService messageService;

    public Catalog isCatalogExistById(int id){

        Optional<Catalog> catalog = this.catalogRepository.findById(id);

        if (catalog.isEmpty()){
            throw new BusinessException(messageService.getMessage(CatalogMessages.CATALOG_NOT_FOUND));
        }

        return catalog.get();
    }

    public Catalog isCatalogAlreadyDeleted(int id){

        Catalog catalog = this.isCatalogExistById(id);

        if(catalog.getDeletedDate() != null) {
            throw new BusinessException(messageService.getMessage(CatalogMessages.CATALOG_ALREADY_DELETED));
        }

        return catalog;
    }


}
