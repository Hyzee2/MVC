package net.login.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class JoinDAO { // �����ͺ��̽��� ���� �� ó���ϴ� Ŭ����
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public JoinDAO() { // BoardDAO �������ڸ��� db���� ����(Ŀ�ؼ� Ǯ ���)
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
	
	
	// ȸ������ �� ȸ�� �߰��ϱ� 
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
			if(result==0)return false; // �����ϸ� ��ȯ���� �ƹ��͵� ����. 
			
			return true;
		}catch(Exception ex){
			System.out.println("ȸ�� ���� ��� ���� : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
	}
	

}
