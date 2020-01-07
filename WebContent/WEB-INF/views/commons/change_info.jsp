<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp"%>
<%@ include file="../include/vol_include.jsp"%>

<style>
	.subtitle{
		
	
	}
</style>
</head>
<body>
	<div class="container">
	<form action="/volunteer/commons/change_info_Pro.vol" method="post" >
		<h3 style="border-bottom: 3px solid black;">＃ 회원정보 수정</h3>
		<p>* 회원님의 정보를 소중히 지켜드리겠습니다 *</p>
		
		<h5 style="margin-top: 30px;" >※ 정보입력</h5>
		<table>
		<tr>
			<td class=" blue-text text-darken-2">
				이메일
			</td>
			<td>
				<input type="email" value="${memberVO.email }" name="email" />
			</td>
		</tr>
		<tr>
			<td class="blue-text text-darken-2">
				전화번호
			</td>
			<td>
				<input type="text" value="${memberVO.callnumber}" name="callnumber" />
			</td>
		</tr>
		<tr>
			<td class="blue-text text-darken-2">
				나이
			</td>
			<td>
			 	<input type="text" value="${memberVO.age }" name="age" />
			 </td>
		</tr>
		<tr>
			<td class="blue-text text-darken-2">
				성별
			</td>
			<td>
			 	<c:if test="${memberVO.gender ==1}">
					<input type="text" value="남" name="isgender" />
				</c:if>
				<c:if test="${memberVO.gender==2}">
					<input type="text" value="여" name="isgender" />
				</c:if>
			 </td>
		</tr>
		<input type="hidden" name="member_type"  value="${memberVO.member_type}"/>
		<tr>
			<td class="blue-text text-darken-2">
				주소
			
			</td>
			<td>
				<div class="row valign-wrapper">
					<div class="input-field col s4">
						<input type="text" id="sample4_roadAddress" placeholder="도로명주소" name="address" value="${memberVO.address}" >	
					</div>
					<div class="input-field col s4">
						<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" >
						<span id="guide" style="color:#999;display:none"></span>
					</div>
					<div class="input-field col s4">
						<input type="text" id="sample4_detailAddress" placeholder="상세주소" >
					</div>
				</div>
				
				<input type="hidden" id="sample4_postcode" placeholder="우편번호">
				<input type="hidden" id="sample4_jibunAddress" placeholder="지번주소">
				
				<input type="hidden" id="sample4_extraAddress" placeholder="참고항목">
				
			</td>
		</tr>
		<c:if test="${memberVO.member_type==1 }">
			<tr>
				<td class="blue-text text-darken-2">
					자격증
				</td>
				<td>
					<input type="text" value="${memberVO.vol_name}" name="vol_name" />
				</td>
			</tr>
			<tr>
				<td class="blue-text text-darken-2">
					자격번호
				</td>
				<td>
					<input type="text" value="${memberVO.regist_number}" name="regist_number" />
				</td>
			</tr>
		</c:if>
		<c:if test="${memberVO.member_type==2 }">
			<tr>		
				<td class="blue-text text-darken-2">
					장애명
				</td>
				<td>
					<input type="text" value="${memberVO.disabled_name}"
						name="disabled_name" />
				</td>
			</tr>
			<tr>
				<td class="blue-text text-darken-2">
					장애번호
				</td>
				<td>
					<input type="number" value="${memberVO.disabled_number}"
					name="disabled_number" />
				</td>
			</tr>	
		</c:if>
	</table>
	<!-- 	 <div class="col s6 offset-s6"><span class="flow-text">6-columns (offset-by-6)</span></div>
 -->		
 	<div class="row">
	 		<button class="btn waves-effect waves-light light-blue darken-4" type="submit" name="action" style="margin: 20px 0 20px 0; float:right; ">수정하기
	    		<i  class="material-icons right">send</i>
	  		</button>
	 </div>
	 <input type="hidden" value="${memberVO.pw }" name="pw"/>
	</form>
	</div>
	<%@ include file="../include/footer.jsp"%>
	
</body>

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

</html>