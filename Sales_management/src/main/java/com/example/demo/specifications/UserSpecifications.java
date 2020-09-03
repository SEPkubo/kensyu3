package com.example.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Login;
import com.example.demo.entity.ManagementList;
import com.example.demo.entity.Status;
public class UserSpecifications {

    public static Specification<ManagementList> subjectContains(String subject) {		// 件名検索
        return StringUtils.isEmpty(subject) ? null : new Specification<ManagementList>() {
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                return cb.like(root.get("subject"), "%" + subject + "%");
            }
        };
    }

    public static Specification<ManagementList> customer_nameContains(int customer_id) {	// 顧客検索
        return customer_id == -1 ? null : new Specification<ManagementList>() {	// customer_idが-1なら検索しない
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerid"), customer_id);
            }
        };
    }

    public static Specification<ManagementList> statusContains(int status_id) {	// ステータス検索
        return status_id == -1 ? null : new Specification<ManagementList>() {	// status_idが-1なら検索しない
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	System.out.println("status");
                return cb.equal(root.get("status_id"), status_id);
            }
        };
    }

    public static Specification<ManagementList> delete_flgCheck() {	// 削除フラグチェック
        return  new Specification<ManagementList>() {
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("delete_flg"), 0);
            }
        };
    }

    public static Specification<ManagementList> s_number(String s_number) {	// S番号検索
        return StringUtils.isEmpty(s_number) ? null : new Specification<ManagementList>() {
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("s_number"),s_number);
            }
        };
    }

    // ログイン認証
    public static Specification<Login> LoginCheckMailaddress(String mailaddress) {	// メールアドレス確認
        return  new Specification<Login>() {
            @Override
            public Predicate toPredicate(Root<Login> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("mailaddress"),mailaddress);
            }
        };
    }

    // ログイン認証
    public static Specification<Login> LoginCheckPassword(String password) {	// パスワード確認
        return  new Specification<Login>() {
            @Override
            public Predicate toPredicate(Root<Login> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("password"),password);
            }
        };
    }

    // 顧客編集画面で使用
    public static Specification<Status> findstatus(Long id) {	// 顧客idからステータス情報取得
        return  new Specification<Status>() {
            @Override
            public Predicate toPredicate(Root<Status> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customer_id"), id);
            }
        };
    }



}
