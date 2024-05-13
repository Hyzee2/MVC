package net.login.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class LoginDAO { // �����ͺ��̽��� ���� �� ó���ϴ� Ŭ����
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public LoginDAO() { // BoardDAO �������ڸ��� db���� ����(Ŀ�ؼ� Ǯ ���)
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
	
	public int loginCheck(String id, String pw) throws SQLException {
		//ȸ���� 1 / ������ 0 / ��ȸ�� -1
		Connection conn = null;
		String sql = "SELECT id ,pw FROM member";
		//������ ����ϱ�
		PreparedStatement stmt = null;
		try{
		    Context init = new InitialContext();
		    DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/MysqlDB");
		    conn = ds.getConnection();
		    stmt = conn.prepareStatement(sql);
		    ResultSet rs = stmt.executeQuery();
		   
		    while(rs.next()) {
		    	if(id.equals("admin") && pw.equals("1234")) {//������
		        	return 0; //������
		        }
		        if(rs.getString("id").equals(id) && rs.getString("pw").equals(pw)) {
		            return 1; //ȸ��
		        }
		    }
		}catch(Exception e){
		    System.out.println("�α��� ��ȸ ������~");
		    e.printStackTrace();
		}
		finally {
			stmt.close();
			conn.close();
		}
		
		return -1;//��ȸ��
	}

}
