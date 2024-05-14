<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<style>
* {
	font-family: 국민연금체;
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh; /* 화면 전체 높이 */
	margin: 0; /* 마진 제거 */
}

#searchFrm {
	display: flex;
	width: 70%; /* 폼 전체 너비 */
	margin: 0 auto;
}

#startNum { /* 페이지 선택 select 스타일 */
	width: 100px; /* 너비 축소 */
	padding: 8px;
	margin-right: 10px; /* 오른쪽 여백 추가 */
	outline: none;
	
	
}

#keyword {
	flex-grow: 1; /* 나머지 공간을 채움 */
	padding: 12px 20px;
	font-size: 18px;
	border: 2px solid #cccccc; /* 연한 회색 테두리 */
	border-right: none; /* 오른쪽 테두리 제거 */
	outline: none;
	border-radius: 20px 0 0 20px; /* 둥근 모서리 */
}

#searchBtn {
	padding: 12px 20px;
	font-size: 18px;
	background-color: #2DB400; /* 네이버 녹색 */
	color: white;
	border: 2px solid #2DB400; /* 버튼 테두리 */
	cursor: pointer;
	border-radius: 0 20px 20px 0; /* 둥근 모서리 */
	transition: background 0.3s;
}

#searchBtn:hover {
	background-color: #1D7E00; /* 네이버 녹색 어두운 버전 */
}

#searchResult ul {
	list-style-type: none;
    padding: 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 10px;
    padding: 10px;
    overflow: hidden; /* 글이 박스 밖으로 넘어가지 않도록 설정 */
}

#searchResult li {
	padding: 5px 0;
}

#searchResult li.title { /* 제목 */
    font-size: 20px; /* 글씨 크기 증가 */
    color: #2DB400; /* 네이버 초록색 */
    font-weight: bold; /* 굵은 글씨 */
    white-space: nowrap; /* 줄바꿈 없이 한 줄로 표시 */
    overflow: hidden; /* 내용이 너무 길면 잘라내기 */
    text-overflow: ellipsis; /* 잘린 텍스트에 말줄임표 표시 */
}

#searchResult li.description { /* 설명 */
    font-size: 16px; /* 현재 글씨 크기 유지 */
    color: #333; /* 진한 회색 */
}

#searchResult li.small { /* 블로거 이름, 링크, 포스트 날짜 */
    font-size: 12px; /* 글씨 크기 감소 */
    color: #666; /* 밝은 회색 */
}

#searchResult a { /* 링크 스타일 */
       color: #0056b3;
       text-decoration: none;
}

#searchResult a:hover {
    text-decoration: underline;
}

a {
	color: #0056b3;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
</style>
<html>
<head>
<meta charset="UTF-8">

<title>네이버 검색 결과</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js">
	
</script>
<script>
	//[검색요청] 버튼 클릭 시 실행할 메서드 정의
	/* $(function() {

		$('#searchBtn').click(function() {
			$.ajax({
				url : "../NaverSearch", // 요청 url(서블릿 매핑한 것)
				type : "get", // HTTP 메서드(get 방식으로 전송)
				data : {
					keyword : $('#keyword').val(), // 검색어
					startNum : $('#startNum option:selected').val()
				// 검색 시작 위치
				},
				dataType : "json" // 응답 데이터 형식
				,
				success : sucFuncJson // 요청 성공 시 호출할 메서드 설정
				,
				error : errFunc
			// 요청 실패 시 호출할 메서드 설정
			});
		});
	}); */
	$(function(){
	    // 검색 버튼 클릭 이벤트
	    $('#searchBtn').click(function(){ 
	        triggerSearch();
	    });

	    // 엔터키 이벤트 리스너 추가
	    $('#keyword').on('keypress', function(e) {
	        if (e.which == 13) { // 엔터 키 코드는 13
	            triggerSearch();
	        }
	    });

	    // 검색 트리거 함수
	    function triggerSearch() {
	        $.ajax({
	            url : "../NaverSearch",
	            type : "get",
	            data : {
	                keyword : $('#keyword').val(),
	                startNum : $('#startNum option:selected').val()
	            },
	            dataType : "json",
	            success : sucFuncJson,
	            error : errFunc
	        });
	    }
	});
	// 검색 성공 시 결과를 화면에 뿌려줌(성공 시의 콜백 메서드)
	function sucFuncJson(d) {
		var str = "";
		$.each(d.items,function(index, item) { // $.each 메서드를 사용하여 콜백 데이터 중 items 부분을 반복 파싱함
			str += "<ul>";
			
			str += "<li class='title'>" + $("<div/>").html(item.title).text() + "</li>";
			str += "<li class='description'>" + $("<div/>").html(item.description).text() + "</li>";
			str += "	<li class='small'><span>"+item.bloggername+"</span><span>"+item.bloggerlink+"</span></li>";
/* 			str += "	<li>" + item.bloggerlink + "</li>"; */
			str += "<li class='small'>" + item.postdate + "</li>";
			str += "	<li><a href='" + item.link + "' target='_blank'>바로가기</a></li>";
			str += "</ul>";
		});
		$('#searchResult').html(str); // id가 searchResult인 영역에 html 형태로 출력됨
	}

	// 실패 시 경고창을 띄워줌
	function errFunc(xhr, status, error) {
		console.error("실패:", status, error);
		console.error("응답 헤더:", xhr.getAllResponseHeaders());
		console.error("응답 본문:", xhr.responseText);
	}
</script>
</head>
<body>
<br><br>
	<div style="width: 80%;">
		<div id="searchFrm">
			<select id="startNum"> <!-- 페이지 선택 위치 -->
		        <option value="1">1페이지</option>
		        <option value="11">2페이지</option>
		        <option value="21">3페이지</option>
		        <option value="31">4페이지</option>
		        <option value="41">5페이지</option>
		    </select>
		    <input type="text" id="keyword" placeholder="검색어를 입력하세요." />
		    <button type="button" id="searchBtn">검색 요청</button>
			<!-- <form id="searchFrm">

				<select id="startNum">
					검색 시작 위치를 페이지 단위로 선택
					<option value="1">1페이지</option>
					<option value="11">2페이지</option>
					<option value="21">3페이지</option>
					<option value="31">4페이지</option>
					<option value="41">5페이지</option>
				</select> <input type="text" id="keyword" placeholder="검색어를 입력하세요." />
				검색어를 입력
				<button type="button" id="searchBtn">검색 요청</button>
			</form> -->
		</div>
		<div class="row" id="searchResult">
			<!-- 자바스크립트 코드에서 결과로 받은 JSON 데이터를 파싱하여 보여줌 -->

		</div>
	</div>
</body>
</html>