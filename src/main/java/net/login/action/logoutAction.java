package net.login.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.*;
import net.login.db.LoginBean;
import net.login.db.LoginDAO;

 public class logoutAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	 	throws Exception{
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		boolean result = false;
 
		HttpSession session = request.getSession();
		session.invalidate(); 
   		
		response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<script>alert('�α׾ƿ��� �Ϸ�Ǿ����ϴ�.'); location.href='./Main.lo';</script>");
	    out.flush();
		
   		System.out.println("�α׾ƿ� �Ϸ�");
   		
   		forward.setRedirect(true);
   		forward.setPath("./Main.lo");
   		return forward;
		
	 }
}