package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * ステータス情報 Entity
 */
@Entity
@Data
@IdClass(Status_PrimaryKey.class)
@Table(name = "status")

public class Status implements Serializable {

	/**
	* 顧客ID
	*/
	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customer_id;

	/**
	 * ステータスID
	 */
	@Id
	@Column(name = "status_id")
	private int status_id;

	/**
	 * ステータス名
	 */
	@Column(name = "status_name")
	private String status_name;

	/**
	 * 削除フラグ
	 */
	@Column(name = "delete_flg")
	private int delete_flg;

}
