<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%@ include file="../include/include.jsp"%>

<style>

	.t1{
		font-size: 30px;
	}

</style>
</head>
<body class="grey lighten-3">

	<%@ include file="../include/vol_include.jsp"%>
	<div class="row center-align">
	
	<div class="col s4"></div>
	<div class="col s4">
	

	
	<div class="container_center">
		<form action="kakaoPay.vol" method="post" name="kakao">
			
			<i class="material-icons" style="font-size: 75px;">home</i><span style="font-size: 75px;">봉찾사</span><br/><br/>
			<span class="t1">많은 사람들을 돕겠습니다!</span><br/>
			<span class="t1">카카오페이로 간편하게 후원하세요.</span> <br/>
			<input type="text"  name="money" style="text-align: center; margin:20px auto 30px auto;  font-size: 20px;" placeholder="금액 입력란(원)"  /> 
			
		
			<img src="/volunteer/img/payment_icon_yellow_large.png" onclick="kakaopay()">
        </form>
    </div>
    </div>
    
    	<div class="col s4"></div>
    </div>
    	<%@ include file="../include/footer.jsp"%>
</body>

<script>
function kakaopay(){
	console.log('test');
    var form = document.kakao;
    var money=form.money.value;
    
    
    if(isNaN(money) || money == ""){
    	alert("비어있거나 숫자가 아닌 값이 있습니다. 확인해주세요.");
    	form.money.select();
    	
    }
    else{
    	
    	 form.submit();
    }
   
}
</script>
</html>