<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="net.admin.db.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="adminNavBar.jsp" %>
<title>회원 리스트</title>
	<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .user-box {
            border-bottom: 1px solid #ddd; /* 각 유저 사이에 선 추가 */
            padding: 10px 0;
        }
        .user-info {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .user-info h3 {
            margin: 0;
            margin-right: 10px;
        }
        .user-info .button-container {
            display: flex;
            align-items: center;
        }
        input[type="submit"], button {
            padding: 8px 15px;
            background-color: #4CAF50;
            border: none;
            color: white;
            border-radius: 20px; /* 버튼을 둥글게 스타일링 */
            cursor: pointer;
            transition: background-color 0.3s;
            margin-left: 10px;
        }
        input[type="submit"]:hover, button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<br>
<br>
<h1 style="text-align: center; margin-bottom: 30px;">회원 관리</h1>
    <div class="container">
        <% 
            List<String> userList = (List<String>) request.getAttribute("userList");
            if (userList != null && !userList.isEmpty()) {
                for (String user : userList) {
        %>
                    <div class="user-box">
                        <div class="user-info">
                            <h3><%= user %></h3>
                            <div class="button-container">
                                <form action="./AdminDetail.ad" method="post">
                                    <input type="hidden" name="userId" value="<%= user %>">
                                    <input type="submit" value="정보보기">
                                </form>
                                <form action="./AdminDelete.ad" method="post">
                                    <input type="hidden" name="userId" value="<%= user %>">
                                    <button type="submit">회원삭제</button>
                                </form>
                            </div>
                        </div>
                    </div>
        <%
                }
            } else {
        %>
                <h2>회원 목록이 존재하지 않습니다.</h2>
        <%
            }
        %>
    </div>
</body>
</html>