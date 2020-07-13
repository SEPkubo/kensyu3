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
public interface UserRepository extends JpaRepository<Management, Long>,JpaSpecificationExecutor<Management> {

	@Query(value = "Select * from management ", nativeQuery = true)
	 public Page<Management> findAll(Pageable pageable);		// 検索条件がない場合の一覧表示



}