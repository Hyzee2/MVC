package net.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.admin.db.AdminBean;
import net.admin.db.AdminDAO;


public class AdminDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		AdminDAO admindao=new AdminDAO(); // db 연결
		
		boolean userDelete=false; 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		String userId = request.getParameter("userId");
		
		userDelete = admindao.adminDelete(userId);
		
		response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<script>alert('회원 삭제가 완료되었습니다.'); location.href='./AdminList.ad';</script>");
	    out.flush();
	
		request.setAttribute("userDelete", userDelete);
		System.out.println(userDelete);
		
		ActionForward forward= new ActionForward(); // 전송방식과 경로를 설정할 수 있는 ActionForward 클래스 객체 생성 
	   	forward.setRedirect(true); // 포워드 방식 (url 변동 없음. 넘겨준 값을 뷰에서도 같은 권한을 갖고 사용하기 위하여)
   		forward.setPath("./AdminList.ad"); // 이동할 뷰 페이지 경로 설정 
		return forward; // 정보를 담은 forward 변수를 반환해줌 		
   		
   	}
}
