package com.rubypaper.config.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubypaper.domain.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// AuthenticationManager는 인증 객체이다.
	// Service 클래스로부터 정보를 반환 받고,
	// 그 정보의 인증이 성공하면 인증된 사용자 정보를 포함하는 Authentication 객체를 반환하고,
	// 실패하면 예외를 발생시킨다.
	private final AuthenticationManager authenticationManager;
	
	
	// 별도의 Post 매핑 어노테이션 없이도 필터가 HTTP 요청을 직접 가로채서 처리한다
	// UsernamePasswordAuthenticationFilter 내장 인터페이스를 통해서 POST/login 요청이 왔을 때 인증을 시도하는 메서드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws org.springframework.security.core.AuthenticationException {

		ObjectMapper mapper = new ObjectMapper();
		// HTTP 요청의 JSON 형태로 전송된 사용자 인증 정보 username(키): password(밸류)를
		// Member 객체로 변환하기 위해 사용
		try {
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			// 매핑을 통해 key, value 형태를 읽어 들여 member의 필드로 대입

			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(),
					member.getPassword());
			// authToken의 구조: name, password (주어진 필드들)
			return authenticationManager.authenticate(authToken); 
			// AuthenticationManager에 authToken을 넣고 인증 성공 실패 확인
			// 성공하면 authToken의 인증된 사용자 정보를 포함한 AuthenticationManager 객체를 반환 
			// 실패하면 예외 발생 
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		// 실패시, 인증 실패값을 http에 응답한다
		return null;
		// 실패시 , null을 반환 
	}

	@Override // 인증 성공 시 자동으로 실행되는 후처리 메서드
	// 인증에 성공하면 실행이 되는 메서드로, HTTP 요청시 입력한 이름(ID)으로 토큰을 발행한다
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// 자격 증명이 성공하면 loadUserByUsername에서 만든 객체가 authResult에 담겨져 있다.
		User user = (User) authResult.getPrincipal();
		// username으로 JWT를 생성해서 Response Header - Authorization에 담아서 돌려준다.
		// 이것은 하나의 예시로서 필요에 따라 추가 정보를 담을 수 있다.
		String token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1000))
				.withClaim("username", user.getUsername())
				.sign(Algorithm.HMAC256("com.rubypaper.jwt"));
		response.addHeader(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + token);
		response.setStatus(200); //200(OK) 상태 코드는 요청이 성공했음을 나타냅니다. 
		//chain.doFilter(request, response);
	}
	
	

}
