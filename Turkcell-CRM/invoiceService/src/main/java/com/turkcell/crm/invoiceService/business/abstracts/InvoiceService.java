package com.turkcell.crm.invoiceService.business.abstracts;

import com.turkcell.crm.invoiceService.business.dtos.GetAllInvoiceResponse;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;

import java.util.List;

public interface InvoiceService {
    void add(OrderCreatedEvent orderCreatedEvent);
    List<GetAllInvoiceResponse> getAll();
}
