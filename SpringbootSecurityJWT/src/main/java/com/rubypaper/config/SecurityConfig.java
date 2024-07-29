package com.rubypaper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import com.rubypaper.config.filter.JWTAuthenticationFilter;
import com.rubypaper.config.filter.JWTAuthorizationFilter;
import com.rubypaper.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable()); // CSRF 보호 비활성화
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.formLogin(frmLogin->frmLogin.disable()); // 폼 로그인을 사용하지 않음
		http.httpBasic(basic->basic.disable()); // http 기본인증을 사용하지 않음
		http.headers(header->header.frameOptions(f->f.disable())); // X-frame 비활성화: 웹 애플리케이션 동작 허용 
		
		http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//STATELESS: 세션 유지하지 않는 설정
		
		http.addFilter(new JWTAuthenticationFilter(
				authenticationConfiguration.getAuthenticationManager()));
		
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		
		return http.build();
	}

}
