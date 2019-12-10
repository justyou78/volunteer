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
	<form action="join_pro.vol" method="post" name="join">
	<div>신뢰할 수 있는 사람인지 알기 위해서 추가적인 정보가 필요합니다. </div>
	<select name=member_type onchange="select_type()" id="member_type">
		<option value="1"  selected>봉사자</option>
		<option value="2">장애인</option>
		
	</select><br/>
	이메일<input type="email"  name="email" placeholder="이메일 형식으로 아이디 입력해주세요."> <br/>
	 나이 <input type="number" name="age" /> <br/>
	성별 <select name="gender"  name="gender"><option value="1" >남</option><option value="2"> 여</option></select><br/>
	주소 <input type="text"  name="address"/> <br/>
	<div id="type"></div>
	
	
	
	<input type="submit" value="회원가입"/>
	<!--  member type 과 dsabled_detail,regist_number_vol / regist_number/disabled 값을 설정해주자 -->
	</form>
	
	
</body>
<script>
		window.onload = function() { //실행될 코드 }
			select_type();
		}
		function select_type(){
			var mt = document.getElementById("member_type");
			console.log(mt.value);
			
			if(mt.value==1){
				
				type.innerHTML='자격증 이름<input type="text" name="vol_name" /><br/> 자격증 번호<input type="text" name="regist_number_vol"/> ';
				
			}
			else if(mt.value==2){
				type.innerHTML='장애명<input type="text" name="disabled_name" /><br/> 장애인등록번호<input type="text" name="disabled_number"/>';
			}
		}
		
</script>
</html>

