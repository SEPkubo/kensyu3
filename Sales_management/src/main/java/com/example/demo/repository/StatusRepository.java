package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Status;


/**
 * 顧客情報 Repository
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {	// 複合主キーに対してLongのみ(後々の問題になる可能性あり)

	@Query(value = "SELECT * FROM status" , nativeQuery = true)
	 public List<Status> getStatus_name();		// ステータス情報を取得

	@Query(value = "SELECT status_name FROM status WHERE customer_id = ?1 AND status_id = ?2" , nativeQuery = true)
	 public String findStatus_name(int customer_id,int status_id);		// idから顧客名を取得



}
