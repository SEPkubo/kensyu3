package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		// ハッシュ化エンコーダ


	/**
	 * ログイン画面を表示
	 * @param model Model
	 * @return ログイン画面
	 */

	@RequestMapping(value={"/login", "/list", "/listsearch","/add","/addcheck","/edit/{id}","/editcheck","/delete/{id}"}, method = RequestMethod.GET)
	public String displayLogin(Model model) {

		model.addAttribute("addresserr", "");	// エラーメッセージに空文字を設定
		model.addAttribute("passworderr", "");
		model.addAttribute("loginerr", "");

		return "login";
	}


	/**
	 * ログインチェック
	 * @param model Model
	 * @return ログイン成功:一覧画面	ログイン失敗:ログイン画面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	public String LoginCheck(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
		String mailaddress = request.getParameter("mailaddress");	// メールアドレス
		String password = request.getParameter("password");	// パスワード

		String addresserr = ErrorCheck.addresscheck(mailaddress);	// メールアドレスの文字数制限メッセージ
		String passworderr = ErrorCheck.passwordcheck(password);	// パスワードの文字数制限メッセージ
		String loginerr = "";	// ログインできなかった場合のメッセージ

		String checkpass = userService.getLoginCheck(mailaddress);		// 入力されたメールアドレスが使われているパスワードを取得

		ErrorCheck.addresscheck(mailaddress);
		if(addresserr != "" || passworderr != "") {
			model.addAttribute("addresserr", addresserr);
			model.addAttribute("passworderr", passworderr);
			model.addAttribute("loginerr", loginerr);
			return "login";
		}


		if (passwordEncoder.matches(password, checkpass)) {	// パスワードチェック
			return "forward:/list";
        }


		loginerr = "メールアドレスもしくはパスワードが間違っているか入力されていません。";
		model.addAttribute("loginerr", loginerr);
		return "login";
	}

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String displayList(@PageableDefault(page = 0, size = 10, direction = Direction.ASC,
                sort = {
                    "customerid",
                    "orderdate"
                    }
                ) Pageable pageable, Model model) {
		create_flg = 0;
		SearchRequest searchRequest = new SearchRequest();	// 検索リスト
		Page<ManagementList> customerlist = userService.getList(pageable);
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
	@RequestMapping(value = "/listsearch", method = RequestMethod.POST)
	public String displayListsearch(@PageableDefault(page = 0, size = 10, direction = Direction.ASC,
                sort = {
                    "customerid",
                    "orderdate"
                    }
                ) Pageable pageable,
			@RequestParam(name = "Serch_subject") String serch_subject,
			@RequestParam(name = "customer_id") Long customer_id,
			@RequestParam(name = "status_id",defaultValue = "-1") int status_id,
			Model model) {
		Page<ManagementList> customerlist = userService.getListSerch(pageable, customer_id, status_id, serch_subject);	// 検索
		PageWrapper<ManagementList> page = new PageWrapper<ManagementList>(customerlist, "/listsearch/?customer_name="
				+ customer_id + "&status=" + status_id + "&Serch_subject=" + serch_subject);

		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報

		SearchRequest searchRequest = new SearchRequest(); // 検索ワードを保持
		searchRequest.setSerch_subject(serch_subject);
		searchRequest.setCustomer_id(customer_id);
		searchRequest.setStatus_id(status_id);
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String displayAdd(@ModelAttribute("managementRequest") ManagementRequest managementRequest, Model model) {

		ErrMessage errmessage = new ErrMessage();

		List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト

		List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報


		if (create_flg == 1) { // 確認画面から戻ってきた場合
			// 日付表示を戻す処理
			managementRequest.setOrderdate(managementRequest.getOrderdate().replace("/", "-")); // スラッシュからハイフンに置き換え
			managementRequest.setDelivery_designation(managementRequest.getDelivery_designation().replace("/", "-"));
			managementRequest.setDelivery_date(managementRequest.getDelivery_date().replace("/", "-"));
			managementRequest.setBilling_date(managementRequest.getBilling_date().replace("/", "-"));
//			managementRequest.setOrder_money(managementRequest.getOrder_money().replace("\\", "")); // 円マーク除去
//			managementRequest.setEstimated_money(managementRequest.getEstimated_money().replace("\\", ""));
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
			List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト
			List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報
			model.addAttribute("errmessage", errmessage);
			model.addAttribute("customerpulldown", customerpulldown);
			model.addAttribute("statuspulldown", statuspulldown);
			return "add";
		}

//		managementRequest.setOrder_money("\\" + managementRequest.getOrder_money()); // 円マーク追加
//		managementRequest.setEstimated_money("\\" + managementRequest.getEstimated_money());
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
		return "forward:/list";
	}

	/**
	 * 編集画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 編集画面
	 */
	@PostMapping("/edit/{id}")
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
		long check = 0;
		namelist.setCustomer_name(userService.findCustomer_name(managementUpdateRequest.getCustomer_id()));
		namelist.setStatus_name(userService.findStatus_name(managementUpdateRequest.getCustomer_id(),managementUpdateRequest.getStatus_id()));

		ManagementUpdate management = userService.findById(managementUpdateRequest.getId());


		if(!(management.getS_number().equals(managementUpdateRequest.getS_number()))) {	// S番号が変更されていたらチェックを行う
			 check = userService.s_numberCheck(managementUpdateRequest.getS_number()); // S番号重複チェック
		}


		ErrMessage errmessage = ErrorCheck.getErr(managementUpdateRequest, check);
		if (result.hasErrors() || errmessage.getErr_flg() == 1) {	// エラー判定
			List<Customer> customerpulldown = userService.getCustomer_name();	// プルダウンの顧客リスト
			List<Status> statuspulldown = userService.getStatus_name();	// プルダウンのステータス情報
			model.addAttribute("errmessage", errmessage);
			model.addAttribute("customerpulldown", customerpulldown);
			model.addAttribute("statuspulldown", statuspulldown);
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
	@PostMapping("/edit")
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

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/customer_list", method = RequestMethod.POST)
	public String customer_list(Model model) {

		List<Customer> list = userService.getcoustomer_list();	// 顧客リスト

		model.addAttribute("list", list);
		return "customer_list";
	}


	/**
	 * 顧客登録画面を表示
	 * @param model Model
	 * @return 登録画面
	 */
	@RequestMapping(value = "/customer_add", method = RequestMethod.POST)
	public String CustomerAdd(Model model) {



		return "customer_add";
	}

	/**
	 * 顧客登録確認画面を表示
	 * @param model Model
	 * @return 登録画面
	 */
	@RequestMapping(value = "/customer_addcheck", method = RequestMethod.POST)
	public String CustomerAddCheck(HttpServletRequest request,Model model) {



		model.addAttribute("customer_name", request.getParameter("customer_name"));
		model.addAttribute("status1", request.getParameter("status1"));
		model.addAttribute("status2", request.getParameter("status2"));
		model.addAttribute("status3", request.getParameter("status3"));
		model.addAttribute("status4", request.getParameter("status4"));
		model.addAttribute("status5", request.getParameter("status5"));
		model.addAttribute("status6", request.getParameter("status6"));
		model.addAttribute("status7", request.getParameter("status7"));
		model.addAttribute("status8", request.getParameter("status8"));
		model.addAttribute("status9", request.getParameter("status9"));
		model.addAttribute("status10", request.getParameter("status10"));
		return "customer_addcheck";
	}



	/**
	 * 顧客編集画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 顧客編集画面
	 */
	@PostMapping("/customer_edit/{id}")
	public String customer_edit(@PathVariable Long id, Model model) {
		Customer customer = userService.findByCustomer_Id(id);
		List<Status> status = userService.getStatusList(id);

		for(int i = 0;i < 10;i++) {	// 10回ループさせる
			if(status.size() - 1 >= i) {		// ステータス情報がある場合、情報を代入する
				model.addAttribute("status" + (i+1), status.get(i).getStatus_name());
			} else {
				model.addAttribute("status" + (i+1), "");	// ステータス情報が無い場合
			}
		}

		model.addAttribute("customer", customer);

		return "customer_edit";
	}

	/**
	 * 顧客編集確認画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 顧客編集確認画面
	 */
	@PostMapping("/customer_editcheck")
	public String customer_editcheck(HttpServletRequest request,Model model) {
		model.addAttribute("customer_name", request.getParameter("customer_name"));
		model.addAttribute("customer_id", request.getParameter("customer_id"));
		model.addAttribute("status1", request.getParameter("status1"));
		model.addAttribute("status2", request.getParameter("status2"));
		model.addAttribute("status3", request.getParameter("status3"));
		model.addAttribute("status4", request.getParameter("status4"));
		model.addAttribute("status5", request.getParameter("status5"));
		model.addAttribute("status6", request.getParameter("status6"));
		model.addAttribute("status7", request.getParameter("status7"));
		model.addAttribute("status8", request.getParameter("status8"));
		model.addAttribute("status9", request.getParameter("status9"));
		model.addAttribute("status10", request.getParameter("status10"));

		return "customer_editcheck";
	}

	/**
	 * 顧客削除画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 顧客削除画面
	 */
	@PostMapping("/customer_delete/{id}")
	public String customer_delete(@PathVariable Long id,HttpServletRequest request, Model model) {


		String errmessage = (String) request.getAttribute("errmessage");
		if (errmessage != null) {	// エラーメッセージがある場合は代入する
			model.addAttribute("errmessage", errmessage);
		} else {
			model.addAttribute("errmessage", "");
		}
		Customer customer = userService.findByCustomer_Id(id);
		List<Status> status = userService.getStatusList(id);

		for(int i = 0;i < 10;i++) {	// 10回ループさせる
			if(status.size() - 1 >= i) {		// ステータス情報がある場合、情報を代入する
				model.addAttribute("status" + (i+1), status.get(i).getStatus_name());
			} else {
				model.addAttribute("status" + (i+1), "");	// ステータス情報が無い場合
			}
		}
		model.addAttribute("customer", customer);

		return "customer_delete";
	}


	@RequestMapping(value = "/customer_create", method = RequestMethod.POST)
	public String customer_create(HttpServletRequest request,Model model) {
		// 顧客登録処理
		Long id = userService.customer_create(request.getParameter("customer_name"));	// 登録処理を行った後IDを返す
		int count = 1;


		// ステータス登録処理
		for (int i = 0;i < 10;i++) {

			if (request.getParameter("status" + (i+1)).isEmpty() == false) {	// ステータス名が入力されているものを登録する
				userService.status_create(id,count,request.getParameter("status" + (i+1)));		// 登録処理
				count++;
			}

		}

		return "forward:/customer_list";
	}

	@RequestMapping(value = "/customer_update", method = RequestMethod.POST)
	public String customer_update(HttpServletRequest request,Model model) {
		// 顧客更新処理

		Long id = (long) Integer.parseInt(request.getParameter("customer_id"));	// Long型に変更
		userService.customer_update(id,request.getParameter("customer_name"));	// 顧客名の変更

		int count = 1;
		// ステータス登録処理
		for (int i = 0;i < 10;i++) {

			if (request.getParameter("status" + (i+1)).isEmpty() == false) {	// ステータス名が入力されているものを登録する
				userService.status_update(id,count,request.getParameter("status" + (i+1)));		// 登録処理
				count++;
			}

		}

		return "forward:/customer_list";
	}

	@RequestMapping(value = "/customer_deleteupdate", method = RequestMethod.POST)
	public String customer_deleteupdate(HttpServletRequest request,Model model) {
		// 顧客削除処理

		Long id = (long) Integer.parseInt(request.getParameter("customer_id"));	// Long型に変更

		if (userService.getListcheck(id) >= 1) {	// 削除予定の顧客idが使われている案件を確認
			model.addAttribute("errmessage", "顧客名が案件に使われているため削除できません");	// エラーメッセージ
			return "forward:customer_delete/" + id;	// 削除予定の顧客名が案件に使われている場合エラーメッセージを表示
		} else {
			userService.customer_deleteupdate(id);	// 顧客削除
		}


		return "forward:/customer_list";
	}



	// 更新処理
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute ManagementUpdateRequest managementUpdateRequest,
			BindingResult result,
			Model model) {
		// 情報の更新
		userService.update(managementUpdateRequest);
		return "forward:/list";
	}

	/**
	 * 削除画面を表示
	 * @param id 表示されるデータのID
	 * @param model Model
	 * @return 削除画面
	 */
	@PostMapping("/delete/{id}")
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
		return "forward:/list";
	}

}