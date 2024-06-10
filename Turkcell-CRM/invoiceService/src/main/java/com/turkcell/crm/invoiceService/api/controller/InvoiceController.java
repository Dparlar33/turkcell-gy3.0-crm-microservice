package com.turkcell.crm.invoiceService.api.controller;

import com.turkcell.crm.invoiceService.business.abstracts.InvoiceService;
import com.turkcell.crm.invoiceService.business.dtos.GetAllInvoiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("invoiceservice/api/v1/invoices/invoice")
@AllArgsConstructor
public class InvoiceController {
    private InvoiceService invoiceService;

    @GetMapping
    public List<GetAllInvoiceResponse> getAll(){
        return this.invoiceService.getAll();
    }
}
