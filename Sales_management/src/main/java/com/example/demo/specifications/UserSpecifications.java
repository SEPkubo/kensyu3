package com.example.demo.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Management;
public class UserSpecifications {

    public static Specification<Management> subjectContains(String subject) {		// 件名検索
        return StringUtils.isEmpty(subject) ? null : new Specification<Management>() {
            @Override
            public Predicate toPredicate(Root<Management> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("subject"), "%" + subject + "%");
            }
        };
    }

    public static Specification<Management> customer_nameContains(String customer_name) {	// 顧客検索
        return StringUtils.isEmpty(customer_name) ? null : new Specification<Management>() {
            @Override
            public Predicate toPredicate(Root<Management> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("customer_name"), "%" + customer_name + "%");
            }
        };
    }

    public static Specification<Management> statusContains(String status) {	// ステータス検索
        return StringUtils.isEmpty(status) ? null : new Specification<Management>() {
            @Override
            public Predicate toPredicate(Root<Management> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("status"), "%" + status + "%");
            }
        };
    }

}
