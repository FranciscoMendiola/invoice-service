package com.invoice.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;

@Service
public class MapperInvoiceList {
	
	public List<DtoInvoiceList> toDtoList(List<Invoice> invoices) {
		List<DtoInvoiceList> dtoInvoices = new ArrayList<>();
		
		for (Invoice invoice : invoices) {
			
			DtoInvoiceList dtoInvoice = new DtoInvoiceList(
		            invoice.getId(),
		            invoice.getUser_id(),
		            invoice.getCustomer_rfc(),
		            invoice.getCustomer_name(),
		            invoice.getIssue_date(),
		            invoice.getTotal(),
		            invoice.getItems().size()
		        );
			dtoInvoices.add(dtoInvoice);
		}
         
         return dtoInvoices;
    }
}
