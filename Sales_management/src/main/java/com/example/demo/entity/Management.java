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
 * 案件一覧情報 Entity
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
	 * 顧客
	 */
	@Column(name="customer_name")
	private String customer_name;

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
	private String subject;

	/**
	 * 数量
	 */
	@Column(name="quantity")
	private String quantity;

	/**
	 * 納品指定日
	 */
	@Column(name="delivery_designation")
	private String delivery_designation;

	/**
	 * 納品日
	 */
	@Column(name="delivery_date")
	private String delivery_date;

	/**
	 * 請求日
	 */
	@Column(name="billing_date")
	private String billing_date;


	/**
	 * 見積金額
	 */
	@Column(name="estimated_money")
	private String estimated_money;

	/**
	 * 受注金額
	 */
	@Column(name="Order_money")
	private String Order_money;

	/**
	 * ステータス
	 */
	@Column(name="status")
	private String status;

	/**
	 * 備考
	 */
	@Column(name="note")
	private String note;

	/**
	 * 備考
	 */
	@Column(name="delete_flg")
	private int delete_flg;



}