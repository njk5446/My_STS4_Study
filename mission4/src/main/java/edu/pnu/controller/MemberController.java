package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dao.LogDao;
import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;



@RestController //@Controller + @ResponseBody
public class MemberController {
	MemberService ms;
	
	public MemberController() {
		ms = new MemberService();
	}
	
	@GetMapping("/members") 
	// GET 타입으로 localhost:8080/members 에 클라이언트가 접속하면(Request)
	// 아래 메소드를 실행한다(Response) // 에러가 나면 404, 405 등등을 Response한다.
	public List<MemberDTO> allList() {
		// 리스트 타입
		return ms.serviceAllmember(); 

	}
	
	
	
}
