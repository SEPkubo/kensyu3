package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * ステータス情報 Entity
 */
@Entity
@Data
@Table(name = "status")

public class Status implements Serializable {

	/**
	* ID
	*/
	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customer_id;

	/**
	 * ステータスID
	 */
	@Column(name = "status_id")
	private int status_id;

	/**
	 * 顧客名
	 */
	@Column(name = "status_name")
	private String status_name;

}
