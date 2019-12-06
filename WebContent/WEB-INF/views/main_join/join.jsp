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
	<form action="join.vol" method="post">
	<div>신뢰할 수 있는 사람인지 알기 위해서 추가적인 정보가 필요합니다. </div>
	이메일<input type="email"  name="email" placeholder="이메일 형식으로 아이디 입력해주세요."> <br/>
	 나이 <input type="number" name="age" /> <br/>
	성별 <select name="gender"  name="gender"><option value="남" >남</option><option value="여"> 여</option></select><br/>
	주소 <input type="text"  name="address"/> <br/>
	<input type="hidden" name="member_type" />
	<c:if test="${type==1 }">
		자격증 이름<input type="text" name="regist_number_vol" /> 
		자격증 번호<input type="text" name="regist_number_vol"/> 
	</c:if>
	<c:if test="${type==1 }">
		
		장애명<input type="text" name="regist_number_vol" /> 
		장애인등록번호<input type="text" name="regist_number_vol"/> 
	</c:if>
	<input type="submit" value="회원가입"/>
	<!--  member type 과 dsabled_detail,regist_number_vol / regist_number/disabled 값을 설정해주자 -->
	</form>
</body>
</html>