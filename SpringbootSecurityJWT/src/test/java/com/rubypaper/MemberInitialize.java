package com.rubypaper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rubypaper.domain.Member;
import com.rubypaper.domain.Role;
import com.rubypaper.persistence.MemberRepository;

@SpringBootTest
public class MemberInitialize {
	@Autowired
	MemberRepository memRepo;
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	// PasswordEncoder 비밀번호 암호화 및 검증 인터페이스

	@Test
	public void doWork() {
		memRepo.save(Member.builder().
				username("member").
				password(encoder.encode("abcd")).
				role(Role.ROLE_MEMBER)
				.enabled(true).build());
		memRepo.save(Member.builder()
				.username("manager")
				.password(encoder.encode("abcd"))
				.role(Role.ROLE_MANAGER)
				.enabled(true).build());
		memRepo.save(Member.builder()
				.username("admin")
				.password(encoder.encode("abcd"))
				.role(Role.ROLE_ADMIN)
				.enabled(true).build());
		// 리포지터리에 각 멤버 객체들의 정보를 저장(이름, 비밀번호(암호화해서), 역할)
	}
}
