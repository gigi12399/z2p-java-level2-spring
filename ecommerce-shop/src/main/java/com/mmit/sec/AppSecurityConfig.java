package com.mmit.sec;

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
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/",
					"/shop",
					"/shop/products/**",
					"/cart/detail",
					"/login",
					"/shop/register",
					"/shop/user/save",
					"/shop/about",
					"/shop/contact",
					"/forgetPassword",
					"/resetPassword",
					"/successPassChange",
					"/cart/detail/total",
					"/css/**",
					"/app/**",
					"/fonts/**",
					"/images/**",
					"/js/**",
					"/scss/**",
					"/upload-photos/**")
		.permitAll()
		.antMatchers("/admin/**").hasAnyRole("ADMIN","SELLER","DELIVERYMAN")
		.antMatchers("/cart/place-order").hasAnyRole("ADMIN","CUSTOMER","SELLER")
		.anyRequest()
		.authenticated() 
		.and()
		.rememberMe().userDetailsService(this.myUserDetailsService())// filter/ authorized
		.and()
		.formLogin() // login 
			.loginPage("/login")
			.defaultSuccessUrl("/cart/checkout",true)
			.permitAll()
		.and()
		.logout()
			.logoutSuccessUrl("/")
			.permitAll();
		//.and()
		//.exceptionHandling()
		//.accessDeniedPage("/403");
	
	return http.build();
	}
}
