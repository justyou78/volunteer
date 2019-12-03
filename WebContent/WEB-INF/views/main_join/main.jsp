<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<input type="button" value="봉사자"   onclick="window.location.href='join.vol'"/>
	
	<input type="button" value="장애인" onclick="window.location.href='join.vol'"/>
	
	<form action="insert.vol" method="post">
		<input type="email" placeholder="이메일을 입력하세요" name="email"/>
		<input type="submit" value="전송"/>
		
	</form>
	
</body>
</html>