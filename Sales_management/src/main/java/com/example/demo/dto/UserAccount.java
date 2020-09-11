package com.example.demo.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Account;

public class UserAccount implements UserDetails {

	private static final long serialVersionUID = -256740067874995659L;

	private Account user;
	private Collection<GrantedAuthority> authorities;

	protected UserAccount(){}

	public UserAccount(Account account,Collection<GrantedAuthority> authorities){	// 権限情報を保持するリスト
		this.user = account;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return this.authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {	// アカウントの期限きれ
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {		// アカウントのロック
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	// 資格情報の期限切れ
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
