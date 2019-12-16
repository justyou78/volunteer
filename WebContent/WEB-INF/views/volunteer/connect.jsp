<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp" %>
</head>
<body>

	<form action="/volunteer/volunteer/connect_pro.vol" method="post">
	
	<input type="hidden" value="${disabled_id }" name="disabled_id"/> 
	이메일<input type="email" name="email"placeholder="이메일을 입력해주세요"/>
	비밀번호<input type="password" name="pw" />

	<input type="submit" value="수락하기" />
	
	</form>
	
	

</body>
</html>

<script>
	
</script>