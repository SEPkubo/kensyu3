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
import com.example.demo.entity.Management;
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

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */





	// 一覧取得(検索条件がない場合)
	public Page<Management> getList(Pageable pageable) {
        return userRepository.All(pageable);
    }


	// 一覧取得(検索条件がある場合)
	 public Page<Management> getListSerch(Pageable pageable,String customer_name,String status,String serch_subject) {
		 return (Page<Management>) userRepository.findAll((Specification
				 .where(UserSpecifications.subjectContains(serch_subject))
				 .and(UserSpecifications.customer_nameContains(customer_name))
				 .and(UserSpecifications.statusContains(status)))
				 ,pageable
			    );

	 }


	// S番号重複チェック
	 public Long s_numberCheck(String s_number) {
		 return userRepository.count(Specification
				 .where(UserSpecifications.s_number(s_number))
			    );

	 }



	// 案件作成
	public void create(ManagementRequest managementRequest) {
		userRepository.save(CreateManagement(managementRequest));
	}


	/**
	 * ユーザーTBLエンティティの生成
	 * @param managementRequest ユーザー情報リクエストデータ
	 * @return ユーザーTBLエンティティ
	 */

	private Management CreateManagement(ManagementRequest managementRequest) {
		Management ｍanagement = new Management();
		ｍanagement.setCustomer_name(managementRequest.getCustomer_name());
		ｍanagement.setOrderdate(managementRequest.getOrderdate());
		ｍanagement.setS_number(managementRequest.getS_number());
		ｍanagement.setSubject(managementRequest.getSubject());
		ｍanagement.setQuantity(managementRequest.getQuantity());
		ｍanagement.setDelivery_designation(managementRequest.getDelivery_designation());
		ｍanagement.setDelivery_date(managementRequest.getDelivery_date());
		ｍanagement.setBilling_date(managementRequest.getBilling_date());
		ｍanagement.setEstimated_money(managementRequest.getEstimated_money());
		ｍanagement.setOrder_money(managementRequest.getOrder_money());
		ｍanagement.setStatus(managementRequest.getStatus());
		ｍanagement.setNote(managementRequest.getNote());

		return ｍanagement;
	}


	/**
     * 案件情報 更新
     * @param user ユーザー情報
     */
    public void update(ManagementUpdateRequest managementUpdateRequest) {
    	Management ｍanagement = findById(managementUpdateRequest.getId());
    	ｍanagement.setCustomer_name(managementUpdateRequest.getCustomer_name());
		ｍanagement.setOrderdate(managementUpdateRequest.getOrderdate());
		ｍanagement.setS_number(managementUpdateRequest.getS_number());
		ｍanagement.setSubject(managementUpdateRequest.getSubject());
		ｍanagement.setQuantity(managementUpdateRequest.getQuantity());
		ｍanagement.setDelivery_designation(managementUpdateRequest.getDelivery_designation());
		ｍanagement.setDelivery_date(managementUpdateRequest.getDelivery_date());
		ｍanagement.setBilling_date(managementUpdateRequest.getBilling_date());
		ｍanagement.setEstimated_money(managementUpdateRequest.getEstimated_money());
		ｍanagement.setOrder_money(managementUpdateRequest.getOrder_money());
		ｍanagement.setStatus(managementUpdateRequest.getStatus());
		ｍanagement.setNote(managementUpdateRequest.getNote());
        userRepository.save(ｍanagement);
    }

	 /**
     * 案件情報 削除
     * @param user ユーザー情報
     */
    public void delete(ManagementUpdateRequest managementUpdateRequest) {
    	Management ｍanagement = findById(managementUpdateRequest.getId());
    	ｍanagement.setDelete_flg(1);
        userRepository.save(ｍanagement);
    }

	/**
     * 案件情報 主キー検索
     * @return 検索結果
     */
    public Management findById(Long id) {
        return userRepository.findById(id).get();
    }


	public List<Map<String, Object>> queryForList(String string) {
		return null;
	}


}
