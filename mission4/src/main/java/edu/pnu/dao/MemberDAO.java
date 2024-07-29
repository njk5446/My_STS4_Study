package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.MemberDTO;


public class MemberDAO {
	Connection con;
	//DB의 데이터에 접근하기 위한 객체 : url, id, pw 필수3개
	public MemberDAO() {
		String url ="jdbc:mysql://localhost:3306/musthave";
		String id ="scott";
		String pwd = "tiger";
		try {
			con = DriverManager.getConnection(url,id,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<MemberDTO> getMemberDTO() {
		List<MemberDTO> dtoList = new ArrayList<>(); //DB에서 가져온 값들을 저장하는 객체 리스트
		String query = "select * from member";
		try { // Connection -> Statement -> ResultSet 순서
			PreparedStatement psmt = con.prepareStatement(query);
			ResultSet rs = psmt.executeQuery(); //쿼리문 실행
			//executeUpdate(): [int타입:바뀐 갯수] Insert, Update, Delete에 사용
			//executeQuery(): [ResultSet타입] Select (조회 시 사용)
			while(rs.next()) { //[boolean]next : 
				//한행씩 읽어오다가 비어있는 행이 나오면 반복을 종료
				MemberDTO dto = new MemberDTO(); // dto 객체 생성
				dto.setId(rs.getInt("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString("name"));
				dto.setRegidate(rs.getDate("regidate"));
				// DB에서 가져온 값들을 dto 객체에 저장
				dtoList.add(dto); //dto 객체를 리스트에 추가
				//rs.next()가 false일때까지 반복수행하면서 각 dto 객체를 리스트에 추가
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dtoList; // 저장된 dto 객체 리스트를 반환한다.
	}
	
	
	
}
