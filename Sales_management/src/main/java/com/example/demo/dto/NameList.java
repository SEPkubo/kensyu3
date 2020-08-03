package com.example.demo.dto;

import java.io.Serializable;


public class NameList implements Serializable {

	// 顧客名
    private String customer_name;

    // ステータス名
    private String status_name;

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}
