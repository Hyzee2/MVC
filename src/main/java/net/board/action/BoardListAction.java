package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import net.board.db.BoardDAO;

public class BoardListAction implements Action{ // �����ͺ��̽��� �̿��ϴ� �׼� ��Ʈ�ѷ�(DB�ΰ��� ����� �� ������!)

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao=new BoardDAO(); // db ����
		
		List boardlist=new ArrayList(); // db���� ������ ���� board �������� ArrayList ���·� ��Ƽ� �������� ���� boardlist ��ü ���� 
		
	  	int page=1; // �ʱ�ȭ ���� 
		int limit=10;
		
		if(request.getParameter("page")!=null){ // ���� �������� "qna_board_list"���� �޾ƿ� page�� ���� ������, 
			page=Integer.parseInt(request.getParameter("page")); // int������ �Ľ� 
		}
		
		int listcount=boarddao.getListCount(); // listcount ������ db�����ؼ� ������ ���� ������ ���� 
		boardlist = boarddao.getBoardList(page,limit); // List�� boardlist ������ db�����ؼ� ������ ���� ������ ����, page�� limit ���ǿ� �´� �۸�ϵ��� list������ �ҷ��´�.   		
		
		// ������ ó�� ���� 
   		int maxpage=(int)((double)listcount/limit+0.95); 
   		
   		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
   		
   		int endpage = maxpage;
   		
   		if (endpage>startpage+10-1) endpage=startpage+10-1;
   		// ������ ó�� �� 
   		
   		// "qna-board-list.jsp"���� �ʿ��� ������ request�� �־��� 
   		request.setAttribute("page", page);		  
   		request.setAttribute("maxpage", maxpage); 
   		request.setAttribute("startpage", startpage); 
   		request.setAttribute("endpage", endpage);    
		request.setAttribute("listcount",listcount); 
		request.setAttribute("boardlist", boardlist);
		
		ActionForward forward= new ActionForward(); // ���۹�İ� ��θ� ������ �� �ִ� ActionForward Ŭ���� ��ü ���� 
	   	forward.setRedirect(false); // ������ ��� (url ���� ����. �Ѱ��� ���� �信���� ���� ������ ���� ����ϱ� ���Ͽ�)
   		forward.setPath("./board/qna_board_list.jsp"); // �̵��� �� ������ ��� ���� 
   		return forward; // ������ ���� forward ������ ��ȯ���� 
	 
	}

}
