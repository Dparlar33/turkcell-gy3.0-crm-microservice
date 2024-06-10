package com.turkcell.crm.invoiceService.core.utilities.mapping;

import org.modelmapper.ModelMapper;


public interface ModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
