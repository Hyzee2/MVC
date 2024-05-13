package net.basket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.basket.db.BasketBean;
import net.basket.db.BasketDAO;
import net.basket.action.ActionForward;

public class BasketAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		BasketDAO basketdao=new BasketDAO();
		
	   	BasketBean basketdata=new BasketBean();
	   	
	   	ActionForward forward=new ActionForward();
   		
   		boolean result=false;
   		
   		try{
   			
   			basketdata.setItem_name(request.getParameter("item_name"));
   			basketdata.setItem_price(request.getParameter("item_price"));
   			basketdata.setItem_total(request.getParameter("item_total"));
   			
   			HttpSession session = request.getSession();
   			basketdata.setId((String)session.getAttribute("id"));
	   		
	   		result=basketdao.basketAdd(basketdata);
	   		
	   		if(result==false){
	   			System.out.println("장바구니 등록 실패");
	   			return null;
	   		}
	   		System.out.println("장바구니 등록 완료");
	   		
	   		forward.setRedirect(true); // 리다이렉트 방식으로 전송
	   		forward.setPath("./BasketList.ba");
	   		return forward;
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return null;
		
	}

}
