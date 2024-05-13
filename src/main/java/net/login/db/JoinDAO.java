package net.login.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class JoinDAO { // 데이터베이스를 연결 및 처리하는 클래스
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public JoinDAO() { // BoardDAO 생성하자마자 db연결 실행(커넥션 풀 방식)
		try{
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
			
			if(conn == null)
				conn = ds.getConnection();
			
			System.out.println("연결되었습니다.");
		}catch(Exception e){
			System.out.println("연결에 실패하였습니다.");
			e.printStackTrace();
			return;
		}
	}
	
	
	// 회원가입 시 회원 추가하기 
	public boolean insertUser(JoinBean joinUser){
		
		String[] birth = joinUser.getBirth();
		String b = "";
		for(int i=0; i<birth.length; i++){
			b += birth[i]+".";
		}
		
		String[] hobby = joinUser.getHobby();
		String h = "";
		for(int i=0; i<hobby.length; i++){
			h += hobby[i]+", ";
		}
		
		
		String sql="";
		
		int result=0;
		
		try{
			sql="Insert into member values (?,?,?,?,?,?,?,?,?,curdate())";		
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, joinUser.getId());
			pstmt.setString(2, joinUser.getPw());
			pstmt.setString(3, joinUser.getSecondPw());
			pstmt.setString(4, joinUser.getMail());
			pstmt.setString(5, joinUser.getName());
			pstmt.setString(6, joinUser.getIDnum());
			pstmt.setString(7, b);
			pstmt.setString(8, h);
			pstmt.setString(9, joinUser.getIntroduction());
			
			result=pstmt.executeUpdate();
			if(result==0)return false; // 성공하면 반환값이 아무것도 없다. 
			
			return true;
		}catch(Exception ex){
			System.out.println("회원 정보 등록 실패 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
	}
	

}
