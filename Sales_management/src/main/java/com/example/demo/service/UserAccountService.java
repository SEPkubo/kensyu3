package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserAccount;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.specifications.UserSpecifications;
@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;	// ハッシュ化エンコーダ

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	// ログイン判定
	       if (username == null || "".equals(username)) {
	            throw new UsernameNotFoundException("Username is empty");
	        }

	        Account ac = repository.findByUsername(username);
	        if (ac == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }

	        if (!ac.isEnabled()) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }

	        UserAccount user = new UserAccount(ac,getAuthorities(ac));

	        return user;
	}

	private Collection<GrantedAuthority> getAuthorities(Account account){	// 権限のセット
		if(account.isAdmin()){
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

	}

    @Transactional
    public void registerAdmin(String username, String password,boolean admin) {	// ユーザ作成
        Account user = new Account(username, passwordEncoder.encode(password),admin);
        repository.save(user);
    }

    @Transactional
    public void user_update(Long id,String username,boolean admin) {	// ユーザ更新
        Account user = getAccount(id);
        user.setUsername(username);
        user.setAdmin(admin);
        repository.save(user);
    }

    public void user_passwordupdate(Long id,String password) {	// ユーザパスワード更新
        Account user = getAccount(id);
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    public boolean cheakuser(String username) {	// ユーザがすでに使われていないか確認
    	Account ac = repository.findByUsername(username);
        if (ac != null) {
        	return false;
        }
        return true;
    }

    public void user_deleteupdate(Long id) {	// ユーザ削除
        Account user = getAccount(id);
        user.setEnabled(false);
        repository.save(user);
    }

 // 顧客一覧取得
 	public List<Account> getuser_list() {
 		return repository.findAll((Specification
				.where(UserSpecifications.user_enabledCheck())));
 	}

 	// idからアカウント情報取得
 	public Account getAccount(Long id) {
 		return repository.findById(id);
 	}


}