<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp" %>

	<style type="text/css">
		body {
  	background-color:#eeeeee;
    text-align: center;
		}
		.t1{
			font-size: 38px;
		}
		
	.container_center{
		position: absolute;
		width: 500px;
		
		height: 500px;
		top: 50%;
		left: 50%;
		margin: -250px 0 0 -250px;
	
	}
	
	</style>
	
	
	
</head>
<body>

		<div	class="container_center">
		
			
			<i class="material-icons" style="font-size: 75px;">home</i><span style="font-size: 75px;">봉찾사</span><br/>
			<span class="t1">간편하게 로그인하고</span><br/>
			<span class="t1">다양한 서비스를 이용하세요.</span>
				
			<br/>
			<br/>
			 
			
				 <a href="https://kauth.kakao.com/oauth/authorize?client_id=0b04594c8c451f296a17595e84141bd2&redirect_uri=http://192.168.0.48:8081/volunteer/main_join/oauth.vol&response_type=code" >
		            <img src="/volunteer/img/kakao_account_login_btn_large_narrow.png">
		        </a>
		    
	    </div>
	        
		
</body>
</html>