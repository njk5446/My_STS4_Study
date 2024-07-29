package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import edu.pnu.domain.MemberVO;


public class MemberService { 
	//MemberService : 컨트롤러에게 데이터를 제공하는 서비스 객체
	//그냥 메서드를 만들어두고 컨트롤러에서 사용하도록 만들어둠


	private List<MemberVO> list = new ArrayList<>();
	//Mission1에서 컨트롤러에 정의했던 List<MemberVO> 변수를 여기에서 선언	
	public MemberService() {
		for (int i = 1; i <= 10; i++) {
			list.add(MemberVO.builder()
					.id(i).name("name" + i)
					.pass("pass" + i)
					.regidate(new Date()).build());
		}
		System.out.println("기본 생성자(List 호출)");
	}

	public List<MemberVO> getAllMember() {
		return list;
	}
	

	public MemberVO getMemberById(int id) {
		for (MemberVO m : list) {
			if (m.getId() == id)
				return m;
		}
		return null;
	}
	

	public MemberVO addMember(int id, String pass, String name) {
		if (getMemberById(id) != null) {
			System.out.println(id+
					"가 이미 존재합니다.");
			return null;
		}

		MemberVO mv = new MemberVO();
		mv.setId(id);
		mv.setPass(pass);
		mv.setName(name);
		mv.setRegidate(new Date());
		list.add(mv);
		return mv;
	}
	

	public int updateMember(MemberVO memberVO) {
		MemberVO m = getMemberById(memberVO.getId());
		if (m == null)
			return 0;
		m.setName(memberVO.getName());
		m.setPass(memberVO.getPass());
		return 1;
	}

	public int removeMember(int id) {
		try {
			list.remove(getMemberById(id));
			} catch(Exception e) {
				return 0;
		}
		return 1;
	}

	public MemberVO addMemberJSON(MemberVO memberVO) {
	if (getMemberById(memberVO.getId()) != null) {
	System.out.println(memberVO.getId() + "가 이미 존재합니다.");
	return null;
	}
	memberVO.setRegidate(new Date());
	list.add(memberVO);
	return memberVO;
	}

	
	
}
