package com.example.demo.dto;
import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
/**
* 情報 リクエストデータ
*/
@Data
public class ManagementRequest implements Serializable {
/**
* 顧客
*/
@NotEmpty(message = "顧客は必須項目です")
private int customer_id;
/**
 * 受注日
 */
@Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "受注日はYYYY/MM/DDの形式で入力してください")
private String orderdate;

/**
 * 番号
 */
@Pattern(regexp = "^S-\\d{4}$", message = "S-NNNNの形式で入力してください")
private String s_number;

/**
 * 件名
 */
@NotEmpty(message = "件名は必須項目です")
private String subject;

/**
 * 数量
 */
@Size(max = 5, message = "数量は5桁以内で入力してください")
private String quantity;

private String unit;

/**
 * 納品指定日
 */
@Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "納品指定日はYYYY/MM/DDの形式で入力してください")
private String delivery_designation;

/**
 * 納品日
 */
@Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "納品日はYYYY/MM/DDの形式で入力してください")
private String delivery_date;

/**
 * 請求日
 */
@Pattern(regexp = "^$|^\\d{4}-\\d{2}-\\d{2}$", message = "請求日はYYYY/MM/DDの形式で入力してください")
private String billing_date;


/**
 * 見積金額
 */
@Size(max = 12, message = "見積金額は12桁以内で入力してください")
private String estimated_money;

/**
 * 受注金額
 */
@Size(max = 12, message = "受注金額は12桁以内で入力してください")
private String Order_money;

/**
 * ステータス
 */
private int status_id;

/**
 * 備考
 */
private String note;

/**
 * 削除プラグ
 */
private int delete_flg;

public int getCustomer_id() {
	return customer_id;
}

public void setCustomer_id(int customer_id) {
	this.customer_id = customer_id;
}
public int getStatus_id() {
	return status_id;
}

public void setStatus_id(int status_id) {
	this.status_id = status_id;
}





}

