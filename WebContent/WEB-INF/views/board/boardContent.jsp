<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>boardContent</title>
	<%@ include file="../include/include.jsp" %>
</head>
<body class="grey lighten-3">
	<%@ include file="../include/vol_include.jsp" %>
	<div class="row" style="margin-bottom: 50px;">
	<div class="col s3"></div>
	<div class="col s6">
	<h3 class="center-align">게시판</h3>
	<table>
		<tr>
			<td style="width: 100px;">date</td><td><fmt:formatDate value="${vo.regdate }" pattern="yyyy.MM.dd"/></td>
		</tr>
		<tr>
			<td>name</td><td>${vo.name }</td>
		</tr>
		<c:if test="${vo.file01 != null}">
		<tr>
			<td>file#1</td><td>${vo.file01 }</td>
		</tr>
		</c:if>
		<c:if test="${vo.file02 != null}">
		<tr>
			<td>file#2</td><td>${vo.file02 }</td>
		</tr>
		</c:if>
		<c:if test="${vo.link01 != null}">
		<tr>
			<td>link#1</td><td>${vo.link01 }</td>
		</tr>
		</c:if>
		<tr>
			<td>subject</td><td>${vo.subject }</td>
		</tr>
		<tr>
			<td colspan="2">${vo.content }</td>
		</tr>
	</table>
	<div class="row" style="margin: 10px;">
	<div class="col s6 left-align">
	<input class="btn light-blue darken-4" type="button" value="목록" onclick="location='boardList.vol'" />
	</div>
	<div class="col s6 right-align">
	<c:if test="${vo.id == id}">
	<input class="btn light-blue darken-4" type="button" value="수정" onclick="location='boardModifyForm.vol?num=${vo.bbsno}'" />
	<input class="btn light-blue darken-4" type="button" value="삭제" onclick="location='boardDeleteForm.vol?num=${vo.bbsno}'" />	
	</c:if>
	</div>
	</div>
	</div>
	<div class="col s3"></div>
	</div>
	<%@ include file="../include/footer.jsp" %>
</body>
</html>