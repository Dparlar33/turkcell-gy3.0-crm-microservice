package com.turkcell.crm.salesService.core.mapping;

import org.modelmapper.ModelMapper;


public interface ModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}
