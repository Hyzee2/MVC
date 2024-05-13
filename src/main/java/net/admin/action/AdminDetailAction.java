package net.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.admin.db.AdminBean;
import net.admin.db.AdminDAO;

public class AdminDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		AdminDAO admindao=new AdminDAO(); // db ����
		
		AdminBean userDetail=new AdminBean(); 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		String userId = request.getParameter("userId");
		
		userDetail = admindao.adminDetail(userId);
	
		request.setAttribute("userDetail", userDetail);
		
		ActionForward forward= new ActionForward(); // ���۹�İ� ��θ� ������ �� �ִ� ActionForward Ŭ���� ��ü ���� 
	   	forward.setRedirect(false); // ������ ��� (url ���� ����. �Ѱ��� ���� �信���� ���� ������ ���� ����ϱ� ���Ͽ�)
   		forward.setPath("./admin/adminDetail.jsp"); // �̵��� �� ������ ��� ���� 
   		return forward; // ������ ���� forward ������ ��ȯ���� 		
		
		
	}

}
