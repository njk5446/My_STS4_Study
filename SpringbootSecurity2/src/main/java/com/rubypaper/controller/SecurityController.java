package com.rubypaper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	@GetMapping({"/", "/index"})
	public String index() {
		System.out.println("index 요청");
		return "index"; // 타입에 맞는 반환되는 타입의 값의 html 파일을 찾는다 (index.html)
	}
	
	@GetMapping("/member")
	public void member() {
		System.out.println("Member 요청"); // void는 해당 Mapping 어노테이션에 입력한 값을 찾는다 "/member"
	}
	
	@GetMapping("/manager")
	public void manager() {
		System.out.println("Manager 요청");
	}
	
	@GetMapping("/admin")
	public void admin() {
		System.out.println("Admin 요청");
	}
	
//	@GetMapping("/loginSuccess")
//	public void loginSuccess() {
//		System.out.println("loginSuccess 요청");
//	}
	
//	@GetMapping("/login")
//	public void login() {
//		System.out.println("login 요청");
//	}

}
