package com.turkcell.crm.invoiceService.business.concretes;

import com.turkcell.crm.invoiceService.business.dtos.GetAllInvoiceResponse;
import com.turkcell.crm.invoiceService.core.utilities.mapping.ModelMapperService;
import com.turkcell.crm.invoiceService.dataAccess.InvoiceRepository;
import com.turkcell.crm.invoiceService.entities.Invoice;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceManagerTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InvoiceManager invoiceManager;

    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testAdd() {

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        Invoice mappedInvoice = new Invoice();


        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(orderCreatedEvent, Invoice.class)).thenReturn(mappedInvoice);

        invoiceManager.add(orderCreatedEvent);

        verify(invoiceRepository, times(1)).save(mappedInvoice);
        verify(modelMapperService.forRequest()).map(any(), eq(Invoice.class));

    }

    @Test
    public void testGetAll() {

        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoiceList = Arrays.asList(invoice1, invoice2);


        when(invoiceRepository.findAll()).thenReturn(invoiceList);


        GetAllInvoiceResponse response1 = new GetAllInvoiceResponse( );
        GetAllInvoiceResponse response2 = new GetAllInvoiceResponse();

        when((modelMapperService.forResponse())).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(invoice1, GetAllInvoiceResponse.class)).thenReturn(response1);
        when(modelMapperService.forResponse().map(invoice2, GetAllInvoiceResponse.class)).thenReturn(response2);

        List<GetAllInvoiceResponse> allInvoices = invoiceManager.getAll();

        verify(invoiceRepository, times(1)).findAll();
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllInvoiceResponse.class));

        assertEquals(2, allInvoices.size());
        assertEquals(response1, allInvoices.get(0));
        assertEquals(response2, allInvoices.get(1));
    }
}