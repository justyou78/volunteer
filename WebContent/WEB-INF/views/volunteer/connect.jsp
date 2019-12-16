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
	<c:if test="${invalid=='nologin' }">
		<script>
			window.alert('카카오로그인을 하셔야 합니다.');
			
		</script>
	</c:if>
	<c:if test="${invalid=='nojoin' }">
		<script>
			window.alert('추가사항 확인을 위한 회원가입을 하셔야 합니다.');
			
		</script>
	</c:if>
	

	<input type="button" onclick="connect('success')" value="수락" />
	<input type="button" onclick="connect('fail')" value="거절" />

</body>
</html>

<script>
	function connect(text) {
		
		if (text == 'success') {
			location.href='/volunteer/disabled/connect.vol?connect="success"';
		}
		else{
			location.href='/volunteer/disabled/connect.vol?connect="fail"';
			
		}
	}
</script>