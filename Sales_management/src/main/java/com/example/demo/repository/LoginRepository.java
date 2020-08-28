package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Login;


/**
 * ログイン情報 Repository
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Long>, JpaSpecificationExecutor<Login> {

	/**
	 * ログイン画面パスワード確認クエリ
	 */
	@Query(value = "SELECT password FROM login WHERE mailaddress = ?1" , nativeQuery = true)
	 public String getPassword(String mailaddress);		// パスワードを取得
}

