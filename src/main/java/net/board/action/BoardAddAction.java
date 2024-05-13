package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.util.*;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

// 게시판 글쓰기 후 데이터베이스에 insert하는 액션 컨트롤러 
public class BoardAddAction implements Action{ // 데이터베이스를 이용하는 액션 컨트롤러(DB로가서 어떤일을 할 것인지!)

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao=new BoardDAO();
		
	   	BoardBean boarddata=new BoardBean();
	   	
	   	ActionForward forward=new ActionForward();
	   	
		String realFolder=""; // 실제파일명
   		String saveFolder="upload"; // 저장파일명
   		
   		int fileSize=5*1024*1024;
   		
   		realFolder=request.getRealPath(saveFolder);
   		
   		boolean result=false;
   		
   		try{
   			
   			MultipartRequest multi=null;  // 파일 업로드
   			
   			multi=new MultipartRequest(request,
   					realFolder,
   					fileSize,
   					"euc-kr",
   					new DefaultFileRenamePolicy());
   			
   			boarddata.setBOARD_NAME(multi.getParameter("BOARD_NAME"));
   			boarddata.setBOARD_PASS(multi.getParameter("BOARD_PASS"));
	   		boarddata.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
	   		boarddata.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
	   		boarddata.setBOARD_FILE(
	   				multi.getFilesystemName((String)multi.getFileNames().nextElement()));
	   		
	   		result=boarddao.boardInsert(boarddata);
	   		
	   		if(result==false){
	   			System.out.println("게시판 등록 실패");
	   			return null;
	   		}
	   		System.out.println("게시판 등록 완료");
	   		
	   		forward.setRedirect(true); // 리다이렉트 방식으로 전송
	   		forward.setPath("./BoardList.bo");
	   		return forward;
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return null;
	}  	

}
