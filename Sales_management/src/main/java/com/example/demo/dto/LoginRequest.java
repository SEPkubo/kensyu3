package com.example.demo.dto;

import java.io.Serializable;

public class LoginRequest implements Serializable {

	/**
	 * メールアドレス
	 */

	private String mailaddress;


	/**
	 * パスワード
	 */

	private String password;


	public String getMailaddress() {
		return mailaddress;
	}


	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
