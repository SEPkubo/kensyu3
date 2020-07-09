package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ユーザー情報 Controller
 */
@Controller

public class UserController {

	/**
	 * ユーザー情報 Service
	 */


	String message = ""; // エラーメッセージ

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String displayList() {

		return "list";
	}



}