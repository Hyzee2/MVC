package net.login.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class LoginDAO { // 데이터베이스를 연결 및 처리하는 클래스
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public LoginDAO() { // BoardDAO 생성하자마자 db연결 실행(커넥션 풀 방식)
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
	
	public int loginCheck(String id, String pw) throws SQLException {
		//회원이 1 / 관리자 0 / 비회원 -1
		Connection conn = null;
		String sql = "SELECT id ,pw FROM member";
		//데이터 등록하기
		PreparedStatement stmt = null;
		try{
		    Context init = new InitialContext();
		    DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
		    conn = ds.getConnection();
		    stmt = conn.prepareStatement(sql);
		    ResultSet rs = stmt.executeQuery();
		   
		    while(rs.next()) {
		    	if(id.equals("admin") && pw.equals("1234")) {//관리자
		        	return 0; //관리자
		        }
		        if(rs.getString("id").equals(id) && rs.getString("pw").equals(pw)) {
		            return 1; //회원
		        }
		    }
		}catch(Exception e){
		    System.out.println("로그인 조회 오류다~");
		    e.printStackTrace();
		}
		finally {
			stmt.close();
			conn.close();
		}
		
		return -1;//비회원
	}

}
