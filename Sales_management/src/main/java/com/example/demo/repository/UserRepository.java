package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ManagementList;


/**
 * 案件情報 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<ManagementList, Long>, JpaSpecificationExecutor<ManagementList> {


	@Query(value = "SELECT * FROM management INNER JOIN customer ON management.customer_id = customer.customer_id " +
			"INNER JOIN status ON management.customer_id = status.customer_id AND management.status_id = status.status_id AND delete_flg = 0 order by 1", nativeQuery = true)
	 public Page<ManagementList> All(Pageable pageable);		// 検索条件がない場合の一覧表示


	@Query(value = "SELECT * FROM management INNER JOIN customer ON management.customer_id = customer.customer_id " +
			"INNER JOIN status ON management.customer_id = status.customer_id AND management.status_id = status.status_id AND delete_flg = 0 order by 1", nativeQuery = true)
	 public Page<ManagementList> find(Pageable pageable,Specification<ManagementList> spec);		// 検索条件がある場合の一覧表示



}