package com.example.demo.dto;

import java.io.Serializable;

public class SearchRequest implements Serializable {

	// 顧客id
	private int customer_id;

	// ステータスid
	private int status_id;

	// 件名
	private String serch_subject;




	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getSerch_subject() {
		return serch_subject;
	}

	public void setSerch_subject(String serch_subject) {
		this.serch_subject = serch_subject;
	}

}
