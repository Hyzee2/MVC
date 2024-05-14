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
		
		// 1. 인증 정보 설정
		String clientId = "wZoulhsjJqqMDF2ZvVbP"; //애플리케이션 클라이언트 아이디
        String clientSecret = "XtFLEgIT6c"; //애플리케이션 클라이언트 시크릿
        
        // 2. 검색 조건 설정
        int startNum = 1; // 검색 시작 위치
        String searchText = req.getParameter("keyword");
//        String text = searchText != null ? URLEncoder.encode(searchText, "UTF-8") : ""; // 검색어 
        String text = searchText != null ? URLEncoder.encode(searchText, "UTF-8") : ""; // 검색어 
        
//        try {
        	//startNum = Integer.parseInt(req.getParameter("startNum")); // 검색 시작 위치를 매개변수로 받음
        	String startNumParam = req.getParameter("startNum");
        	
        	if (startNumParam != null && startNumParam.matches("\\d+")) {
                startNum = Integer.parseInt(startNumParam);
            }
//        	System.out.println(startNum);
//        	
//        	String searchText = req.getParameter("keyword"); // 검색어를 매개변수로 받음 
//            text = URLEncoder.encode(searchText, "UTF-8"); // 검색어는 한글 깨짐을 방지하기 위해 인코딩
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("검색어 인코딩 실패",e);
//        }

        // 3. API URL 조합
        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=10&start=" + startNum; 
        /* JSON 결과 
         * display는 한 번에 가져올 검색 결과의 개수
         * start는 검색 시작 위치
         * */
        
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

        // 4. API 호출
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId); // 클라이언트 아이디를 요청 헤더로 전달
        requestHeaders.put("X-Naver-Client-Secret", clientSecret); // 클라이언트 시크릿을 요청 헤더로 전달
        String responseBody = get(apiURL,requestHeaders); // API 호출

        // 5. 결과 출력
//        System.out.println(responseBody);
//        
//        resp.setContentType("text/html; charset=UTF-8");
        System.out.println(responseBody);
//        String str = responseBody;
//        str = URLEncoder.encode(str, "EUC-KR");
//        System.out.println(str);
        resp.getWriter().write(responseBody); // 서블릿에서 인코딩 후 즉시 출력
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }

	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 오류 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
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
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
