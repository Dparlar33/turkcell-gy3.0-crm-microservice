package com.turkcell.crm.invoiceService.business.dtos;

import com.turkcell.crm.invoiceService.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllInvoiceResponse {
    private int id;
    private int accountId;
    private int customerId;
    private double price;
    private int addressId;
    private List<OrderItem> orderItems;
}
