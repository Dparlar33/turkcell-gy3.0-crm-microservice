package com.turkcell.crm.salesService.business.concretes;

import com.turkcell.crm.salesService.api.client.CatalogClient;
import com.turkcell.crm.salesService.business.abstracts.ProductConfigurationService;
import com.turkcell.crm.salesService.business.dto.response.GetAllProductConfigResponse;
import com.turkcell.crm.salesService.business.dto.response.ProductPropertyResponseDto;
import com.turkcell.crm.salesService.core.mapping.ModelMapperService;
import com.turkcell.crm.salesService.entities.ProductConfig;
import com.turkcell.turkcellcrm.common.events.basket.BasketItemDto;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductConfigurationManager implements ProductConfigurationService {

    private CatalogClient catalogClient;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllProductConfigResponse> getAllProductConfig(CreateOrderRequest createOrderRequest) {

        List<BasketItemDto> basketItemDto = createOrderRequest.getBasketItemDtos();
        List<Integer> productIds = new ArrayList<>();

        for (BasketItemDto basketItemDto1 : basketItemDto) {
            productIds.add(basketItemDto1.getProductId());
        }

        List<ProductPropertyResponseDto> productPropertyResponseDto =
                this.catalogClient.getProductPropertyIdByProductId(productIds);

        List<ProductConfig> productConfigs = productPropertyResponseDto.stream().map(productPropertyResponseDto1 ->
                this.modelMapperService.forResponse().map(productPropertyResponseDto1, ProductConfig.class)).toList();


        List<GetAllProductConfigResponse> getAllProductConfigResponses = productConfigs.stream().map(productConfig ->
                this.modelMapperService.forResponse().map(productConfig, GetAllProductConfigResponse.class)).toList();
        return getAllProductConfigResponses;
    }
}
