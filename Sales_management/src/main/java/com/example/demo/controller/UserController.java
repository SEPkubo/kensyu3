package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.ManagementRequest;
import com.example.demo.entity.Management;
import com.example.demo.service.UserService;


/**
 * ユーザー情報 Controller
 */
@Controller

public class UserController {

	/**
	 * ユーザー情報 Service
	 */
	@Autowired
	UserService userService;


	String message = ""; // エラーメッセージ

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
		Page<Management> customerlist = userService.getList(pageable);


		model.addAttribute("customerlist", customerlist.getContent());


		return "list";
	}


	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String displayAdd(Model model) {

		model.addAttribute("managementRequest", new ManagementRequest());
		return "add";
	}



}