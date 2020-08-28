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
 * ログイン情報 Entity
 */
@Entity
@Data
@Table(name="login")
public class Login implements Serializable {

	/**
	    * ID
	    */
	    @Id
	    @Column(name="user_id")
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int user_id;


		/**
		 * メールアドレス
		 */
		@Column(name="mailaddress")
		private String mailaddress;

		/**
		 * パスワード
		 */
		@Column(name="password")
		private String password;

}
