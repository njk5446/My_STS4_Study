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
	Connection con; //DB에 접근하기 위해선 Connection 내장 객체가 필수적으로 쓰인다(그냥 외우자)
	//DB의 데이터에 접근하기 위한 객체 : url, id, pw 필수3개
	public MemberDAO() { //
		String url ="jdbc:mysql://localhost:3306/musthave"; //해당 데이터베이스로 접속  
		String id ="scott"; // mySql 아이디
		String pwd = "tiger"; // mySql 비밀번호
		try {
			con = DriverManager.getConnection(url,id,pwd);
			// DriverManager: DB에 연결하도록 적절한 드라이버를 찾아서 연결한다.
			// DriverManager.getConnection(url,id,pwd) 한 세트라고 보면됨..
		} catch (SQLException e) { //예외 발생시 처리
			e.printStackTrace();
		}
		System.out.println("DAO");
		
	}
	
	public List<MemberDTO> getMemberDTO() {
		List<MemberDTO> dtoList = new ArrayList<>(); //DB에서 가져온 값들을 저장하는 객체 리스트를 생성
		String query = "select * from member"; // DB에서 호출할 쿼리문을 작성
		try { // 순서: Connection -> Statement -> ResultSet 
			PreparedStatement psmt = con.prepareStatement(query);
			//PreparedStatement: SQL 쿼리를 실행하도록 하는 내장객체
			//con: 연결객체인 con을 통해 쿼리문을 PreparedStatement 객체에 삽입 (SQL 쿼리 실행)
			ResultSet rs = psmt.executeQuery(); //실행한 SQL 쿼리의 결과를 저장하는 객체 
			//executeUpdate(): [int타입:바뀐 갯수] Insert, Update, Delete에 사용
			//executeQuery(): [ResultSet타입]의 SELECT 쿼리를 실행하는데 사용
			while(rs.next()) { //[boolean]next : 
				//한행씩 읽어오다가 비어있는 행이 나오면 반복을 종료
				MemberDTO dto = new MemberDTO(); // dto 객체 생성
				dto.setId(rs.getInt("id")); // 실행한 쿼리의 결과 rs의 id를 빼온다
				dto.setPass(rs.getString("pass")); // 실행한 쿼리의 결과 rs의 pass를 빼온다
				dto.setName(rs.getString("name")); // 실행한 쿼리의 결과 rs의 name를 빼온다
				dto.setRegidate(rs.getDate("regidate")); // 실행한 쿼리의 결과 rs의 regidate를 빼온다
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
