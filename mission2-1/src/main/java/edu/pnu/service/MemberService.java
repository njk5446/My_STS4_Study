package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.MemberVO;

public class MemberService {
	
	private List<MemberVO> list = new ArrayList<>();
	
	public MemberService() {
		for (int i = 1; i <= 10; i++) {
			list.add(MemberVO.builder()
					.id(i)
					.name("name" + i)
					.pass("pass" + i)
					.build());
		}
		System.out.println("기본 생성자로 객체 10개를 담은 리스트 생성");
	}
	
	//전체 리스트 출력
	public List<MemberVO> getAllMember(){
		return list;
	}
	
	//id로 검색
	public MemberVO searchMember(int id) {
		for (MemberVO m : list) {
			if(m.getId() == id) {
				return m;
			}
		}
	}
	
}
