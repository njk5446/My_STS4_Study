package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;


@Service
public class MemberService {
	
	MemberDAO dao; // MemberDAO 메서드 호출하기 위해 MemberDAO 타입의 참조변수를 선언
	
	public MemberService() {
		dao = new MemberDAO();
		System.out.println("MemberService 호출");
		
	}

	
	public List<MemberDTO> serviceAllmember() { // List를 반환하기 위해서 타입은 List<MemberDTO> 
		return dao.getMemberDTO();  // MemberDAO의 메서드 호출
	}
	
	
}
