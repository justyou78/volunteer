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
	<!-- <input type="text" id="sample4_postcode" placeholder="우편번호"> -->
<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
<input type="text" id="sample4_roadAddress" placeholder="도로명주소" name="address">
<span id="guide" style="color:#999;display:none"></span>
<!-- <input type="text" id="sample4_jibunAddress" placeholder="지번주소">
<input type="text" id="sample4_detailAddress" placeholder="상세주소">
<input type="text" id="sample4_extraAddress" placeholder="참고항목"> -->

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>
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

