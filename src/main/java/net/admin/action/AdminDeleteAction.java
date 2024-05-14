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
		AdminDAO admindao=new AdminDAO(); // db ����
		
		boolean userDelete=false; 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		String userId = request.getParameter("userId");
		
		userDelete = admindao.adminDelete(userId);
		
		response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<script>alert('ȸ�� ������ �Ϸ�Ǿ����ϴ�.'); location.href='./AdminList.ad';</script>");
	    out.flush();
	
		request.setAttribute("userDelete", userDelete);
		System.out.println(userDelete);
		
		ActionForward forward= new ActionForward(); // ���۹�İ� ��θ� ������ �� �ִ� ActionForward Ŭ���� ��ü ���� 
	   	forward.setRedirect(true); // ������ ��� (url ���� ����. �Ѱ��� ���� �信���� ���� ������ ���� ����ϱ� ���Ͽ�)
   		forward.setPath("./AdminList.ad"); // �̵��� �� ������ ��� ���� 
		return forward; // ������ ���� forward ������ ��ȯ���� 		
   		
   	}
}
