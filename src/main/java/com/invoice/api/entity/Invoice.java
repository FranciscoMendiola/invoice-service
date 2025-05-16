package com.invoice.api.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "invoice")
public class Invoice {
	
	@Id
	private String id;

	@NotNull(message="El user_id es obligatorio")
	private Integer user_id;

//	@NotNull(message="El issuer_rfc es obligatorio")
	private String issuer_rfc;

//	@NotNull(message="El issuer_name es obligatorio")
	private String issuer_name;

//	@NotNull(message="El issuer_pc es obligatorio")
	private String issuer_pc;

	@NotNull(message="El customer_rfc es obligatorio")
	private String customer_rfc;

	@NotNull(message="El customer_name es obligatorio")
	private String customer_name;

//	@NotNull(message="El issue_date es obligatorio")
	private String issue_date;

	@NotNull(message="El currency es obligatorio")
	private String currency;

	@NotNull(message="El payment_method es obligatorio")
	private String payment_method;

	@NotNull(message="El payment_form es obligatorio")
	private String payment_form;

	@NotNull(message="El subtotal es obligatorio")
	private Double subtotal;

	@NotNull(message="El taxes es obligatorio")
	private Double taxes;

	@NotNull(message="El total es obligatorio")
	private Double total;

	@NotNull(message="Los items son obligatorios")
	private List<InvoiceItem> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getIssuer_rfc() {
		return issuer_rfc;
	}

	public void setIssuer_rfc(String issuer_rfc) {
		this.issuer_rfc = issuer_rfc;
	}

	public String getIssuer_name() {
		return issuer_name;
	}

	public void setIssuer_name(String issuer_name) {
		this.issuer_name = issuer_name;
	}

	public String getIssuer_pc() {
		return issuer_pc;
	}

	public void setIssuer_pc(String issuer_pc) {
		this.issuer_pc = issuer_pc;
	}

	public String getCustomer_rfc() {
		return customer_rfc;
	}

	public void setCustomer_rfc(String customer_rfc) {
		this.customer_rfc = customer_rfc;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getPayment_form() {
		return payment_form;
	}

	public void setPayment_form(String payment_form) {
		this.payment_form = payment_form;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

}
