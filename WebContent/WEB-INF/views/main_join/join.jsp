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
	
	
	<div class="container" style="margin-top: 50px; width: 50%;">
	<form action="join_pro.vol" method="post" name="join" >
	
	<h3>가입정보 입력</h3>
	<blockquote class="">신뢰할 수 있는 사람인지 알기 위해서 추가적인 정보가 필요합니다. </blockquote>
	
	<div class="input-field">
	
    <select class="icons"  onchange="select_type()"  id="member_type" name="member_type">
      <option value="1" data-icon="/volunteer/img/jange2.png" class="left"  checked>봉사자</option>
      <option value="2" data-icon="/volunteer/img/jange1.png"   class="left" >장애인</option>
    </select>
    <label>유형</label>
  	</div>
  	
  	
	<br/>
	 <div class="row">
        <div class="input-field col s12">
          <input id="email" type="email" class="validate" name="email">
          <label for="email">이메일</label>
        </div>
      </div>
	<div class="row">
        <div class="input-field col s12">
          <input id="password" type="password" class="validate" name="pw">
          <label for="password">비밀번호</label>
        </div>
      </div>
      
      
       
       <div class="row">
	       <div class="input-field col s4">
		    <select class="icons"  onchange="select_type()"   name="gender"  >
		      <option value="1"  class="left"  checked>남</option>
		      <option value="2" class="left" >여</option>
		    </select>
		    <label>성별</label>
		  	</div>
	     
	        <div class="input-field col s4">
	          <input id="age" type="number" class="validate" name="age">
	          <label for="age">나이</label>
	        </div>
	          <div class="input-field col s4">
	          <i class="material-icons prefix">phone</i>
	          <input id="icon_telephone" type="tel" class="validate" name="callnumber" >
	          <label for="icon_telephone">전화번호</label>
	       </div>
        
      </div>
       
	 
  	
  	 <div class="row">
  	  	<div class="input-field col s6">
       
	          <input type="text" id="sample4_roadAddress" placeholder="도로명주소" name="address">
	          <label for="address">address</label>
	    </div>
        <div class="input-field col s6">
        <a class="waves-effect waves-light btn light-blue darken-4" onclick="sample4_execDaumPostcode()"><i class="material-icons right" >map</i>우편번호 찾기</a>
          
		<span id="guide" style="color:#999;display:none"></span>
        </div>
        
      </div>
       
       
       <input type="hidden" id="sample4_postcode" placeholder="우편번호">

<input type="hidden" id="sample4_jibunAddress" placeholder="지번주소">
<input type="hidden" id="sample4_detailAddress" placeholder="상세주소">
<input type="hidden" id="sample4_extraAddress" placeholder="참고항목">

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
	
		<div class="row">
			<button class="btn waves-effect waves-light col s2 offset-s10 light-blue darken-4" type="submit" name="action">회원가입
		    	<i class="material-icons right">send</i>
		  	</button>
	  	</div>
	<!--  member type 과 dsabled_detail,regist_number_vol / regist_number/disabled 값을 설정해주자 -->
	</form>
	</div>
	
	
</body>
<script>

		document.addEventListener('DOMContentLoaded', function() {
		    var elems = document.querySelectorAll('select');
		    var instances = M.FormSelect.init(elems);
		  });
		
		  // Or with jQuery
		
		  $(document).ready(function(){
		    $('select').formSelect();
		  });
		window.onload = function() { //실행될 코드 }
			select_type();
		}
		function select_type(){
			var mt = document.getElementById("member_type");
			console.log(mt.value);
			
			if(mt.value==1){
				
				type.innerHTML='자격증 이름<input type="text" name="vol_name" /><br/> 자격증 번호<input type="text" name="regist_number"/> ';
				
			}
			else if(mt.value==2){
				type.innerHTML='장애명<input type="text" name="disabled_name" /><br/> 장애인등록번호<input type="text" name="disabled_number"/>';
			}
		}
		
</script>
</html>

