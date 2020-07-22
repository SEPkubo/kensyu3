package com.example.demo.dto;

import java.io.Serializable;

public class ErrMessage implements Serializable {


	/**
	 * 受注日のエラーメッセージ
	 */
	private String orderdate;

	/**
	 * S番号のエラーメッセージ
	 */
	private String s_number;

	/**
	 * エラーフラグ
	 */
	private int err_flg;

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getS_number() {
		return s_number;
	}

	public void setS_number(String s_number) {
		this.s_number = s_number;
	}

	public int getErr_flg() {
		return err_flg;
	}

	public void setErr_flg(int err_flg) {
		this.err_flg = err_flg;
	}




}
