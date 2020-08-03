package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Status_PrimaryKey implements Serializable {

	/**
	* 顧客ID
	*/
	@Column(name = "customer_id")
	private int customer_id;

	/**
	 * ステータスID
	 */
	@Column(name = "status_id")
	private int status_id;

}
