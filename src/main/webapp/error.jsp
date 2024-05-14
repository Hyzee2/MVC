<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>에러 페이지</title>
    <style>
        body {
            font-family: 국민연금체;
            background-color: #F9F9F9;
            text-align: center;
            padding-top: 50px;
        }
        .error-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #E74C3C;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .error-container h1 {
            color: #E74C3C;
            margin-bottom: 20px;
        }
        .error-container p {
            margin-bottom: 20px;
        }
        .error-container a {
            display: inline-block;
            padding: 10px 20px;
            color: white;
            background-color: #3498DB;
            text-decoration: none;
            border-radius: 3px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Error Page</h1>
        <p>요청을 처리하는 중에 문제가 발생했습니다.</p>
        <a href="./Main.lo">돌아가기</a>
    </div>
</body>
</html>