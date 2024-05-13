package net.admin.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class AdminDAO { // 데이터베이스를 연결 및 처리하는 클래스
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public AdminDAO() { // BoardDAO 생성하자마자 db연결 실행(커넥션 풀 방식)
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
	
	

	// 회원 목록보기
	public List<String> adminList() { // List형으로 반환 
		String user_id = "";
		String sql = "select id from member";

		List<String> list = new ArrayList<>(); 
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			while (rs.next()) { 
				
				user_id = rs.getString("id");
				
				list.add(user_id); 
			}

			return list; // 결과 값들을 담은 list 반환 
			
		} catch (Exception ex) {
			System.out.println("getUserList 에러 : " + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
		}
		return list;			
	}
	
	
	
	// 회원 상세보기 
	public AdminBean adminDetail(String userId) throws Exception{
		AdminBean admin = null;
		System.out.println(userId);
		try{
			pstmt = conn.prepareStatement(
					"select id, mail, name, IDnum, birth, hobby, introduction from member where id = ?");
			pstmt.setString(1, userId);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				admin = new AdminBean();
				
				admin.setId(rs.getString("id"));
				admin.setMail(rs.getString("mail"));
				admin.setName(rs.getString("name"));
				admin.setIDnum(rs.getString("IDnum"));
				admin.setBirth(rs.getString("birth"));
				admin.setHobby(rs.getString("hobby"));
				admin.setIntroduction(rs.getString("introduction"));
				
			}
			System.out.println("getDatail성공: "+admin.getName());
			return admin;
		}catch(Exception ex){
			System.out.println("getDetail 실패 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return null;
	}
	
	// 회원 삭제 
	public boolean adminDelete(String userId) {
		
		String sql="delete from member where id=?";
		String sql2="delete from basket where id=?";
		
		int result=0;
		
		try{
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
			
			if(result==0) {
				return false;
			}
			
			pstmt=conn.prepareStatement(sql2);
			
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
			
			if(result==0) {
				return false;
			}
			
			System.out.println("userDelete 성공");
			
			return true;
		}catch(Exception ex){
			System.out.println("userDelete 실패 : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception ex) {}
		}
		
		return false;
	}
	
	

}
