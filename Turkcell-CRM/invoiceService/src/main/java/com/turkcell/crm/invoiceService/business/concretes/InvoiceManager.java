package com.turkcell.crm.invoiceService.business.concretes;

import com.turkcell.crm.invoiceService.business.abstracts.InvoiceService;
import com.turkcell.crm.invoiceService.business.dtos.GetAllInvoiceResponse;
import com.turkcell.crm.invoiceService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.invoiceService.dataAccess.InvoiceRepository;
import com.turkcell.crm.invoiceService.entities.Invoice;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private ModelMapperService modelMapperService;

    @Override
    public void add(OrderCreatedEvent orderCreatedEvent) {

       Invoice invoice = this.modelMapperService.forRequest().map(orderCreatedEvent, Invoice.class);
       this.invoiceRepository.save(invoice);
    }

    @Override
    public List<GetAllInvoiceResponse> getAll() {

        List<Invoice> invoices = this.invoiceRepository.findAll();

        List<GetAllInvoiceResponse> getAllInvoiceResponses=invoices.stream().map(invoice -> this.modelMapperService.forResponse().
                map(invoice, GetAllInvoiceResponse.class)).toList();

        return getAllInvoiceResponses;

    }
}
