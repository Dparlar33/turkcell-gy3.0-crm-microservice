package com.turkcell.turkcellcrm.searchService.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectProductRequest {
    private int selectedProductId;
}
