package com.example.demo.errorcheck;

import java.io.UnsupportedEncodingException;

import com.example.demo.dto.ErrMessage;
import com.example.demo.dto.ManagementRequest;

public class ErrorCheck {

	/**
	 * 登録画面・編集画面で表示するエラーメッセージ
	 */
	public static ErrMessage getErr(ManagementRequest managementRequest,Long check) {

		ErrMessage errmessage = new ErrMessage();

		errmessage.setErr_flg(0);	// エラーフラグ

		if (managementRequest.getOrderdate().equals("")) {

			errmessage.setOrderdate("受注日を入力してください");
			errmessage.setErr_flg(1);

			if (managementRequest.getCustomer_id() == 1 && managementRequest.getStatus_id() == 1) {
				errmessage.setOrderdate("");
				errmessage.setErr_flg(0);
			}
		}

		if(check >= 1 && !(managementRequest.getS_number().equals(""))) {
			errmessage.setS_number("S番号が重複しています");
			errmessage.setErr_flg(1);
		}

		return errmessage;

	}

	public static String addresscheck(String address) throws UnsupportedEncodingException {

		String addressmessage = "";
		if (address.getBytes("Shift-JIS").length > 255) {
			addressmessage = "255桁以上です";
		}
		return addressmessage;

	}

public static String passwordcheck(String password) throws UnsupportedEncodingException {

		String passwordmessage = "";
		if (password.getBytes("Shift-JIS").length > 17) {
			passwordmessage = "17桁以上です";
		}
		return passwordmessage;

	}

}
