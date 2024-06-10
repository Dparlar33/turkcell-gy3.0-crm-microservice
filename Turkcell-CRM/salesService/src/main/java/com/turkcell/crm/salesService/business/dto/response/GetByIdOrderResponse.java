package com.turkcell.crm.salesService.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetByIdOrderResponse {

    private double totalPrice;
    private int addressId;
    private int accountId;
    private List<GetByIdOrderItemResponse> orderItems;
    private int customerId;
}
