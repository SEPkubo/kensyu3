package com.example.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.ManagementList;
import com.example.demo.entity.ManagementUpdate;
public class UserSpecifications {

    public static Specification<ManagementUpdate> subjectContains(String subject) {		// 件名検索
        return StringUtils.isEmpty(subject) ? null : new Specification<ManagementUpdate>() {
            @Override
            public Predicate toPredicate(Root<ManagementUpdate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	System.out.println("a");
                return cb.like(root.get("subject"), "%" + subject + "%");
            }
        };
    }

    public static Specification<ManagementUpdate> listJoin(String subject) {		// 顧客テーブル顧客id結合
        return StringUtils.isEmpty(subject) ? null : new Specification<ManagementUpdate>() {
            @Override
            public Predicate toPredicate(Root<ManagementUpdate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.join("customer", JoinType.LEFT).get("customer_id"), root.get("customer_id"));
            }
        };
    }

    public static Specification<ManagementUpdate> listJoinStatus(String subject) {		// ステータステーブルステータスid結合
        return StringUtils.isEmpty(subject) ? null : new Specification<ManagementUpdate>() {
            @Override
            public Predicate toPredicate(Root<ManagementUpdate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.join("status", JoinType.LEFT).get("status_id"), root.get("status_id"));
            }
        };
    }

    public static Specification<ManagementUpdate> listJoinStatus2(String subject) {		// ステータステーブル顧客id結合
        return StringUtils.isEmpty(subject) ? null : new Specification<ManagementUpdate>() {
            @Override
            public Predicate toPredicate(Root<ManagementUpdate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.join("status", JoinType.LEFT).get("customer_id"), root.get("customer_id"));
            }
        };
    }

    public static Specification<ManagementList> customer_nameContains(int customer_id) {	// 顧客検索
        return StringUtils.isEmpty(customer_id) ? null : new Specification<ManagementList>() {
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.le(root.get("customer_id"), customer_id);
            }
        };
    }

    public static Specification<ManagementList> statusContains(int status_id) {	// ステータス検索
        return StringUtils.isEmpty(status_id) ? null : new Specification<ManagementList>() {
            @Override
            public Predicate toPredicate(Root<ManagementList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.le(root.get("status"), status_id);
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

}
