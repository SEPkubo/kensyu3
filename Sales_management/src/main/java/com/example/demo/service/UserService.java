package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Management;
import com.example.demo.repository.UserRepository;


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
        return userRepository.findAll(pageable);
    }





	public List<Map<String, Object>> queryForList(String string) {
		return null;
	}


}
