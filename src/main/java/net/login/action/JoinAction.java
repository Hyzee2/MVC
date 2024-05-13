package net.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.login.db.JoinBean;
import net.login.db.JoinDAO;


// 회원정보 insert하는 액션 컨트롤러 
public class JoinAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		JoinDAO joindao=new JoinDAO();
		
	   	JoinBean joindata=new JoinBean();
	   	
	   	ActionForward forward=new ActionForward();
   		
   		boolean result=false;
   		
   		try{
   			
   			joindata.setId(request.getParameter("id"));
   			joindata.setPw(request.getParameter("pw"));
   			joindata.setSecondPw(request.getParameter("secondPw"));
   			joindata.setMail(request.getParameter("mail"));
   			joindata.setName(request.getParameter("name"));
   			joindata.setIDnum(request.getParameter("IDnum"));
   			joindata.setBirth(request.getParameterValues("birth"));
   			joindata.setHobby(request.getParameterValues("hobby"));
   			joindata.setIntroduction(request.getParameter("introduction"));
   			
	   		result=joindao.insertUser(joindata);
	   		
	   		if(result==false){
	   			System.out.println("insert 실패");
	   			return null;
	   		}
	   		System.out.println("insert 완료");
	   		
	   		forward.setRedirect(true); // 리다이렉트 방식으로 전송
	   		forward.setPath("./LoginForm.lo");
	   		return forward;
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return null;
	}  	


}
