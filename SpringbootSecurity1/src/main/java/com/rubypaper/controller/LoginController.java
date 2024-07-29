package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rubypaper.domain.Member;
import com.rubypaper.service.MemberService;

@Controller
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String joinProc(Member member) {
		memberService.save(member);
		return "welcome";
	}
	
	@GetMapping("/login")
	public void login() {
		System.out.println("login 요청");
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("loginSuccess 요청");
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("accessDenied");
	}
	
	@GetMapping("/auth")
	public @ResponseBody ResponseEntity<?> auth(@AuthenticationPrincipal User user) {
	 //ResponseBody로 받아야 화면에 출력이 가능 (http 출력은 body로 돌아가기때문)
		
		if(user == null) {
			return ResponseEntity.ok("로그인 상태가 아닙니다.");
		}
		return ResponseEntity.ok(user);
	}
}
