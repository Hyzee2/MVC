package net.basket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.basket.db.BasketBean;
import net.basket.db.BasketDAO;

public class BasketModifyAction implements Action { // ��ٱ��� ������ ���� DB�� ������Ʈ

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 request.setCharacterEncoding("UTF-8");
		 ActionForward forward = new ActionForward();
		 boolean result = false;
		 
		 HttpSession session = request.getSession();
		 String id = (String)session.getAttribute("id");
		 
		 String[] item_name = request.getParameterValues("item_name");
		 
		 String[] item_total = request.getParameterValues("item_total");
		 
		 int resultCnt = 0;
		 for(int i=0; i<item_name.length; i++, resultCnt++) {
			 try{
				 
				 BasketDAO basketdao=new BasketDAO();
				 BasketBean basketdata=new BasketBean();
				 basketdata.setId(id);
				 basketdata.setItem_name(item_name[i]);
				
				 basketdata.setItem_total(item_total[i]);
				 
				 result = basketdao.basketModify(basketdata);
				 System.out.println("i: "+i);
				 System.out.println(basketdata.toString());
				 
				 if(result==false){
			   		System.out.println("��ٱ��� ���� ����");
			   		return null;
			   	 }
			   	 System.out.println("��ٱ��� ���� �Ϸ�");
			   	 
			   	 
		   	 }catch(Exception ex){
		   			ex.printStackTrace();	 
			 }
		 }
		 
		 //������ ����, �ٸ��� ����
		 if(item_name.length != resultCnt ) {
			 System.out.println("��ٱ��� ���� ����");
	   		return null;
		 }
			 
		 
		 forward.setRedirect(true); // �����̷�Ʈ�� ���� ( url ����) 
	   	 forward.setPath("./BasketList.ba");  
	   	 return forward;
	}

}
