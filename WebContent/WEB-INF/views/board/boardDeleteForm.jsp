<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>boardDeleteForm</title>
	<%@ include file="../include/include.jsp" %>
</head>
<body class="grey lighten-3">
	<%@ include file="../include/vol_include.jsp" %>
	<div class="row" style="margin-bottom: 50px;">
	<div class="col s3"></div>
	<div class="col s6">
	<h3 class="center-align">게시판</h3>
	삭제할려면 '삭제'를 입력하세요. <br />
	<input type="text" name="check" id="delete" value="삭제"/> <br />
	<div class="row" style="margin: 10px;">
		<div class="col s6 left-align">
		<input class="btn light-blue darken-4" type="button" value="목록" onclick="location='boardList.vol'" />
		</div>
		<div class="col s6 right-align">
		<input class="btn light-blue darken-4" type="button" value="삭제" onclick="checkForm();" />
		</div>
	</div>
	</div>
	<div class="col s3"></div>
	</div>
	<%@ include file="../include/footer.jsp" %>
	<script>
	function checkForm() {
		var del = $("#delete").val();
		if(del != '삭제'){
			alert('삭제라고 입력해주세요.');
			return;
		}
		
		location='boardDeletePro.vol?num=${num }';
			
	
	
	}
</script>
</body>

</html>