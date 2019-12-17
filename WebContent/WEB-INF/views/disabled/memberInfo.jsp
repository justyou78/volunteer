<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberInfo</title>
</head>
<body>

	<h1>memberInfo</h1>
	아이디 >> ${vo.id }<br />
	이메일 >> ${vo.email }<br />
	이름 >> ${vo.name }<br />
	나이 >> ${vo.age }<br />
	성별 >> ${vo.gender }<br />
	주소 >> ${vo.address }<br />
	<c:if test="${vo.picture == null}">
		<img src="\img\bono.png">
	</c:if>
	<c:if test="${vo.picture != null}">
		<img src="${vo.picture }">
	</c:if>
</body>
</html>