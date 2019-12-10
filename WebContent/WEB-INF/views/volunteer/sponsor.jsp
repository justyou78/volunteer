<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="kakaoPay.vol" method="post" name="kakao">
			후원 금액을 적어주세요 !<input type="text"  name="money" /> 
		
			<img src="/volunteer/img/payment_icon_yellow_small.png" onclick="kakaopay()">
        </form>
    
</body>

<script>
function kakaopay(){
    var form = document.kakao;
    
    form.submit();
}
</script>
</html>