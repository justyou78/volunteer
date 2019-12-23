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
	<c:if test="${auth !=null }">
		<script>
				window.alert('${auth}');
			
		</script>
		
	</c:if>
	<form action="/volunteer/volunteer/main.vol" method="post" >
	<a href="change_info.vol">회원정보수정</a>
	<a href="/volunteer/board/boardList.vol">게시판</a>
	<a href="sponsor.vol">후원하기</a>
	
	<input type="date"  name="sysdate" /> <br/>
	<table>
		<tr>
			<td>봉사자</td>
			<td>장애인</td>
			<td>봉사시간</td>
			
			<td>내용</td>
			<td>봉사끝난시간</td>
		</tr>	
		<c:forEach var="vo" items="${list }">
		
		<tr>
			
			<td> ${hm[vo.vol_id]}</td>
			<td>${hm[vo.disabled_id]} </td>
			<td>${vo.vol_time } </td>
			<td>${vo.content } </td>
			<td>${vo.time } </td>
		</tr>
		</c:forEach>
		
		
		
		
	
	</table>
	
	<table>
		<tr>
			<td>후원자아이디</td>
			<td>후원자 닉네임</td>
			<td>봉사시간</td>
		</tr>	
		<c:forEach var="donationVO" items="${donationList}">
		<tr>
			<td> ${donationVO.user_id}</td>
			<td>${donationVO.item_name} </td>
			<td>${donationVO.total} </td>
		</tr>
		</c:forEach>
	</table>
	<input type="submit" value="찾기"/>
	</form>


	<img src="/volunteer/img/graphimg/${imgName}"/>
	
</body>
</html>