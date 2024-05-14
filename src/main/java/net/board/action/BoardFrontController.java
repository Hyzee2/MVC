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
		// get, post ����� ���͵� �� ������ �����ϵ��� doProcess �����.
		// ��� jsp ���Ϸ� �̵��� �� �������ִ� ����
		// http://localhost:8080/MVC/BoardList.bo
		String RequestURI = request.getRequestURI(); // ��ü URL �ҷ�����
		String contextPath = request.getContextPath(); // URL �� MVC���� �ҷ�����(MVC�� �� ������Ʈ ��)
		String command = RequestURI.substring(contextPath.length()); // URL �߶󳻰� BoardList.bo �κи� ����
		
		ActionForward forward = null; // ������� ����, �����̷������� ������ �����ϴ� Ŭ���� ����
		Action action = null; // �������̽� Action���� �������ε��� ����� 
		
		if(command.equals("/BoardList.bo")) { // 1. db�� ���ٿ��� ���
			action = new BoardListAction(); // Action�� �������̽��ϴ� BoardListAction ��ü ����. �������ε� �����ϸ�  BoardListAction action = new BoardListAction();
			try {
				forward = action.execute(request, response); // action�� forward�� ����ְ�, �� �Ʒ��ʿ��� ���۹�İ� ��ο� �°� ����ȴ�. 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardWrite.bo")) { // 2. db �Ȱ��� �ٷ� ��� ���� ���  
			forward = new ActionForward(); // ActionForward Ŭ���� ���� ���� 
			forward.setRedirect(false); // ���۹���� ������ 
			forward.setPath("./board/qna_board_write.jsp"); // "qna_board_write.jsp"�� ��� ���� 
		}else if(command.equals("/BoardAddAction.bo")) { // 3. ������� �����̷��� ���� ���� ��� (1��, 2�� ��� ȥ��) 
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
		   forward.setRedirect(false); // ������ ��� 
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
		
		
		if(forward.isRedirect()) { // forward��� ���� �ȿ� isRedirect�� ��������Ƿ� true�̸� redirect���, false�̸� forward ���
			response.sendRedirect(forward.getPath()); // url����. ������������ �����ִ� ������ ����� 
		}else { 
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath()); // RequestDispatcher��� ���� �����
			dispatcher.forward(request, response); // forward ����ϸ� ������ ������� �̵� ����
			// url �������. ������������ �����ִ� ���� �״�� �°�
		}
		
	}

	
}
