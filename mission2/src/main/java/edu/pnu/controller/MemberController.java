package edu.pnu.controller;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;
import edu.pnu.service.MemberService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController //@Controller + @ResponseBody
public class MemberController {
	MemberService ms = new MemberService();
	
	
	//기본 생성자
	public MemberController() {
		ms = new MemberService();
	}
	
	
	//생성
	@GetMapping("/members")
	public List<MemberVO> getAllMember() {
		return ms.getAllMember();
	}
	//검색
	@GetMapping("/member")
	public MemberVO getMemberById(int id) {
		return ms.getMemberById(id);
	}
	
	//입력(Create - insert)
	@PostMapping("/member")
	public MemberVO addMember(int id, String pass, String name) {
		return ms.addMember(id, pass, name);
	}
	
	//수정(Update - update)
	@PutMapping("/member")
	public int updateMember(MemberVO memberVO) {
		return ms.updateMember(memberVO);
	}

	//삭제(Delete - delete)
	@DeleteMapping("/member")
	public int removeMember(int id) {
		return ms.removeMember(id);
	}
	
	//입력(Create - insert)
	@PostMapping("/memberJSON")
	public MemberVO addMemberJSON(@RequestBody MemberVO memberVO) {
		return ms.addMemberJSON(memberVO);
	}

	
	
	
	
	
	
	
}
