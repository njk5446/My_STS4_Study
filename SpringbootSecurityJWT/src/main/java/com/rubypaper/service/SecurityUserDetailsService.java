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

@Service // UserDetailsService에서 반환한 값은 스프링 부트 자체에서 처리해서 AuthenticationManager로 반환된다.
public class SecurityUserDetailsService implements UserDetailsService{
	
	// 해당 서비스 클래스의 역할: DB 리포지터리에 저장된 데이터를 가져와서 AuthenticationManager에 반환한다
	
	@Autowired private MemberRepository memeRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username)
	throws UsernameNotFoundException
	{
		Member member = memeRepo.findById(username)
								.orElseThrow(()->new UsernameNotFoundException("Not Found"));
		return new User(member.getUsername(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
	
	
	
	
}
