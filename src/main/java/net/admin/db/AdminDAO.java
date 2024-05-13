package net.admin.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class AdminDAO { // �����ͺ��̽��� ���� �� ó���ϴ� Ŭ����
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public AdminDAO() { // BoardDAO �������ڸ��� db���� ����(Ŀ�ؼ� Ǯ ���)
		try{
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
			
			if(conn == null)
				conn = ds.getConnection();
			
			System.out.println("����Ǿ����ϴ�.");
		}catch(Exception e){
			System.out.println("���ῡ �����Ͽ����ϴ�.");
			e.printStackTrace();
			return;
		}
	}
	
	

	// ȸ�� ��Ϻ���
	public List<String> adminList() { // List������ ��ȯ 
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

			return list; // ��� ������ ���� list ��ȯ 
			
		} catch (Exception ex) {
			System.out.println("getUserList ���� : " + ex);
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
	
	
	
	// ȸ�� �󼼺��� 
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
			System.out.println("getDatail����: "+admin.getName());
			return admin;
		}catch(Exception ex){
			System.out.println("getDetail ���� : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return null;
	}
	
	// ȸ�� ���� 
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
			
			System.out.println("userDelete ����");
			
			return true;
		}catch(Exception ex){
			System.out.println("userDelete ���� : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception ex) {}
		}
		
		return false;
	}
	
	

}
