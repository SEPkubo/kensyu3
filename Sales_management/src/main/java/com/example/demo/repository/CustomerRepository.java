package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;


/**
 * 顧客情報 Repository
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	/**
	 * プルダウンに使用
	 */
	@Query(value = "SELECT * FROM customer WHERE delete_flg = 0" , nativeQuery = true)
	 public List<Customer> getCustomer_name();		// 顧客名を取得


	/**
	 * 登録や編集の確認画面で使用
	 */
	@Query(value = "SELECT customer_name FROM customer WHERE customer_id = ?1 AND delete_flg = 0" , nativeQuery = true)
	 public String findCustomer_name(int id);		// idから顧客名を取得





}

