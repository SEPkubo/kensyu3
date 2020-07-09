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
 * ユーザー情報 Entity
 */
@Entity
@Data
@Table(name="management")

public class Management implements Serializable {


   /**
    * ID
    */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;


	/**
	 * 顧客id
	 */
	@Column(name="customer_id")
	private int customer_id;

	/**
	 * 受注日
	 */
	@Column(name="orderdate")
	private String orderdate;

	/**
	 * 番号
	 */
	@Column(name="s_number")
	private String s_number;

	/**
	 * 件名
	 */
	@Column(name="subject")
	private int subject;

	/**
	 * 数量
	 */
	@Column(name="quantity")
	private int quantity;

	/**
	 * 納品指定日
	 */
	@Column(name="delivery_designation")
	private int delivery_designation;

	/**
	 * 納品日
	 */
	@Column(name="delivery_date")
	private int delivery_date;

	/**
	 * 請求日
	 */
	@Column(name="billing_date")
	private int billing_date;


	/**
	 * 見積金額
	 */
	@Column(name="estimated_money")
	private int estimated_money;

	/**
	 * 請求金額
	 */
	@Column(name="Order_money")
	private int Order_money;

	/**
	 * ステータスid
	 */
	@Column(name="status_id")
	private int status_id;



}