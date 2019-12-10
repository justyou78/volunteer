<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardList</title>
</head>
<body>
	<h1>boardList</h1>
	<table>
		<tr>
			<td>번호</td><td>제목</td><td>이름</td><td>날짜</td><td>조회</td>
		</tr>
		<c:forEach var="vo" items="${vo}">
		<tr>
			<td>${vo.bbsno }</td><td><a href="boardContent.vol?num=${vo.bbsno}">${vo.subject }</a></td><td>${vo.id }</td><td>${vo.regdate }</td><td>${vo.views }</td>
		</tr>
		</c:forEach>
	</table>
	<input type="button" value="글쓰기" onclick="location='boardWriteForm.vol'" />
</body>
</html>