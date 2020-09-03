package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ManagementRequest;
import com.example.demo.dto.ManagementUpdateRequest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ManagementList;
import com.example.demo.entity.ManagementUpdate;
import com.example.demo.entity.Status;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.ManagementUpdateRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.specifications.UserSpecifications;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {

	/**
	 * ユーザー情報 Repository
	 */
	@Autowired
	UserRepository userRepository;


	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	ManagementUpdateRepository managementUpdateRepository;

	@Autowired
	LoginRepository loginRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */


	// 一覧取得(検索条件がない場合)
	public Page<ManagementList> getList(Pageable pageable) {
        return (Page<ManagementList>) userRepository.findAll((Specification
				 .where(UserSpecifications.delete_flgCheck()))
				 ,pageable);
    }


	// 一覧取得(検索条件がある場合)
	 public Page<ManagementList> getListSerch(Pageable pageable,int customer_id,int status_id,String serch_subject) {
		 return (Page<ManagementList>) userRepository.findAll((Specification
				 .where(UserSpecifications.subjectContains(serch_subject))
				 .and(UserSpecifications.customer_nameContains(customer_id))
				 .and(UserSpecifications.statusContains(status_id))
				 .and(UserSpecifications.delete_flgCheck()))
				 ,pageable);

	 }


		// 顧客一覧取得
		public List<Customer> getcoustomer_list() {
	        return customerRepository.findAll();
	    }

		// ログイン情報確認
	 public String getLoginCheck(String mailaddress) {
		 return loginRepository.getPassword(mailaddress);



	 }



	// S番号重複チェック
	 public Long s_numberCheck(String s_number) {
		 return userRepository.count(Specification
				 .where(UserSpecifications.s_number(s_number))
			    );

	 }

	 // プルダウンの顧客名取得
	 public List<Customer> getCustomer_name() {
		 return customerRepository.getCustomer_name();

	 }

	 // プルダウンのステータス情報取得
	 public List<Status> getStatus_name() {
		 return statusRepository.getStatus_name();

	 }

	 // idから顧客名取得
	 public String findCustomer_name(int Customer_id) {
		 return customerRepository.findCustomer_name(Customer_id);

	 }

	 // idからステータス情報取得
	 public String findStatus_name(int Customer_id,int Status_id) {
		 return statusRepository.findStatus_name(Customer_id,Status_id);

	 }


	// 案件作成
	public void create(ManagementRequest managementRequest) {
		managementUpdateRepository.save(CreateManagement(managementRequest));
	}

	// 顧客登録
		public Long customer_create(String customer_name) {
			Long id;
			Customer customer = new Customer();
			customer.setCustomer_name(customer_name);
			customerRepository.save(customer);	// 顧客の登録処理を行う
			id = customer.getCustomer_id();
			return id;		// 登録した顧客のIDを返す
		}

		// ステータス登録
		public void status_create(Long customer_id,int status_id,String status_name) {
			Status status = new Status();
			status.setCustomer_id(customer_id);
			status.setStatus_id(status_id);
			status.setStatus_name(status_name);
			statusRepository.save(status);	// ステータスの登録処理を行う

		}

		// 顧客編集
		public void customer_update(Long customer_id,String customer_name) {

			Customer customer = customerRepository.getOne(customer_id);
			System.out.println(customer);
			customer.setCustomer_name(customer_name);
			customerRepository.save(customer);


		}

		// ステータス編集
				public void status_update(Long customer_id,int status_id,String status_name) {
					Status status = statusRepository.findid(customer_id,status_id);
					if(status != null) {
						status.setStatus_name(status_name);
					} else {
						status = new Status();
						status.setCustomer_id(customer_id);
						status.setStatus_id(status_id);
						status.setStatus_name(status_name);

					}

					statusRepository.save(status);	// ステータスの更新処理を行う

				}



	/**
	 * ユーザーTBLエンティティの生成
	 * @param managementRequest ユーザー情報リクエストデータ
	 * @return ユーザーTBLエンティティ
	 */

	private ManagementUpdate CreateManagement(ManagementRequest managementRequest) {
		ManagementUpdate ｍanagement = new ManagementUpdate();
		ｍanagement.setCustomer_id(managementRequest.getCustomer_id());
		ｍanagement.setOrderdate(managementRequest.getOrderdate());
		ｍanagement.setS_number(managementRequest.getS_number());
		ｍanagement.setSubject(managementRequest.getSubject());
		ｍanagement.setQuantity(managementRequest.getQuantity());
		ｍanagement.setUnit(managementRequest.getUnit());
		ｍanagement.setDelivery_designation(managementRequest.getDelivery_designation());
		ｍanagement.setDelivery_date(managementRequest.getDelivery_date());
		ｍanagement.setBilling_date(managementRequest.getBilling_date());
		ｍanagement.setEstimated_money(managementRequest.getEstimated_money());
		ｍanagement.setOrder_money(managementRequest.getOrder_money());
		ｍanagement.setStatus_id(managementRequest.getStatus_id());
		ｍanagement.setNote(managementRequest.getNote());

		return ｍanagement;
	}


	/**
     * 案件情報 更新
     * @param user ユーザー情報
     */
    public void update(ManagementUpdateRequest managementUpdateRequest) {
    	ManagementUpdate ｍanagement = findById(managementUpdateRequest.getId());
    	ｍanagement.setCustomer_id(managementUpdateRequest.getCustomer_id());
		ｍanagement.setOrderdate(managementUpdateRequest.getOrderdate());
		ｍanagement.setS_number(managementUpdateRequest.getS_number());
		ｍanagement.setSubject(managementUpdateRequest.getSubject());
		ｍanagement.setQuantity(managementUpdateRequest.getQuantity());
		ｍanagement.setUnit(managementUpdateRequest.getUnit());
		ｍanagement.setDelivery_designation(managementUpdateRequest.getDelivery_designation());
		ｍanagement.setDelivery_date(managementUpdateRequest.getDelivery_date());
		ｍanagement.setBilling_date(managementUpdateRequest.getBilling_date());
		ｍanagement.setEstimated_money(managementUpdateRequest.getEstimated_money());
		ｍanagement.setOrder_money(managementUpdateRequest.getOrder_money());
		ｍanagement.setStatus_id(managementUpdateRequest.getStatus_id());
		ｍanagement.setNote(managementUpdateRequest.getNote());
		managementUpdateRepository.save(ｍanagement);
    }

	 /**
     * 案件情報 削除
     * @param user ユーザー情報
     */
    public void delete(ManagementUpdateRequest managementUpdateRequest) {
    	ManagementUpdate ｍanagement = findById(managementUpdateRequest.getId());
    	ｍanagement.setDelete_flg(1);
    	managementUpdateRepository.save(ｍanagement);
    }

	/**
     * 案件情報 主キー検索
     * @return 検索結果
     */
    public ManagementUpdate findById(Long id) {
        return managementUpdateRepository.findId(id);
    }

	/**
     * 顧客情報 ID検索
     * 顧客編集画面で使用
     * @return 検索結果
     */
    public Customer findByCustomer_Id(Long id) {
        return customerRepository.findById(id).get();		// 顧客idで情報を取得
    }

	// 一覧取得(検索条件がない場合)
	public List<Status> getStatusList(Long id) {
        return statusRepository.findAll((Specification
				 .where(UserSpecifications.findstatus(id))));
    }


	public List<Map<String, Object>> queryForList(String string) {
		return null;
	}


}
