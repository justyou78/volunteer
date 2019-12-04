<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%@ include file="/WEB-INF/views/include/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
		  <form method="get" action="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://localhost:8081/volunteer/main_join/oauth.vol&response_type=code">
        	<input type="submit" value="전송"/>
        </form>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://localhost:8081/volunteer/main_join/oauth.vol&response_type=code">
            <img src="/volunteer/img/kakao_login_btn_small.png">
        </a>
      
	
	<input type="button" value="봉사자"   onclick="window.location.href='join.vol'"/>
	
	<input type="button" value="장애인" onclick="window.location.href='join.vol'"/>
	
	<form action="insert.vol" method="post">
		<input type="email" placeholder="이메일을 입력하세요" name="email"/>
		<input type="submit" value="전송"/>
		
	</form>
	
</body>
</html>