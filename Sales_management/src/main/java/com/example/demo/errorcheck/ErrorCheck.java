package com.example.demo.errorcheck;

import com.example.demo.dto.ErrMessage;
import com.example.demo.dto.ManagementRequest;

public class ErrorCheck {

	public static ErrMessage getErr(ManagementRequest managementRequest,Long check) {

		ErrMessage errmessage = new ErrMessage();

		errmessage.setErr_flg(0);	// エラーフラグ

		if (managementRequest.getOrderdate().equals("") && managementRequest.getStatus() == null) {

			errmessage.setOrderdate("受注日を入力してください");
			errmessage.setErr_flg(1);
		}

		if(check >= 1) {
			errmessage.setS_number("S番号が重複しています");
			errmessage.setErr_flg(1);
		}

		return errmessage;

	}

}
