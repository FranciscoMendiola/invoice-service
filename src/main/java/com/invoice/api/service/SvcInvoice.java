package com.invoice.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;

public interface SvcInvoice {

	public ResponseEntity<List<DtoInvoiceList>> getInvoices();
	public ResponseEntity<Invoice> getInvoice(String id);
	public ResponseEntity<ApiResponse> createInvoice(Invoice invoice);
}
