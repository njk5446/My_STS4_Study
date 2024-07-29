package com.rubypaper.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// OncePerRequestFilter 
// 사용이유: (중복방지) 동일요청이 여러 번 실행되지 않도록 한다. 
// 동일한 요청이 중복된 인증 작업이 일어나면 비정상적인 상태가 될 수 있기 때문
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	// 인가 설정을 위해 사용자의 Role 정보를 읽어 들이기 위한 객체 설정
	private final MemberRepository memberRepo;
	
	@Override // doFilter 메서드는 하나의 요청에 대해 단 한번만 필터를 거치게한다 (사용자 정의 메서드로 바꿈)
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		String srcToken = req.getHeader("Authorization"); // 요청 헤더에서 Authorizationd을 얻어온다.
		if(srcToken == null || !srcToken.startsWith("Bearer ")) { // 토큰이 비어있거나 Bearer로 시작하지않으면,
			filterChain.doFilter(req, res);
			return; 
		}
		
		String jwtToken = srcToken.replace("Bearer ", ""); // 토큰에서 "Bearer "를 제거
		
		// 토큰에서 username 추출
		String username = JWT.require(Algorithm.HMAC256("com.rubypaper.jwt"))
						  .build()
						  .verify(jwtToken)
						  .getClaim("username")
						  .asString();
		
		Optional<Member> opt = memberRepo.findById(username); 
		// 토큰에서 얻은 username으로 DB를 검색해서 사용자를 검색

		if (!opt.isPresent()) { // 사용자가 존재하지않으면, 
			filterChain.doFilter(req, res); // 필터를 그냥 통과
			return;
		}
		
		Member findmember = opt.get(); // 
		
		// DB에서 읽은 사용자 정보를 이용해서 UserDetails 타입의 객체를 생성
		User user = new User(findmember.getUsername(), 
				findmember.getPassword(), 
				AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
		
		// Authentication 객체를 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호는 필요 없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세션에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(req, res);
		
		
	}

}
