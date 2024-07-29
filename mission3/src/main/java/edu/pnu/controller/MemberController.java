package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;



@RestController //@Controller + @ResponseBody
public class MemberController { 
	MemberService ms; // MemberService 메서드를 사용하기 위해서 MemberService타입의 참조변수를 미리 생성한다 
	
	public MemberController() { // 컨트롤러의 기본생성자
		System.out.println("MemberController 호출");
	}
	
	@GetMapping("/members")  //GetMapping : 값을 화면에 반환
	//@GetMapping 애너테이션이 위에 있는 메서드는 /members를 url에 입력시에 사용되는 메서드
	// GET 타입으로 localhost:8080/members에 클라이언트가 접속해서 Request로 데이터를 서버에 전송
	// 아래 메소드를 실행한다(Response) // 에러가 나면 404, 405 등등을 Response한다.
	public List<MemberDTO> allList() { //리스트를 반환하기 위해서 타입은 List<MemberDTO>
		// 리스트 타입
		return ms.serviceAllmember(); // MemberService의 메서드를 호출

	}
	
	
}
