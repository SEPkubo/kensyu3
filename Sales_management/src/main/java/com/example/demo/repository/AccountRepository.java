package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>,JpaSpecificationExecutor<Account> {

	// メールアドレスからユーザ取得
	public Account findByUsername(String username);

	// idからユーザ取得
	public Account findById(Long id);


}
