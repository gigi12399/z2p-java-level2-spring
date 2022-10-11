package com.mmit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public MyUserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(myUserDetailsService());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain filterWeb(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/css/**",
					"/app/**",
					"/js/**",
					"/forgetPassword",
					"/resetPassword",
					"/successPassChange"
					).permitAll()
		.antMatchers("/**").hasRole("admin")
		.anyRequest()
		.authenticated() // filter/ 
		.and()
		.rememberMe().userDetailsService(this.myUserDetailsService())
		.and()
		.formLogin()// login 
			.loginPage("/login")
			.defaultSuccessUrl("/dashboard",true)
			.permitAll()
		.and()
		.logout()
			.logoutSuccessUrl("/")
			.permitAll();
		/*.and()
		.exceptionHandling()
		.accessDeniedPage("/403");*/
	
	return http.build();
	}
}
