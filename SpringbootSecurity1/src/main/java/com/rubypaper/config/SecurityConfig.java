package com.rubypaper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Configuration 어노테이션을 선언한 클래스는 설정 클래스라고 정의한다(IoC 컨테이너에 로드)
@EnableWebSecurity // 해당 클래스내에 스프링 시큐리티 적용에 필요한 필터 객체들을 자동 생성한다
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean // Bean 어노테이션을 선언하면 해당 메서드가 리턴하는 객체를 IoC 컨테이너에 등록
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// SecurityFilterChain:  
		// HttpSecurity: 웹 보안 설정 클래스로, 빌터 패턴을 사용해서 보안 설정들을 체인 방식으로 연결하고, 
		//				 필터 형태로 추가되어 요청을 처리할때 순차적으로 적용
		http.authorizeHttpRequests(security->security
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.csrf(cf->cf.disable()); // CSRF 보호 비활성화(CSRF로부터 보호받지못한다)
		
		http.headers(hr->hr.frameOptions(fo->fo.disable()));

		
		http.formLogin(form->
				form.loginPage("/login")
					.defaultSuccessUrl("/loginSuccess", true)
					);
		
		http.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		
		http.logout(logout->logout
				.invalidateHttpSession(true) // 현재 브라우저와 연결된 세션 강제종료
				.deleteCookies("JSESSIONID") // 세션 아이디가 저장된 쿠키 삭제
				.logoutSuccessUrl("/login")); // 로그아웃 후 이동할 URL 지정  
		return http.build();
	}
	
//	@Autowired
//	public void authenticate(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("user")
//		.password("{noop}123"); // noop: 비밀번호가 암호화 되어있지 않다는 의미
//		auth.inMemoryAuthentication()
//		.withUser("manager")
//		.password("{noop}123") // 비밀번호가 암호화 되어있지 않다는 의미
//		.roles("MANAGER");
//		auth.inMemoryAuthentication()
//		.withUser("admin")
//		.password("{noop}123")
//		.roles("ADMIN");
//		}

}
