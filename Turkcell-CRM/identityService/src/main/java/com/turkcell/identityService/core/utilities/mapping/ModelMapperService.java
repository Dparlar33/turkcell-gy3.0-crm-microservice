package com.turkcell.identityService.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public interface ModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}

