package net.board.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

public class BoardDAO { // 데이터베이스를 연결 및 처리하는 클래스
	static Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public BoardDAO() { // BoardDAO 생성하자마자 db연결 실행(커넥션 풀 방식)
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
	
	// 글의 개수 구하기
	public int getListCount() {
		int x = 0; // 초기화 

		try {
			pstmt = conn.prepareStatement("select count(*) from board2");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1); // count(*)행 가져오기 
			}
		} catch (Exception ex) {
			System.out.println("getListCount 에러: " + ex);
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
		return x; //count(*)행 반환
	}

	// 글 목록보기
	public List getBoardList(int page, int limit) { // List형으로 반환 
		String board_list_sql = "select * from " + "(select row_number() over() rnum,BOARD_NUM,BOARD_NAME,BOARD_SUBJECT,"
				+ "BOARD_CONTENT,BOARD_FILE,BOARD_RE_REF,BOARD_RE_LEV,"
				+ "BOARD_RE_SEQ,BOARD_READCOUNT,BOARD_DATE from "
				+ "(select * from board2 order by BOARD_RE_REF desc,BOARD_RE_SEQ asc) AS s)AS u " + "where rnum>=? and rnum<=?";		// 오라클->코끼리 서브쿼리 부분에 별칭 추가

		List list = new ArrayList(); // 여러 개의 글 목록 값을 담기 위해 ArrayList형으로 list 변수 생성 

		int startrow = (page - 1) * 10 + 1; // 읽기 시작할 row번호
		int endrow = startrow + limit - 1; // 읽기 마지막 row번호.
		try {
			pstmt = conn.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow); // where 조건절에서 첫번째 ? 설정
			pstmt.setInt(2, endrow); // where 조건절에서 두번째 ? 설정
			rs = pstmt.executeQuery();

			while (rs.next()) { // select해서 가지고 오는 컬럼 값들이 많다면 BoardBean 객체를 생성해서 넣어준다음, 해당 객체를 list에 add 시켜준다. 
				BoardBean board = new BoardBean();
				
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				
				list.add(board); // select 문으로 가지고 온 한 줄의 데이터들을 board에 담아서 list에 add 해준다. 
			}

			return list; // 결과 값들을 담은 list 반환 
		} catch (Exception ex) {
			System.out.println("getBoardList 에러 : " + ex);
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
	
	// 작성한 글 추가하기 
	public boolean boardInsert(BoardBean board){
		int num =0;
		String sql="";
		
		int result=0;
		
		try{
			pstmt=conn.prepareStatement("select max(board_num) from board2");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num =rs.getInt(1)+1; // 가장 마지막 번호 +1
			else
				num=1;
			
			sql="insert into board2 (BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,";
			sql+="BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF,"+
				"BOARD_RE_LEV,BOARD_RE_SEQ,BOARD_READCOUNT,"+
				"BOARD_DATE) values(?,?,?,?,?,?,?,?,?,?,now())";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_NAME());
			pstmt.setString(3, board.getBOARD_PASS());
			pstmt.setString(4, board.getBOARD_SUBJECT());
			pstmt.setString(5, board.getBOARD_CONTENT());
			pstmt.setString(6, board.getBOARD_FILE());
			pstmt.setInt(7, num);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 0);
			pstmt.setInt(10, 0);
			
			result=pstmt.executeUpdate();
			if(result==0)return false; // 성공하면 반환값이 아무것도 없다. 
			
			return true;
		}catch(Exception ex){
			System.out.println("boardInsert 실패 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
	}
	
	// 게시글 수정하기 
	public boolean boardModify(BoardBean modifyboard) throws Exception{
		String sql="update board2 set BOARD_SUBJECT=?,BOARD_CONTENT=? where BOARD_NUM=?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, modifyboard.getBOARD_SUBJECT());
			pstmt.setString(2, modifyboard.getBOARD_CONTENT());
			pstmt.setInt(3, modifyboard.getBOARD_NUM());
			pstmt.executeUpdate();
			return true;
		}catch(Exception ex){
			System.out.println("boardModify 실패 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			}
		return false;
	}
	
	// 게시글 작성자 확인하기 
	public boolean isBoardWriter(int num, String pass) {
		String board_sql="select * from board2 where BOARD_NUM=?";
		
		try{
			pstmt=conn.prepareStatement(board_sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			rs.next();
			
			if(pass.equals(rs.getString("BOARD_PASS"))){
				return true;
			}
		}catch(SQLException ex){
			System.out.println("isBoardWriter 실패 : " + ex);
		}
		return false;
	}
	
	// 조회수 증가하기 
	public void setReadCountUpdate(int num) throws Exception{ // 게시글 번호를 매개변수로 받는다 
		String sql="update board2 set BOARD_READCOUNT = "+
			"BOARD_READCOUNT+1 where BOARD_NUM = "+num;
		
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(SQLException ex){
			System.out.println("setReadCountUpdate 실패 : "+ex);
		}
	}
	
	// 게시글 상세보기 
	public BoardBean getDetail(int num) throws Exception{ // 게시글 번호를 매개변수로 받는다 
		BoardBean board = null;
		try{
			pstmt = conn.prepareStatement(
					"select * from board2 where BOARD_NUM = ?");
			pstmt.setInt(1, num);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			}
			return board;
		}catch(Exception ex){
			System.out.println("getDetail 실패 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return null;
	}
	
	// 게시글 삭제 
	public boolean boardDelete(int num) {
		
		String board_delete_sql="delete from board2 where BOARD_num=?";
		
		int result=0;
		
		try{
			pstmt=conn.prepareStatement(board_delete_sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("boardDelete 실패 : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception ex) {}
		}
		
		return false;
	}
	
	// 게시물 답변 달기
	public int boardReply(BoardBean board){
		String board_max_sql="select max(board_num) from board2";
		String sql="";
		int num=0;
		int result=0;
		
		int re_ref=board.getBOARD_RE_REF();
		int re_lev=board.getBOARD_RE_LEV();
		int re_seq=board.getBOARD_RE_SEQ();
		
		try{
			pstmt=conn.prepareStatement(board_max_sql);
			rs = pstmt.executeQuery();
			if(rs.next())num =rs.getInt(1)+1;
			else num=1;
			
			sql="update board2 set BOARD_RE_SEQ=BOARD_RE_SEQ+1 where BOARD_RE_REF=? ";
			sql+="and BOARD_RE_SEQ>?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,re_ref);
			pstmt.setInt(2,re_seq);
			result=pstmt.executeUpdate();
			
			re_seq = re_seq + 1;
			re_lev = re_lev+1;
			
			sql="insert into board2 (BOARD_NUM,BOARD_NAME,BOARD_PASS,BOARD_SUBJECT,";
			sql+="BOARD_CONTENT, BOARD_FILE,BOARD_RE_REF,BOARD_RE_LEV,BOARD_RE_SEQ,";
			sql+="BOARD_READCOUNT,BOARD_DATE) values(?,?,?,?,?,?,?,?,?,?,curdate())";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_NAME());
			pstmt.setString(3, board.getBOARD_PASS());
			pstmt.setString(4, board.getBOARD_SUBJECT());
			pstmt.setString(5, board.getBOARD_CONTENT());
			pstmt.setString(6, ""); 
			pstmt.setInt(7, re_ref);
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			pstmt.setInt(10, 0);
			pstmt.executeUpdate();
			return num; // 게시글 번호를 반환
		}catch(SQLException ex){
			System.out.println("boardReply 실패 : "+ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return 0;
	}

}
