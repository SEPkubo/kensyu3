package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Management;


/**
 * ユーザー情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<Management, Long>, JpaSpecificationExecutor<Management> {

	@Query(value = "Select * from management where delete_flg = 0", nativeQuery = true)
	 public Page<Management> All(Pageable pageable);		// 検索条件がない場合の一覧表示

//	@Query(value = "Select * from management where delete_flg = 0 AND customer_name LIKE  %?1% AND status LIKE  %?2% AND subject LIKE  %?3%", nativeQuery = true)
//	 public Page<Management> ListSerch(String customer_name,String status,String serch_subject,Pageable pageable);		// 検索条件がある場合の一覧表示


}