<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp" %>

<style type="text/css">

	.t1{
		font-size: 27px;
	}
	</style>
</head>
<body>
	<div class="row center-align valign-wrapper" style="height: 1000px;">
	
	<div class="col s3"></div>
	<div class="col s9">
	

	
	<div class="container_center">
	
	<form action="/volunteer/volunteer/connect_pro.vol" method="post">
		<i class="material-icons" style="font-size: 75px;">alarm</i><span style="font-size: 75px;">봉찾사</span><br/><br/>
	
		<span class="t1">봉사 요청이 왔습니다!</span><br/>
		<span class="t1">정보를 입력 후, 수락버튼을 누르세요. </span> <br/>
		<input type="hidden" value="${disabled_id }" name="disabled_id"/> 
		<input type="email" name="email"placeholder="이메일을 입력해주세요"/>
		<input type="password" name="pw" placeholder="비밀번호를 입력해주세요" />
		<button class="btn waves-effect waves-light black lighten-3" type="submit" name="action" value="수락하기">수락하기
	    <i class="material-icons right">send</i>
	  	</button>
	
	</form>
	</div>
</div>
<div class="col s3"></div>
</div>
	
	

</body>
</html>

<script>
	
</script>