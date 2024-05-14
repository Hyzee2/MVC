package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardFrontController
 */
@WebServlet("/BoardFrontController")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
		// get, post 방식을 들어와도 한 군데서 관리하도록 doProcess 만든다.
		// 어느 jsp 파일로 이동할 지 결정해주는 역할
		// http://localhost:8080/MVC/BoardList.bo
		String RequestURI = request.getRequestURI(); // 전체 URL 불러오기
		String contextPath = request.getContextPath(); // URL 중 MVC까지 불러오기(MVC는 웹 프로젝트 명)
		String command = RequestURI.substring(contextPath.length()); // URL 잘라내고 BoardList.bo 부분만 추출
		
		ActionForward forward = null; // 포워드로 할지, 리다이랙션으로 보낼지 결정하는 클래스 변수
		Action action = null; // 인터페이스 Action으로 동적바인딩을 사용함 
		
		if(command.equals("/BoardList.bo")) { // 1. db를 갔다오는 방식
			action = new BoardListAction(); // Action을 인터페이스하는 BoardListAction 객체 생성. 동적바인딩 사용안하면  BoardListAction action = new BoardListAction();
			try {
				forward = action.execute(request, response); // action을 forward에 담아주고, 맨 아래쪽에서 전송방식과 경로에 맞게 실행된다. 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardWrite.bo")) { // 2. db 안가고 바로 뷰로 가는 방식  
			forward = new ActionForward(); // ActionForward 클래스 변수 생성 
			forward.setRedirect(false); // 전송방식은 포워드 
			forward.setPath("./board/qna_board_write.jsp"); // "qna_board_write.jsp"로 경로 설정 
		}else if(command.equals("/BoardAddAction.bo")) { // 3. 포워드와 리다이렉션 같이 쓰는 방식 (1번, 2번 방식 혼합) 
			action = new BoardAddAction();
			try {
				forward = action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardModifyAction.bo")){
		   action = new BoardModifyAction();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		}else if(command.equals("/BoardDetailAction.bo")){
		   action = new BoardDetailAction();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	    }else if(command.equals("/BoardModify.bo")){
		   action = new BoardModifyView();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
 	    }else if(command.equals("/BoardDelete.bo")){
		   forward=new ActionForward();
		   forward.setRedirect(false); // 포워드 방식 
		   forward.setPath("./board/qna_board_delete.jsp");
		}else if(command.equals("/BoardDeleteAction.bo")){
		   action = new BoardDeleteAction();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	    }else if(command.equals("/BoardReplyAction.bo")){
		   action = new BoardReplyView();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	    }else if(command.equals("/BoardReplyView.bo")){
		   action = new BoardReplyAction();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	    }
		
		
		if(forward.isRedirect()) { // forward라는 변수 안에 isRedirect가 담겨있으므로 true이면 redirect방식, false이면 forward 방식
			response.sendRedirect(forward.getPath()); // url변경. 이전페이지가 갖고있던 권한은 사라짐 
		}else { 
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath()); // RequestDispatcher라는 변수 만들기
			dispatcher.forward(request, response); // forward 사용하면 포워드 방식으로 이동 가능
			// url 변경없이. 이전페이지가 갖고있던 권한 그대로 승계
		}
		
	}

	
}
