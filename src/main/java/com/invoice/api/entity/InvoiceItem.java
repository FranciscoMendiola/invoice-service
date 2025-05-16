package com.invoice.api.entity;

import jakarta.validation.constraints.NotNull;

public class InvoiceItem {
	
	@NotNull(message="El gtin es obligatorio")
	private String gtin;

	@NotNull(message="El product es obligatorio")
	private String product;

	@NotNull(message="El quantity es obligatorio")
	private Integer quantity;

	@NotNull(message="El unit_price es obligatorio")
	private Double unit_price;

	@NotNull(message="El subtotal es obligatorio")
	private Double subtotal;

	@NotNull(message="El taxes es obligatorio")
	private Double taxes;

	@NotNull(message="El total es obligatorio")
	private Double total;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
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
	

}
