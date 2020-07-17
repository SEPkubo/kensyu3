package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ManagementRequest;
import com.example.demo.dto.ManagementUpdateRequest;
import com.example.demo.dto.SearchRequest;
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
		SearchRequest searchRequest = new SearchRequest();
		model.addAttribute("customerlist", customerlist.getContent());
		model.addAttribute("searchRequest", searchRequest);

		return "list";
	}


	/**
	 * 住所検索の一覧画面を表示
	 * @param model Model
	 * @return 住所検索の一覧画面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/listsearch", method = RequestMethod.GET)
	public String displayListsearch(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@RequestParam(name = "Serch_subject") String serch_subject,@RequestParam(name = "customer_name") String customer_name,@RequestParam(name = "status", defaultValue = "") String status,
			Model model) throws UnsupportedEncodingException {
		Page<Management> customerlist = userService.getListSerch(pageable,customer_name,status,serch_subject);

		SearchRequest searchRequest = new SearchRequest();	// 検索ワードを保持
		searchRequest.setCustomer_name(customer_name);
		searchRequest.setStatus(status);
		searchRequest.setSerch_subject(serch_subject);
		model.addAttribute("customerlist", customerlist.getContent());
		model.addAttribute("searchRequest", searchRequest);
		return "list";
	}



	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 登録画面
	 */

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String displayAdd(@ModelAttribute("managementRequest") ManagementRequest managementRequest,Model model) {

		// 確認画面から戻ってきた場合に顧客名が選択されていなければ顧客名にnullを入れる
		if(managementRequest.getCustomer_name() != null && managementRequest.getCustomer_name().equals("")) {
			managementRequest.setCustomer_name(null);
			// 日付表示を戻す処理
			managementRequest.setOrderdate(managementRequest.getOrderdate().replace("/", "-")); 	// スラッシュからハイフンに置き換え
			managementRequest.setDelivery_designation(managementRequest.getDelivery_designation().replace("/", "-"));
			managementRequest.setDelivery_date(managementRequest.getDelivery_date().replace("/", "-"));
			managementRequest.setBilling_date(managementRequest.getBilling_date().replace("/", "-"));

		}



		return "add";
	}

	/**
	 * 登録確認画面を表示
	 * @param model Model
	 * @return 登録画面
	 */

	@PostMapping(value = "/addcheck")
	public String displayAddcheck(@ModelAttribute("managementRequest") ManagementRequest managementRequest,Model model) {
		managementRequest.setOrderdate(managementRequest.getOrderdate().replace("-", "/")); 	// ハイフンからスラッシュに置き換え
		managementRequest.setDelivery_designation(managementRequest.getDelivery_designation().replace("-", "/"));
		managementRequest.setDelivery_date(managementRequest.getDelivery_date().replace("-", "/"));
		managementRequest.setBilling_date(managementRequest.getBilling_date().replace("-", "/"));
		return "addcheck";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute ManagementRequest managementRequest, Model model) {
		// 登録処理
		managementRequest.setDelete_flg(0);
		userService.create(managementRequest);
		return "redirect:list";
	}

	/**
	 * 編集画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 編集画面
	 */
	@GetMapping("/edit/{id}")
	public String displayView(@PathVariable Long id, Model model){
		Management management = userService.findById(id);

		management.setOrderdate(management.getOrderdate().replace("/", "-")); 	// スラッシュからハイフンに置き換え
		management.setDelivery_designation(management.getDelivery_designation().replace("/", "-"));
		management.setDelivery_date(management.getDelivery_date().replace("/", "-"));
		management.setBilling_date(management.getBilling_date().replace("/", "-"));
		model.addAttribute("managementUpdateRequest", management);
		return "edit";
	}

	// 編集確認画面
		@PostMapping("/editcheck")
		public String editcheck(@ModelAttribute("managementUpdateRequest") ManagementUpdateRequest managementUpdateRequest, Model model) {

			managementUpdateRequest.setOrderdate(managementUpdateRequest.getOrderdate().replace("-", "/")); 	// ハイフンからスラッシュに置き換え
			managementUpdateRequest.setDelivery_designation(managementUpdateRequest.getDelivery_designation().replace("-", "/"));
			managementUpdateRequest.setDelivery_date(managementUpdateRequest.getDelivery_date().replace("-", "/"));
			managementUpdateRequest.setBilling_date(managementUpdateRequest.getBilling_date().replace("-", "/"));
			return "editcheck";
		}

		/**
		 * 確認画面から戻ってきた場合の編集画面を表示
		 * @param id 表示されるデータのID
		 * @param model Model
		 * @return 編集画面
		 */
		@GetMapping("/edit")
		public String displayedit(@ModelAttribute("managementUpdateRequest") ManagementUpdateRequest managementUpdateRequest, Model model){

			managementUpdateRequest.setOrderdate(managementUpdateRequest.getOrderdate().replace("/", "-")); 	// スラッシュからハイフンに置き換え
			managementUpdateRequest.setDelivery_designation(managementUpdateRequest.getDelivery_designation().replace("/", "-"));
			managementUpdateRequest.setDelivery_date(managementUpdateRequest.getDelivery_date().replace("/", "-"));
			managementUpdateRequest.setBilling_date(managementUpdateRequest.getBilling_date().replace("/", "-"));
			model.addAttribute("managementUpdateRequest", managementUpdateRequest);
			return "edit";
		}

		// 更新処理
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String update(@Validated @ModelAttribute ManagementUpdateRequest managementUpdateRequest, BindingResult result,
				Model model) {
			// 情報の更新
			userService.update(managementUpdateRequest);
			return "redirect:/list";
		}

		/**
		 * 削除画面を表示
		 * @param id 表示されるデータのID
		 * @param model Model
		 * @return 削除画面
		 */
		@GetMapping("/delete/{id}")
		public String delete(@PathVariable Long id, Model model){
			Management management = userService.findById(id);
			management.setOrderdate(management.getOrderdate().replace("/", "-")); 	// スラッシュからハイフンに置き換え
			model.addAttribute("managementUpdateRequest", management);

			return "delete";
		}

		// 削除処理
		@RequestMapping(value = "/delete", method = RequestMethod.POST)
		public String delete(@Validated @ModelAttribute ManagementUpdateRequest managementUpdateRequest, BindingResult result,
				Model model) {

			// 情報の更新
			userService.delete(managementUpdateRequest);
			return "redirect:/list";
		}


}