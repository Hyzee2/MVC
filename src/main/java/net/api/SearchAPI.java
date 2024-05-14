package net.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchAPI
 */

public class SearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		service(request, response);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.service(req, resp);
		
		resp.setContentType("application/json; charset=UTF-8"); 
		req.setCharacterEncoding("UTF-8");
		
		// 1. ���� ���� ����
		String clientId = "wZoulhsjJqqMDF2ZvVbP"; //���ø����̼� Ŭ���̾�Ʈ ���̵�
        String clientSecret = "XtFLEgIT6c"; //���ø����̼� Ŭ���̾�Ʈ ��ũ��
        
        // 2. �˻� ���� ����
        int startNum = 1; // �˻� ���� ��ġ
        String searchText = req.getParameter("keyword");
//        String text = searchText != null ? URLEncoder.encode(searchText, "UTF-8") : ""; // �˻��� 
        String text = searchText != null ? URLEncoder.encode(searchText, "UTF-8") : ""; // �˻��� 
        
//        try {
        	//startNum = Integer.parseInt(req.getParameter("startNum")); // �˻� ���� ��ġ�� �Ű������� ����
        	String startNumParam = req.getParameter("startNum");
        	
        	if (startNumParam != null && startNumParam.matches("\\d+")) {
                startNum = Integer.parseInt(startNumParam);
            }
//        	System.out.println(startNum);
//        	
//        	String searchText = req.getParameter("keyword"); // �˻�� �Ű������� ���� 
//            text = URLEncoder.encode(searchText, "UTF-8"); // �˻���� �ѱ� ������ �����ϱ� ���� ���ڵ�
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("�˻��� ���ڵ� ����",e);
//        }

        // 3. API URL ����
        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=10&start=" + startNum; 
        /* JSON ��� 
         * display�� �� ���� ������ �˻� ����� ����
         * start�� �˻� ���� ��ġ
         * */
        
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML ���

        // 4. API ȣ��
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId); // Ŭ���̾�Ʈ ���̵� ��û ����� ����
        requestHeaders.put("X-Naver-Client-Secret", clientSecret); // Ŭ���̾�Ʈ ��ũ���� ��û ����� ����
        String responseBody = get(apiURL,requestHeaders); // API ȣ��

        // 5. ��� ���
//        System.out.println(responseBody);
//        
//        resp.setContentType("text/html; charset=UTF-8");
        System.out.println(responseBody);
//        String str = responseBody;
//        str = URLEncoder.encode(str, "EUC-KR");
//        System.out.println(str);
        resp.getWriter().write(responseBody); // �������� ���ڵ� �� ��� ���
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }

	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
	                return readBody(con.getInputStream());
	            } else { // ���� �߻�
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API ��û�� ���� ����", e);
	        } finally {
	            con.disconnect();
	        }
	    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        try(InputStreamReader streamReader = new InputStreamReader(body, "UTF-8");


        	BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ������ �д� �� �����߽��ϴ�.", e);
        }
    }

}
