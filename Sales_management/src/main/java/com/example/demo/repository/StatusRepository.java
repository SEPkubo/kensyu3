package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Status;


/**
 * ステータス情報 Repository
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {	// 複合主キーに対してLongのみ(後々の問題になる可能性あり)

	/**
	 * プルダウンに使用
	 */
	@Query(value = "SELECT * FROM status WHERE delete_flg = 0" , nativeQuery = true)
	 public List<Status> getStatus_name();		// ステータス情報を取得

	/**
	 * 登録画面や編集画面で使用,ステータス編集画面で使用
	 */
	@Query(value = "SELECT status_name FROM status WHERE customer_id = ?1 AND status_id = ?2 AND delete_flg = 0" , nativeQuery = true)
	 public String findStatus_name(int customer_id,int status_id);		// idから顧客名を取得

	/**
	 * 顧客編集画面で使用
	 */
	@Query(value = "SELECT * FROM status WHERE customer_id = ?1 AND status_id = ?2 AND delete_flg = 0" , nativeQuery = true)
	 public Status findid(Long customer_id,int status_id);		// idから顧客名を取得




}
