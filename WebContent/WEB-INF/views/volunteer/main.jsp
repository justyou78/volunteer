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
	<form action="/volunteer/volunteer/main.vol" method="post" >
	
	
	<input type="date"  name="sysdate" /> <br/>
	<table>
		<tr>
			<td>봉사자</td>
			<td>장애인</td>
			<td>내용</td>
			<td>시간</td>
		</tr>	
		<c:forEach var="list" items="${vol_list }">
		
		<tr>
			<td>${list.vol_id } </td>
			<td>${list.disabled_id } </td>
			<td>${list.vol_time_id } </td>
			<td>${list.content_id } </td>
			
		</tr>
		</c:forEach>
		
	</table>
	
	
	
	<input type="submit" value="찾기"/>
	</form>


	<img src="/volunteer/img/graphimg/${imgName}"/>
	
</body>
</html>