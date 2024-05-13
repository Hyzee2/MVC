package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.util.*;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

// �Խ��� �۾��� �� �����ͺ��̽��� insert�ϴ� �׼� ��Ʈ�ѷ� 
public class BoardAddAction implements Action{ // �����ͺ��̽��� �̿��ϴ� �׼� ��Ʈ�ѷ�(DB�ΰ��� ����� �� ������!)

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao=new BoardDAO();
		
	   	BoardBean boarddata=new BoardBean();
	   	
	   	ActionForward forward=new ActionForward();
	   	
		String realFolder=""; // �������ϸ�
   		String saveFolder="upload"; // �������ϸ�
   		
   		int fileSize=5*1024*1024;
   		
   		realFolder=request.getRealPath(saveFolder);
   		
   		boolean result=false;
   		
   		try{
   			
   			MultipartRequest multi=null;  // ���� ���ε�
   			
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
	   			System.out.println("�Խ��� ��� ����");
	   			return null;
	   		}
	   		System.out.println("�Խ��� ��� �Ϸ�");
	   		
	   		forward.setRedirect(true); // �����̷�Ʈ ������� ����
	   		forward.setPath("./BoardList.bo");
	   		return forward;
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return null;
	}  	

}
