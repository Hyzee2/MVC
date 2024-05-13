package net.basket.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class BasketDAO { // �����ͺ��̽��� ���� �� ó���ϴ� Ŭ����
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public BasketDAO() { 
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
	
	// ��ٱ��� ��Ϻ���
	public List<BasketBean> basketList(String id) { // List������ ��ȯ 
		String basket_list_sql = "select * from Basket where id = ?";

		List<BasketBean> list = new ArrayList<>(); // ���� ���� �� ��� ���� ��� ���� ArrayList������ list ���� ���� 

	
		try {
			pstmt = conn.prepareStatement(basket_list_sql);
			pstmt.setString(1, id); // where ���������� ù��° ? ����
			
			rs = pstmt.executeQuery();

			while (rs.next()) { 
				BasketBean basket = new BasketBean();
				
				basket.setId(rs.getString("id"));
				basket.setItem_name(rs.getString("item_name"));
				basket.setItem_price(rs.getString("item_price"));
				basket.setItem_total(rs.getString("item_total"));
				
				list.add(basket); 
			}

			return list; // ��� ������ ���� list ��ȯ 
		} catch (Exception ex) {
			System.out.println("getBasketList ���� : " + ex);
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
	
	// ��ٱ��� �߰��ϱ�
	public boolean basketAdd(BasketBean basketdata){
		String item_name ="";
		String sql="";
		
		int result=0;

		
		try{
			pstmt=conn.prepareStatement("select * from Basket where id=? and item_name=?");
			pstmt.setString(1, basketdata.getId());
			pstmt.setString(2, basketdata.getItem_name());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				item_name = rs.getString("item_name");
				System.out.println("��ǰ�̸��� "+item_name );
				
				if(item_name != null) {
				
					sql = "update Basket set item_total=item_total+? where id=? and item_name=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basketdata.getItem_total());
					pstmt.setString(2, basketdata.getId());
					pstmt.setString(3, basketdata.getItem_name());
					result=pstmt.executeUpdate();
					pstmt.close();
				}
			
			}else{
				
				sql = "insert into Basket (id, item_name, item_price, item_total, basketDate) values(?,?,?,?,sysdate())";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basketdata.getId());
				pstmt.setString(2, basketdata.getItem_name());
				pstmt.setString(3, basketdata.getItem_price());
				pstmt.setString(4, basketdata.getItem_total());
				result=pstmt.executeUpdate();
			}
			
			
			if(result==0) { 
				
				return false;
			}else {
				System.out.println("basketAdd ����");
				return true;
			}
		}catch(Exception ex){
			System.out.println("basketAdd ���� : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
	}
	
	// ��ٱ��� �����ϱ� 
	public boolean basketModify(BasketBean basketdata) throws Exception{
		String sql="update Basket set item_total=? where id=? and item_name=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, basketdata.getItem_total());
			pstmt.setString(2, basketdata.getId());
			pstmt.setString(3, basketdata.getItem_name());
			pstmt.executeUpdate();
			return true;
		}catch(Exception ex){
			System.out.println("basketModify ���� : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			}
		return false;
	}
	

}
