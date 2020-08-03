package com.example.demo.dto;

import java.io.Serializable;

public class SearchRequest implements Serializable {

	// 顧客
	private String customer_name;

	// ステータス
	private String status;

	// 件名
	private String serch_subject;


	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerch_subject() {
		return serch_subject;
	}

	public void setSerch_subject(String serch_subject) {
		this.serch_subject = serch_subject;
	}

}
