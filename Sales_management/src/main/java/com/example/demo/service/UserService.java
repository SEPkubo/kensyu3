package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {


}
