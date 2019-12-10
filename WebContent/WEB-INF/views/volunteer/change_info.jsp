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
	<form action="/volunteer/volunteer/change_info_Pro.vol" method="post">
	
	<h1>회원정보 수정</h1>
	<input type="email" value="${memberVO.email }" name="email" />
	<input type="text" value="${memberVO.age }" name="age" />
	<c:if test="${memberVO.gender ==1}">
		<input type="text" value="남" name="isgender" />
	</c:if>
	<c:if test="${memberVO.gender==2}">
		<input type="text" value="여" name="isgender" />
	</c:if>
	
	<input type="text" value="${memberVO.address}" name="address" />
	<c:if test="${memberVO.member_type== 1 }">
		<input type="text" value="${memberVO.vol_name}" name="vol_name" />
		<input type="text" value="${memberVO.regist_number}" name="regist_number" />
		
	</c:if>
	<c:if test="${memberVO.member_type== 2 }">
		<input type="text" value="${memberVO.disabled_name}" name="disabled_name" />
		<input type="text" value="${memberVO.disabled_number}" name="disabled_number" />
		
	</c:if>
	<input type="hidden" name="member_type" value=${memberVO.member_type } />
	<input type="submit" value="수정하기"/>
	</form>
	
	
	
</body>
</html>