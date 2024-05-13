package net.admin.action;

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
@WebServlet("/AdminFrontController")
public class AdminFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFrontController() {
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
		
		if(command.equals("/AdminList.ad")) { // ��ü ȸ�� ����Ʈ �ҷ����� 
			action = new AdminListAction();
		    try{
			   forward=action.execute(request, response);
		    }catch(Exception e){
			   e.printStackTrace();
		    }
		}else if(command.equals("/AdminDetail.ad")){ // ȸ�� �󼼺��� 
		   action = new AdminDetailAction();
		   try{
			   forward=action.execute(request, response);
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	    }else if(command.equals("/AdminDelete.ad")){ // ȸ�� �����ϱ�
		   action = new AdminDeleteAction();
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
