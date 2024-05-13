package net.basket.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.basket.action.ActionForward;

/**
 * Servlet implementation class BoardFrontController
 */
@WebServlet("/BasketFrontController")
public class BasketFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BasketFrontController() {
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
		
		if(command.equals("/BasketAdd.ba")) { // 장바구니 추가하기  
			action = new BasketAddAction(); 
			try {
				forward = action.execute(request, response); 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Item.ba")) { // 아이템 리스트 보여주기 
			forward=new ActionForward();
		    forward.setRedirect(false); // 포워드 방식 
		    forward.setPath("./basket/item.jsp");
			
		}else if(command.equals("/BasketList.ba")) { // 장바구니 리스트 보여주기 
			action = new BasketListAction(); 
			try {
				forward = action.execute(request, response); 
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BasketModify.ba")) { // 장바구니 내역 수정하기
			action = new BasketModifyAction(); 
			try {
				forward = action.execute(request, response); 
			}catch(Exception e) {
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
