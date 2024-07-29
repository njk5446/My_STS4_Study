package com.rubypaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

@Service
public class BoardUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//memRepo에서 사용자 정보를 검색
		Member member = memberRepo.findById(username)
							.orElseThrow(()->new UsernameNotFoundException("Not Found"));
		System.out.println(member);

		return new User(member.getUsername(), member.getPassword(),
					AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
	
}
