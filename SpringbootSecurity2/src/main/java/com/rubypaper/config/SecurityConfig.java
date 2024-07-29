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

@Configuration 
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	PasswordEncoder encode() {
		return new BCryptPasswordEncoder(); 
		// 비밀번호를 해싱, 검증(입력한 비밀번호와 해시된 비밀번호를 비교)을 수행하는 내장객체
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//Http 보안 설정 과정에서 여러 예외가 발생 할 수 있으므로 예외를 던져줘야 서버가 정상 구동된다.
		
		http.authorizeHttpRequests(security->security
				// **는 와일드카드로 모든 하위 경로를 포함한다는 뜻(그냥 외우셈)
				.requestMatchers("/member/**").authenticated() 
			    // authenticated: 해당 경로(member)로 들어가는 요청은 인증된 사용자만 접근 가능
				// (서버 실행시 주어지는 password나, 설정한 password로 접속에 성공한 사용자만 접근가능)
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				// hasAnyRole: 해당 경로(manager)로 들어가는 요청은 파라미터안에 들어가는 Role 중 하나만 만족해도 접근 가능
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()); // 나머지 요청은 허용됨(그냥 설정을 마치는걸로 이해하자)
				// hasRole: 해당 경로(admin)로 들어가는 요청은 파라미터안에 들어있는 것을 만족해야 접근 가능
		http.csrf(cf->cf.disable()); // CSRF 보호 비활성화(h2-console 접속에 필ㅇ
		http.formLogin(form->form
							.loginPage("/login")
							.defaultSuccessUrl("/loginSuccess", false)); 
		http.headers(hr->hr.frameOptions(fo->fo.disable()));
		// formLogin: HttpSecurity에서 제공하는 기본 로그인 폼을 사용하는 메서드
		// loginPage(): 기본 로그인폼을 사용자가 커스터마이징해서 html을 구현하도록하는 메서드
		// defaultSuccessUrl(): 로그인 성공시 기본적으로 접속되는 url주소를 설정
		
		http.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		// 예외처리 페이지 출력
		
		http.logout(logout->logout
				.invalidateHttpSession(true) //현재 브라우저와 연결된 세션 강제 종료
				.deleteCookies("JSESSIONID")
				 // logout시 쿠키삭제, JSESSIONID는 서버가 클라이언트를 식별하기 위해 사용하는 고유한 식별자
				.logoutSuccessUrl("/login")); // 로그아웃 성공시 해당 경로로 이동
		
		return http.build(); 
		// HttpSecurity는 빌더 패턴이기 때문에 build()로 모든 설정 저장을 마치고 SecurityFilterChain으로 반환
		// SecurityFilterChain은 저장된 보안 설정으로 보안 검사를 체인방식으로 순차적으로 수행한다
	}
	
//	@Autowired
//	public void autheticate(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("user") // 사용자 아이디(계정) 생성
//			.password("{noop}123") // 사용자 패스워드 생성
//			.roles("MEMBER"); // 사용자 Role 생성
//		auth.inMemoryAuthentication()
//			.withUser("manager") // 사용자 아이디(계정) 생성
//			.password("{noop}123") // 사용자 패스워드 생성
//			.roles("MANAGER"); // 사용자 Role 생성
//		auth.inMemoryAuthentication()
//			.withUser("admin") // 사용자 아이디(계정) 생성
//			.password("{noop}123") // 사용자 패스워드 생성
//			.roles("ADMIN"); // 사용자 Role 생성
//	}
	
	
}
