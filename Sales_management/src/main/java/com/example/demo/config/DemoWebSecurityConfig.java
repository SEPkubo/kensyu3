package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class DemoWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // AUTHORIZE
            .authorizeRequests()
            /* */.mvcMatchers().permitAll()		// 認証を除外するものを指定
            	.antMatchers("/add","/addcheck","/edit/**","/editcheck","/delete/**","/customer_add","/customer_addcheck","/customer_edit/**","/customer_editcheck","/customer_delete/**").hasRole("ADMIN")	// ADMIN権限を持つユーザのみアクセス可能
            /* */.anyRequest().authenticated()	// 除外したもの以外は要認証
            .and()
            .exceptionHandling()
            .accessDeniedPage("/err");	// 認証エラー画面
        http
            // LOGIN
            .formLogin()
            /* */.loginPage("/login")	// 使用するログイン画面
            /*    */.permitAll()
            /* */.defaultSuccessUrl("/list")	// ログイン後の遷移画面
        // end
        ;

        // ログアウト設定
        http
        	.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))       // ログアウト処理のパス
            .logoutSuccessUrl("/login");	// ログアウト成功したあとのURL


    }


	@Autowired
	void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception{

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}