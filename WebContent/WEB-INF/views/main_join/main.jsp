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
		<c:if test="${isLogin !=null}">
			
			<script>
				window.alert('${isLogin}');
			</script>
		</c:if>
			 
	       <!-- 로그인 되었을 때  -->
		<c:if test="${id !=null }">
			<form method="get" action="logout.vol">
				<input type="submit"  value="로그아웃"  />
				
			</form>
		</c:if>
		   <!-- 로그인 되어있지 않을 때.  -->
		<c:if test="${id ==null }">
			 <a href="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://192.168.0.48:8081/volunteer/main_join/oauth.vol&response_type=code">
	            <img src="/volunteer/img/kakao_login_btn_small.png">
	        </a>
		</c:if>
		
		
		
		
        <!-- <img alt="" src="http://k.kakaocdn.net/dn/bdxYWy/btqz0GfFKbr/QOlFfVwItHqviC2rEbbEP1/img_640x640.jpg"> -->
        

	<input type="button" value="봉사자"   onclick="window.location.href='/volunteer/main_join/join.vol?page=1'"/>
	
	<input type="button" value="장애인" onclick="window.location.href='/volunteer/main_join/join.vol?page=2'"/>
	
	
	
</body>
</html>