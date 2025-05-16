package com.invoice.api.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoInvoiceList;
import com.invoice.api.entity.Invoice;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.common.mapper.MapperInvoiceList;
import com.invoice.common.util.JwtDecoder;
import com.invoice.exception.ApiException;
import com.invoice.exception.DBAccessException;

@Service
public class SvcInvoiceImp implements SvcInvoice {
	
	@Autowired
    private RepoInvoice repo;
	
	@Autowired
	private JwtDecoder jwtDecoder;
	
	@Autowired
	MapperInvoiceList mapper;

	@Override
	public ResponseEntity<List<DtoInvoiceList>> getInvoices() {
		try {
			if(jwtDecoder.isAdmin()) {
				return new ResponseEntity<>(mapper.toDtoList(repo.findAll()), HttpStatus.OK);
			}else {
				Integer user_id = jwtDecoder.getUserId();
				return new ResponseEntity<>(mapper.toDtoList(repo.findByUser_Id(user_id)), HttpStatus.OK);
			}
		}catch (DataAccessException e) {
	        throw new DBAccessException();
	    }
	}

	@Override
	public ResponseEntity<Invoice> getInvoice(String id) {
		try {
			Invoice invoice = repo.findById(id).get();
			if(!jwtDecoder.isAdmin()) {
				Integer user_id = jwtDecoder.getUserId();
				if(invoice.getUser_id() != user_id) {
					throw new ApiException(HttpStatus.FORBIDDEN, "El token no es válido para consultar esta factura");
				}
			}
			return new ResponseEntity<>(invoice, HttpStatus.OK);
		}catch (DataAccessException e) {
	        throw new DBAccessException();
	    }catch (NoSuchElementException e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de la factura no existe");
	    }
	}

	@Override
	public ResponseEntity<ApiResponse> createInvoice(Invoice invoice) {
		try {

			/*
			* Proyecto. Los datos del invoice no están completos, falta definir:
			* user_id
			* issuer_rfc
			* issuer_name
			* issuer_pc
			* issue_date
			*/
			
			repo.save(invoice);
			return new ResponseEntity<>(new ApiResponse("La factura ha sido registrada"), HttpStatus.CREATED);
		}catch (DataAccessException e) {
	        throw new DBAccessException();
	    }
	}
}
