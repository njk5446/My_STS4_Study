package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;


@Service
public class MemberService {
	
	MemberDAO dao;
	
	public MemberService() {
		dao=new MemberDAO();
	}

	
	public List<MemberDTO> serviceAllmember() {
		return dao.getMemberDTO();
	}
	
	
}
