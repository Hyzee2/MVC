<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="NavBar.jsp" %> <!-- 네비게이션 바를 포함 -->
    <title>로그인 페이지</title>
</head>
<body>
<br>
<br>
<div style="max-width: 400px; margin: 0 auto;">
    <h1 style="text-align: center; margin-bottom: 30px;">로그인</h1>
    <form action="./LoginAction.lo" method="post" style="background-color: #F9F9F9; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
        <div style="margin-bottom: 20px;">
            <label for="id" style="font-weight: bold;">아이디:</label>
            <input type="text" id="id" name="id" maxlength="20" style="width: calc(100% - 22px); padding: 10px; border: 1px solid #ccc; border-radius: 3px;">
        </div>
        <div style="margin-bottom: 20px;">
            <label for="password" style="font-weight: bold;">비밀번호:</label>
            <input type="password" id="pw" name="pw" maxlength="20" style="width: calc(100% - 22px); padding: 10px; border: 1px solid #ccc; border-radius: 3px;">
        </div>
        <input type="submit" value="로그인" style="width: 100%; padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 3px; cursor: pointer;">
    </form>
    <!-- 회원가입 버튼 -->
    <button id="signup-btn" onclick="location.href='./JoinForm.lo'" style="width: 100%; padding: 10px; margin-top: 10px; background-color: #3498DB; color: white; border: none; border-radius: 3px; cursor: pointer;">회원가입</button>
</div>
<script>
    document.querySelector('form').addEventListener('submit', function(event) {
        var id = document.getElementById('id').value.trim();
        var pw = document.getElementById('pw').value.trim();
        if (!id) {
            alert('아이디를 입력하세요.');
            event.preventDefault();
        } else if (!pw) {
            alert('비밀번호를 입력하세요.');
            event.preventDefault();
        }
    });
</script>
</body>
</html>