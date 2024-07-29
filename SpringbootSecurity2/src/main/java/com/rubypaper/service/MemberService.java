package com.rubypaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.domain.Role;
import com.rubypaper.persistence.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private MemberRepository memberRepo;
	
	public void save(Member member) {
	member.setPassword(encoder.encode(member.getPassword()));
	member.setEnabled(true);
	member.setRole(Role.ROLE_MEMBER);
	memberRepo.save(member);
	}

}
