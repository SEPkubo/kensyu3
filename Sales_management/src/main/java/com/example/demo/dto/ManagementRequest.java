package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
/**
* 情報 リクエストデータ
*/
@Data
public class ManagementRequest implements Serializable {
/**
* 顧客id
*/
private String customer_id;
/**
 * 受注日
 */
private String orderdate;

/**
 * 番号
 */
private String s_number;

/**
 * 件名
 */
private String subject;

/**
 * 数量
 */
private int quantity;

/**
 * 納品指定日
 */
private int delivery_designation;

/**
 * 納品日
 */
private int delivery_date;

/**
 * 請求日
 */
private int billing_date;


/**
 * 見積金額
 */
private int estimated_money;

/**
 * 請求金額
 */
private int Order_money;

/**
 * ステータスid
 */
private String status;

}