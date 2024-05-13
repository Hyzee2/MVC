package net.basket.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.basket.db.BasketBean;
import net.basket.db.BasketDAO;

public class BasketListAction implements Action { // 장바구니 목록 보여주는 액션 컨트롤러

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		BasketDAO basketdao=new BasketDAO(); // db 연결
		
		List<BasketBean> basketlist=new ArrayList<>(); 
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		basketlist = basketdao.basketList(id);
	
		request.setAttribute("basketlist", basketlist);
		
		ActionForward forward= new ActionForward(); // 전송방식과 경로를 설정할 수 있는 ActionForward 클래스 객체 생성 
	   	forward.setRedirect(false); // 포워드 방식 (url 변동 없음. 넘겨준 값을 뷰에서도 같은 권한을 갖고 사용하기 위하여)
   		forward.setPath("./basket/basket.jsp"); // 이동할 뷰 페이지 경로 설정 
   		return forward; // 정보를 담은 forward 변수를 반환해줌 
	 
	}

}
