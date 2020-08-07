package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.dto.ErrMessage;
import com.example.demo.dto.ManagementRequest;
import com.example.demo.dto.ManagementUpdateRequest;
import com.example.demo.dto.NameList;
import com.example.demo.dto.SearchRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ManagementList;
import com.example.demo.entity.ManagementUpdate;
import com.example.demo.entity.Status;
import com.example.demo.errorcheck.ErrorCheck;
import com.example.demo.pagewrapper.PageWrapper;
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

	int create_flg = 0; // 登録確認画面表示フラグ

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
		create_flg = 0;
		SearchRequest searchRequest = new SearchRequest();	// 検索リスト
		Page<ManagementList> customerlist = userService.getList(pageable);	// pageableを使った一覧の取得
		PageWrapper<ManagementList> page = new PageWrapper<ManagementList>(customerlist, "/list");

		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報


		model.addAttribute("page", page);
		model.addAttribute("customerlist", customerlist.getContent());
		model.addAttribute("searchRequest", searchRequest);	// 検索ワードリスト(無いとエラーになるため)
		model.addAttribute("customerpulldown", customerpulldown);
		model.addAttribute("statuspulldown", statuspulldown);


		return "list";
	}

	/**
	 * 検索の一覧画面を表示
	 * @param model Model
	 * @return 検索の一覧画面
	 */
	@RequestMapping(value = "/listsearch", method = RequestMethod.GET)
	public String displayListsearch(@PageableDefault(page = 0, size = 10) Pageable pageable,
			@RequestParam(name = "Serch_subject") String serch_subject,
			@RequestParam(name = "customer_id") String customer_id,
			@RequestParam(name = "status_id",defaultValue = "") String status_id,
			Model model) {
		System.out.println(serch_subject);
//		System.out.println(customer_id);
//		System.out.println(status_id);
		Page<ManagementUpdate> customerlist = userService.getListSerch(pageable, customer_id, status_id, serch_subject);	// 検索

		PageWrapper<ManagementUpdate> page = new PageWrapper<ManagementUpdate>(customerlist, "/listsearch/?customer_name="
				+ customer_id + "&status=" + status_id + "&Serch_subject=" + serch_subject);

		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報

		SearchRequest searchRequest = new SearchRequest(); // 検索ワードを保持
//		searchRequest.setCustomer_id(customer_id);
//		searchRequest.setStatus_id(status_id);
		searchRequest.setSerch_subject(serch_subject);
		model.addAttribute("customerlist", customerlist);
		model.addAttribute("searchRequest", searchRequest);
		model.addAttribute("page", page);
		model.addAttribute("customerpulldown", customerpulldown);
		model.addAttribute("statuspulldown", statuspulldown);
		return "list";
	}

	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 登録画面
	 */

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String displayAdd(@ModelAttribute("managementRequest") ManagementRequest managementRequest, Model model) {

		ErrMessage errmessage = new ErrMessage();

		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報

		// 確認画面から戻ってきた場合に顧客名が選択されていなければ顧客名にnullを入れる
		if (managementRequest.getCustomer_id() != 0) {

		}

		if (create_flg == 1) { // 確認画面から戻ってきた場合
			// 日付表示を戻す処理
			managementRequest.setOrderdate(managementRequest.getOrderdate().replace("/", "-")); // スラッシュからハイフンに置き換え
			managementRequest.setDelivery_designation(managementRequest.getDelivery_designation().replace("/", "-"));
			managementRequest.setDelivery_date(managementRequest.getDelivery_date().replace("/", "-"));
			managementRequest.setBilling_date(managementRequest.getBilling_date().replace("/", "-"));
			managementRequest.setOrder_money(managementRequest.getOrder_money().replace("\\", "")); // 円マーク除去
			managementRequest.setEstimated_money(managementRequest.getEstimated_money().replace("\\", ""));
		}

		create_flg = 0; // 確認画面フラグ
		model.addAttribute("errmessage", errmessage);
		model.addAttribute("customerpulldown", customerpulldown);
		model.addAttribute("statuspulldown", statuspulldown);
		return "add";
	}

	/**
	 * 登録確認画面を表示
	 * @param model Model
	 * @return 登録画面
	 */

	@PostMapping(value = "/addcheck")
	public String displayAddcheck(@Validated @ModelAttribute("managementRequest") ManagementRequest managementRequest,
			BindingResult result, Model model) {
		create_flg = 1; // 確認画面フラグ
		long check = userService.s_numberCheck(managementRequest.getS_number()); // S番号重複チェック

		NameList namelist = new NameList();
		namelist.setCustomer_name(userService.findCustomer_name(managementRequest.getCustomer_id()));
		namelist.setStatus_name(userService.findStatus_name(managementRequest.getCustomer_id(),managementRequest.getStatus_id()));
		ErrMessage errmessage = ErrorCheck.getErr(managementRequest, check); // エラーメッセージ受け取り
		if (result.hasErrors() || errmessage.getErr_flg() == 1) {
			model.addAttribute("errmessage", errmessage);
			return "add";
		}

		managementRequest.setOrder_money("\\" + managementRequest.getOrder_money()); // 円マーク追加
		managementRequest.setEstimated_money("\\" + managementRequest.getEstimated_money());
		managementRequest.setOrderdate(managementRequest.getOrderdate().replace("-", "/")); // ハイフンからスラッシュに置き換え
		managementRequest.setDelivery_designation(managementRequest.getDelivery_designation().replace("-", "/"));
		managementRequest.setDelivery_date(managementRequest.getDelivery_date().replace("-", "/"));
		managementRequest.setBilling_date(managementRequest.getBilling_date().replace("-", "/"));
		model.addAttribute("namelist", namelist);
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
	public String displayView(@PathVariable Long id, Model model) {
		ManagementUpdate management = userService.findById(id);
		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報
		ErrMessage errmessage = new ErrMessage();

		management.setOrderdate(management.getOrderdate().replace("/", "-")); // スラッシュからハイフンに置き換え
		management.setDelivery_designation(management.getDelivery_designation().replace("/", "-"));
		management.setDelivery_date(management.getDelivery_date().replace("/", "-"));
		management.setBilling_date(management.getBilling_date().replace("/", "-"));
		model.addAttribute("managementUpdateRequest", management);
		model.addAttribute("errmessage", errmessage);
		model.addAttribute("customerpulldown", customerpulldown);
		model.addAttribute("statuspulldown", statuspulldown);
		return "edit";
	}

	// 編集確認画面
	@PostMapping("/editcheck")
	public String editcheck(@ModelAttribute("managementUpdateRequest") ManagementUpdateRequest managementUpdateRequest,
			BindingResult result, Model model) {

		NameList namelist = new NameList();
		namelist.setCustomer_name(userService.findCustomer_name(managementUpdateRequest.getCustomer_id()));
		namelist.setStatus_name(userService.findStatus_name(managementUpdateRequest.getCustomer_id(),managementUpdateRequest.getStatus_id()));

		long check = userService.s_numberCheck(managementUpdateRequest.getS_number()); // S番号重複チェック

		check = 0;	// 調整

		ErrMessage errmessage = ErrorCheck.getErr(managementUpdateRequest, check);
		if (result.hasErrors() || errmessage.getErr_flg() == 1) {
			model.addAttribute("errmessage", errmessage);
			return "edit";
		}
		managementUpdateRequest.setOrderdate(managementUpdateRequest.getOrderdate().replace("-", "/")); // ハイフンからスラッシュに置き換え
		managementUpdateRequest
				.setDelivery_designation(managementUpdateRequest.getDelivery_designation().replace("-", "/"));
		managementUpdateRequest.setDelivery_date(managementUpdateRequest.getDelivery_date().replace("-", "/"));
		managementUpdateRequest.setBilling_date(managementUpdateRequest.getBilling_date().replace("-", "/"));
		model.addAttribute("namelist", namelist);
		return "editcheck";
	}

	/**
	 * 確認画面から戻ってきた場合の編集画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 編集画面
	 */
	@GetMapping("/edit")
	public String displayedit(
			@ModelAttribute("managementUpdateRequest") ManagementUpdateRequest managementUpdateRequest, Model model) {
		ErrMessage errmessage = new ErrMessage();
		managementUpdateRequest.setOrderdate(managementUpdateRequest.getOrderdate().replace("/", "-")); // スラッシュからハイフンに置き換え
		managementUpdateRequest
				.setDelivery_designation(managementUpdateRequest.getDelivery_designation().replace("/", "-"));
		managementUpdateRequest.setDelivery_date(managementUpdateRequest.getDelivery_date().replace("/", "-"));
		managementUpdateRequest.setBilling_date(managementUpdateRequest.getBilling_date().replace("/", "-"));
		model.addAttribute("managementUpdateRequest", managementUpdateRequest);
		model.addAttribute("errmessage", errmessage);
		return "edit";
	}

	// 更新処理
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute ManagementUpdateRequest managementUpdateRequest,
			BindingResult result,
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
	public String delete(@PathVariable Long id, Model model) {
		ManagementUpdate management = userService.findById(id);
		NameList namelist = new NameList();
		namelist.setCustomer_name(userService.findCustomer_name(management.getCustomer_id()));
		model.addAttribute("managementUpdateRequest", management);
		model.addAttribute("namelist", namelist);
		return "delete";
	}

	// 削除処理
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@Validated @ModelAttribute ManagementUpdateRequest managementUpdateRequest,
			BindingResult result,
			Model model) {

		// 情報の更新
		userService.delete(managementUpdateRequest);
		return "redirect:/list";
	}

}