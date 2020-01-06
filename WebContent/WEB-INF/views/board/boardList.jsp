<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>boardList</title>
	<%@ include file="../include/include.jsp" %>
</head>
<body class="grey lighten-3">
	<%@ include file="../include/vol_include.jsp" %>
	<div class="row" style="margin-bottom: 50px;">
	<div class="col s3"></div>
	<div class="col s6">
	<h3 class="center-align">게시판</h3>
	<table>
		<thead>
		<tr>
			<th class="center-align">번호</th><th class="center-align">제목</th><th class="center-align">이름</th><th class="center-align">날짜</th><th class="center-align">조회</th>
		</tr>
		</thead>
		<c:forEach var="vo" items="${vo}">
		<tr>
			<td style="width: 100px; text-align: center;">${vo.bbsno }</td><td>[${vo.categor }]<a href="boardContent.vol?num=${vo.bbsno}">${vo.subject }</a></td><td style="width: 100px; text-align: center;"><a href="" onclick="newopen(${vo.id })">${vo.name }</a></td><td style="width: 100px; text-align: center;"><fmt:formatDate value="${vo.regdate }" pattern="yyyy.MM.dd"/></td><td style="width: 50px; text-align: center;">${vo.views }</td>
			
		</tr>
		</c:forEach>
	</table>
		<div class="row" style="margin: 10px;">
			<div class="col s6 left-align">
			<input class="btn light-blue darken-4" type="button" value="목록" onclick="location='boardList.vol'" />
			</div>
			<div class="col s6 right-align">
			<input class="btn light-blue darken-4" type="button" value="글쓰기" onclick="location='boardWriteForm.vol'" />
			</div>
		</div>
	</div>
	<div class="col s3"></div>
	</div>
	<%@ include file="../include/footer.jsp" %>
	
</body>
	<script>
   // 상세정보 띄우는 메서드
   function newopen(id) {
      window.open('http://localhost:8081/volunteer/commons/memberInfo.vol?id='+id+'', 'info', 'width=700, height=350');
   }
</script>
</html>