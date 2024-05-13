package net.basket.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class BasketDAO { // 데이터베이스를 연결 및 처리하는 클래스
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public BasketDAO() { 
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
	
	// 장바구니 목록보기
	public List<BasketBean> basketList(String id) { // List형으로 반환 
		String basket_list_sql = "select * from Basket where id = ?";

		List<BasketBean> list = new ArrayList<>(); // 여러 개의 글 목록 값을 담기 위해 ArrayList형으로 list 변수 생성 

	
		try {
			pstmt = conn.prepareStatement(basket_list_sql);
			pstmt.setString(1, id); // where 조건절에서 첫번째 ? 설정
			
			rs = pstmt.executeQuery();

			while (rs.next()) { 
				BasketBean basket = new BasketBean();
				
				basket.setId(rs.getString("id"));
				basket.setItem_name(rs.getString("item_name"));
				basket.setItem_price(rs.getString("item_price"));
				basket.setItem_total(rs.getString("item_total"));
				
				list.add(basket); 
			}

			return list; // 결과 값들을 담은 list 반환 
		} catch (Exception ex) {
			System.out.println("getBasketList 에러 : " + ex);
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
	
	// 장바구니 추가하기
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
				System.out.println("상품이름은 "+item_name );
				
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
				System.out.println("basketAdd 성공");
				return true;
			}
		}catch(Exception ex){
			System.out.println("basketAdd 실패 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
	}
	
	// 장바구니 수정하기 
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
			System.out.println("basketModify 실패 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			}
		return false;
	}
	

}
