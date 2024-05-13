package net.login.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.BoardModifyView;

/**
 * Servlet implementation class BoardFrontController
 */
@WebServlet("/LoginFrontController")
public class LoginFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginFrontController() {
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
		
		if(command.equals("/Main.lo")){ // ���� ȭ������ �̵� 
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("./login/main.jsp");
		}else if(command.equals("/LoginForm.lo")){ // �α��� ȭ������ �̵� 
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("./login/loginForm.jsp");
		}else if(command.equals("/JoinForm.lo")){ // ȸ������ ȭ������ �̵� 
			   forward=new ActionForward();
			   forward.setRedirect(false);
			   forward.setPath("./login/joinForm.jsp");
		}else if(command.equals("/JoinUser.lo")){ // ȸ������ ��� �Ϸ� �� ���� ȭ������ �̵� 
			   action = new JoinAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
	 	}else if(command.equals("/LoginAction.lo")){ // �α��� �� id�� pw �´��� Ȯ���ϱ� 
			   action = new loginAction();
			   try{
				   forward=action.execute(request, response);
			   }catch(Exception e){
				   e.printStackTrace();
			   }
	 	}else if(command.equals("/LogoutAction.lo")){ 
	 		   action = new logoutAction();
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
