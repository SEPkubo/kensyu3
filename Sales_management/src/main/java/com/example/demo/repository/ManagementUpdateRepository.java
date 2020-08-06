package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ManagementUpdate;

/**
 * 案件更新用Repository
 */
@Repository
public interface ManagementUpdateRepository
		extends JpaRepository<ManagementUpdate, Long>, JpaSpecificationExecutor<ManagementUpdate> {

	@Query(value = "SELECT id,management.customer_id,management.orderdate,management.s_number,management.subject,management.quantity,management.delivery_designation,management.delivery_date,management.billing_date,management.estimated_money,management.Order_money,management.status_id,management.note,management.delete_flg FROM management INNER JOIN customer ON management.customer_id = customer.customer_id INNER JOIN status ON management.customer_id = status.customer_id AND management.status_id = status.status_id WHERE id = ?1", nativeQuery = true)
	public ManagementUpdate findId(Long id); // 編集画面で表示する情報を取得

}