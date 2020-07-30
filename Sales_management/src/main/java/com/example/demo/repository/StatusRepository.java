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
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {

	@Query(value = "SELECT * FROM status" , nativeQuery = true)
	 public List<Status> getStatus_name();		// ステータス情報を取得



}
