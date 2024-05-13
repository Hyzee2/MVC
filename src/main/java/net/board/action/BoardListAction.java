package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import net.board.db.BoardDAO;

public class BoardListAction implements Action{ // 데이터베이스를 이용하는 액션 컨트롤러(DB로가서 어떤일을 할 것인지!)

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao=new BoardDAO(); // db 연결
		
		List boardlist=new ArrayList(); // db에서 가지고 오는 board 정보들을 ArrayList 형태로 담아서 가져오기 위해 boardlist 객체 생성 
		
	  	int page=1; // 초기화 과정 
		int limit=10;
		
		if(request.getParameter("page")!=null){ // 이전 페이지인 "qna_board_list"에서 받아온 page의 값이 있으면, 
			page=Integer.parseInt(request.getParameter("page")); // int형으로 파싱 
		}
		
		int listcount=boarddao.getListCount(); // listcount 변수는 db연결해서 쿼리문 실행 값으로 설정 
		boardlist = boarddao.getBoardList(page,limit); // List형 boardlist 변수는 db연결해서 쿼리문 실행 값으로 설정, page와 limit 조건에 맞는 글목록들을 list형으로 불러온다.   		
		
		// 페이지 처리 시작 
   		int maxpage=(int)((double)listcount/limit+0.95); 
   		
   		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
   		
   		int endpage = maxpage;
   		
   		if (endpage>startpage+10-1) endpage=startpage+10-1;
   		// 페이지 처리 끝 
   		
   		// "qna-board-list.jsp"에서 필요한 값들을 request에 넣어줌 
   		request.setAttribute("page", page);		  
   		request.setAttribute("maxpage", maxpage); 
   		request.setAttribute("startpage", startpage); 
   		request.setAttribute("endpage", endpage);    
		request.setAttribute("listcount",listcount); 
		request.setAttribute("boardlist", boardlist);
		
		ActionForward forward= new ActionForward(); // 전송방식과 경로를 설정할 수 있는 ActionForward 클래스 객체 생성 
	   	forward.setRedirect(false); // 포워드 방식 (url 변동 없음. 넘겨준 값을 뷰에서도 같은 권한을 갖고 사용하기 위하여)
   		forward.setPath("./board/qna_board_list.jsp"); // 이동할 뷰 페이지 경로 설정 
   		return forward; // 정보를 담은 forward 변수를 반환해줌 
	 
	}

}
