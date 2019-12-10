<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardContent</title>
</head>
<body>
	<h1>boardContent</h1>
	<table>
		<tr>
			<td>date</td><td>${vo.regdate }</td>
		</tr>
		<tr>
			<td>name</td><td>${vo.id }</td>
		</tr>
		<tr>
			<td>file#1</td><td>${vo.file01 }</td>
		</tr>
		<tr>
			<td>file#2</td><td>${vo.file02 }</td>
		</tr>
		<tr>
			<td>link#1</td><td>${vo.link01 }</td>
		</tr>
		<tr>
			<td>subject</td><td>${vo.subject }</td>
		</tr>
		<tr>
			<td colspan="2">${vo.content }</td>
		</tr>
	</table>
	<input type="button" value="수정" onclick="location='boardModifyForm.vol?num=${vo.bbsno}'" />
	<input type="button" value="삭제" onclick="location='boardDeleteForm.vol?num=${vo.bbsno}'" />	
</body>
</html>