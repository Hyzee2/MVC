<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String id = (String)session.getAttribute("id"); %>
<!DOCTYPE html>
<style>
* {
	font-family: 국민연금체;
}
</style>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="./Main.lo">💓 CODE WAVE 홈페이지 💓</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<% 
					if(id != null){
					%>
					<li class="nav-item">
						<a class="nav-link" href="./LogoutAction.lo">로그아웃</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="./Item.ba">제품</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="./BasketList.ba">장바구니</a>
					</li>
					
					<%
				}else{%>
					<li class="nav-item">
						<a class="nav-link" href="./LoginForm.lo">로그인</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="./JoinForm.lo">회원가입</a>
					</li>
				<%
					
				} %>
					<li class="nav-item">
						<a class="nav-link" href="./BoardList.bo">게시판</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="./login/SearchView.jsp">검색🔍</a>
					</li>
					
				</ul>
			</div>
			<%if(id != null){ %>
				<div class="d-flex">
					<span class="navbar-text"><%=id %>회원님 환영합니다!</span>
				</div>
			<%} %>
		</div>
	</nav>
</body>
</html>