package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.BoardBean;

 public class BoardDetailAction implements Action { // 게시글 상세보기 
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		request.setCharacterEncoding("euc-kr");
   		
		BoardDAO boarddao=new BoardDAO();
	   	BoardBean boarddata=new BoardBean();
	   	
		int num=Integer.parseInt(request.getParameter("num")); // request로 전송받은 num 값을 num변수에 넣어준다 
		boarddao.setReadCountUpdate(num); // 조회수 증가 update
	   	boarddata=boarddao.getDetail(num); // 상세정보 가져오기 select  
	   	
	   	if(boarddata==null){
	   		System.out.println("상세보기 실패");
	   		return null;
	   	}
	   	System.out.println("상세보기 성공");
	   	
	   	request.setAttribute("boarddata", boarddata);
	   	ActionForward forward = new ActionForward();
	   	forward.setRedirect(false); // 포워드로 전송 
   		forward.setPath("./board/qna_board_view.jsp");
   		return forward;

	 }
}