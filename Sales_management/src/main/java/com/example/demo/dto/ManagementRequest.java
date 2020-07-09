package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
/**
* 情報 リクエストデータ
*/
@Data
public class ManagementRequest implements Serializable {
/**
* 名前
*/
private String name;
/**
* 住所
*/
private String address;
/**
* 電話番号
*/
private String phone;

}