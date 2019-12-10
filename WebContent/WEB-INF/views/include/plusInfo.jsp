<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="include.jsp" %>

</head>
<body>
	<c:if test="${plusInfo ==false}">
  		<p><a href="/volunteer/main_join/join.vol">  추가 정보를 입력하셔야 봉사활동을 진행 할 수 있습니다.</a></p>
  
 	 </c:if>
		
	

</body>
</html>