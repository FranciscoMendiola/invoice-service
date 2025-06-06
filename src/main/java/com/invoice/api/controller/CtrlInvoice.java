package com.invoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.api.dto.DtoInvoiceList;

import com.invoice.api.entity.Invoice;
import com.invoice.api.service.SvcInvoice;
import com.invoice.common.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice", description = "Administración de facturas")
public class CtrlInvoice {

	@Autowired
	SvcInvoice svc;

	@GetMapping
	@Operation(summary = "Consulta de facturas", description = "Administrador consulta todas las facturas. Cliente consulta sus facturas.")
	public ResponseEntity<List<DtoInvoiceList>> getInvoices() {	
		return svc.getInvoices();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Consulta de factura", description = "Consulta el detalle de una factura")
	public ResponseEntity<Invoice> getInvoice(@PathVariable("id") String id) {		
		return svc.getInvoice(id);
	}

	@PostMapping
	@Operation(summary = "Creación de factura", description = "Cliente crea una factura")
	public ResponseEntity<ApiResponse> createInvoice(HttpServletRequest request, @Valid @RequestBody Invoice invoice) {
		return svc.createInvoice(request, invoice);
	}
}
