package net.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.admin.db.AdminBean;
import net.admin.db.AdminDAO;

public class AdminListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		AdminDAO admindao=new AdminDAO(); // db ����
		
		List<String> userList=new ArrayList<>(); 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		userList = admindao.adminList();
	
		request.setAttribute("userList", userList);
		
		ActionForward forward= new ActionForward(); // ���۹�İ� ��θ� ������ �� �ִ� ActionForward Ŭ���� ��ü ���� 
	   	forward.setRedirect(false); // ������ ��� (url ���� ����. �Ѱ��� ���� �信���� ���� ������ ���� ����ϱ� ���Ͽ�)
   		forward.setPath("./admin/adminList.jsp"); // �̵��� �� ������ ��� ���� 
   		return forward; // ������ ���� forward ������ ��ȯ���� 		
		
	}

}
