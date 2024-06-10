package com.turkcell.crm.invoiceService.dataAccess;

import com.turkcell.crm.invoiceService.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
