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
 * 顧客一覧情報 Entity
 */
@Entity
@Data
@Table(name="customer")

public class Customer implements Serializable {


	/**
	    * ID
	    */
	    @Id
	    @Column(name="customer_id")
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long customer_id;


		/**
		 * 顧客名
		 */
		@Column(name="customer_name")
		private String customer_name;







}
