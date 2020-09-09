package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;

/**
 * ログイン情報 Entity
 */
@Entity
@Data
@Table(name="login")
public class Login implements Serializable {

	/**
	    * ID
	    */
	    @Id
	    @Column(name="user_id")
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int user_id;


		/**
		 * メールアドレス
		 */
		@Column(name="mailaddress")
		private String mailaddress;

		/**
		 * パスワード
		 */
		@Column(name="password")
		private String password;

		/**
		 * 権限
		 */
		@Column(name="role")
		private String role;

		public String getRole() {
			return this.role;
		}

		public void setRole(String role){
			this.role = role;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			Collection<GrantedAuthority> authorityList = new ArrayList<>();
			authorityList.add(new SimpleGrantedAuthority("ROLE_" + this.role));
			return authorityList;
		}


}
