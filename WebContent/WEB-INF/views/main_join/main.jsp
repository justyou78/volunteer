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
		  <!-- <form method="POST" action="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://localhost:8081/volunteer/main_join/oauth.vol&response_type=code">
        	<input type="submit" value="전송"/>
        </form> -->
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://localhost:8081/volunteer/main_join/oauth.vol&response_type=code">
            <img src="/volunteer/img/kakao_login_btn_small.png">
        </a>
        <!-- <img alt="" src="http://k.kakaocdn.net/dn/bdxYWy/btqz0GfFKbr/QOlFfVwItHqviC2rEbbEP1/img_640x640.jpg"> -->
        
      <c:set var ="path" value="${pageContext.request.contextPath}" />
 	
	
	<input type="button" value="봉사자"   onclick="window.location.href='join.vol?type=1'"/>
	
	<input type="button" value="장애인" onclick="window.location.href='join.vol?type=2'"/>
	
	
</body>
</html>