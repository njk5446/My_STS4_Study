package com.rubypaper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	@GetMapping({"/", "/index"})
	public String index() { // 타입에 맞는 반환되는 값의 html 파일을 찾는다 (index.html)
		System.out.println("index 요청");
		return "index";
	}
	
	@GetMapping("/member")
	public void member() { // void는 Mapping 어노테이션에 입력한 url의 html 파일을 찾는다 (member.html)
		System.out.println("Member 요청입니다.");
	}
	
	@GetMapping("/manager")
	public void forManager() {
		System.out.println("Manager 요청입니다.");
	}
	
	@GetMapping("/admin")
	public void forAdmin() {
		System.out.println("Admin 요청입니다.");
	}
}
