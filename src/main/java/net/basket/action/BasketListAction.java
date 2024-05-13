package net.basket.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.basket.db.BasketBean;
import net.basket.db.BasketDAO;

public class BasketListAction implements Action { // ��ٱ��� ��� �����ִ� �׼� ��Ʈ�ѷ�

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		BasketDAO basketdao=new BasketDAO(); // db ����
		
		List<BasketBean> basketlist=new ArrayList<>(); 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		basketlist = basketdao.basketList(id);
	
		request.setAttribute("basketlist", basketlist);
		
		ActionForward forward= new ActionForward(); // ���۹�İ� ��θ� ������ �� �ִ� ActionForward Ŭ���� ��ü ���� 
	   	forward.setRedirect(false); // ������ ��� (url ���� ����. �Ѱ��� ���� �信���� ���� ������ ���� ����ϱ� ���Ͽ�)
   		forward.setPath("./basket/basket.jsp"); // �̵��� �� ������ ��� ���� 
   		return forward; // ������ ���� forward ������ ��ȯ���� 
	 
	}

}
