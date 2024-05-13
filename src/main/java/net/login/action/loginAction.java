package net.login.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.board.db.*;
import net.login.db.LoginBean;
import net.login.db.LoginDAO;

 public class loginAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) 
	 	throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 ActionForward forward = new ActionForward();
		 boolean result = false;
		 
		 String id = request.getParameter("id");
		 String pw = request.getParameter("pw");
		 
		 LoginDAO logindao=new LoginDAO();
		
		 
		 try {
			 int usercheck=logindao.loginCheck(id, pw); // �α��� ȸ�� ���� Ȯ��  
			 
			 if(usercheck == -1){// �α����� ȸ���� ��ġ���� �ʴ� ��� 
			   		response.setContentType("text/html;charset=UTF-8");
			   		PrintWriter out=response.getWriter();
			   		out.println("<script>");
			   		out.println("alert('�߸��� ���̵�� ��� �Դϴ�.');");
			   		out.println("location.href='./LoginForm.lo';");
			   		out.println("</script>");
			   		out.close();
			   		return null;
			 }else if(usercheck == 0) {
				 System.out.println("�����ڷ� �α��� �Ϸ�");
				 HttpSession session = request.getSession();
				 session.setAttribute("id", id);
				 
				 forward.setRedirect(true); // �����̷�Ʈ�� ���� ( url ����) 
				 forward.setPath("./AdminList.ad?id="+id);
				 return forward;
			 }else if(usercheck == 1) {
				 System.out.println("�Ϲ� ȸ�� �α��� �Ϸ�");
				 HttpSession session = request.getSession();
				 session.setAttribute("id", id);
				 
				 forward.setRedirect(true); // �����̷�Ʈ�� ���� ( url ����) 
				 forward.setPath("./Main.lo?id="+id);
				 return forward;
			 }
			 }catch(Exception ex) {
				 ex.printStackTrace();
			 }
		 return null;
		 
	 }
}