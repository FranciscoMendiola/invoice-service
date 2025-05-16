package com.invoice.api.dto;

public class DtoInvoiceList {
	
	private String id;
	
	private Integer user_id;
	
	private String customer_rfc;
	
	private String customer_name;
	
	private String issue_date;
	
	private Double total_price;
	
	private Integer total_items;

	public DtoInvoiceList(String id, Integer user_id, String customer_rfc, String customer_name, String issue_date,
			Double total_price, Integer total_items) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.customer_rfc = customer_rfc;
		this.customer_name = customer_name;
		this.issue_date = issue_date;
		this.total_price = total_price;
		this.total_items = total_items;
	}

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

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Integer getTotal_items() {
		return total_items;
	}

	public void setTotal_items(Integer total_items) {
		this.total_items = total_items;
	}
}
