package com.rubypaper;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rubypaper.domain.Member;
import com.rubypaper.domain.Role;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberInitialize implements ApplicationRunner{
	
	private final MemberRepository memRepo;
	private final PasswordEncoder encoder; 
	// 비밀번호를 안전한 형태로 변환한 후 저장하는 내장객체
	// 로그인 시에는 입력된 비밀번호와 저장된 비밀번호를 비교하는 데 사용
	
	
	@Override // 서버 실행시 사용자의 정보(아이디,패스워드)가 MemberRepository에 저장된다.
	public void run(ApplicationArguments args) throws Exception {
		memRepo.save(Member.builder().username("member").password(encoder.encode("123"))
				.role(Role.ROLE_MEMBER).enabled(true).build());
		memRepo.save(Member.builder().username("manager").password(encoder.encode("123"))
				.role(Role.ROLE_MANAGER).enabled(true).build());
		memRepo.save(Member.builder().username("admin").password(encoder.encode("123"))
				.role(Role.ROLE_ADMIN).enabled(true).build());

	}
}

