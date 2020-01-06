<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>boardWriteForm</title>
	<%@ include file="../include/include.jsp" %>
</head>
<body class="grey lighten-3">
	<%@ include file="../include/vol_include.jsp" %>
	<div class="row" style="margin-bottom: 50px;">
	<div class="col s3"></div>
	<div class="col s6">
	<h3 class="center-align">게시판</h3>
	<form action="boardWritePro.vol" method="post">
		<input type="hidden" name="views" value="0" />
		<table>
			<tr>
				<td>category</td>
				<td>
					<select name="categor">
						<option value="문의">문의</option>
						<option value="자유">자유</option>
						<option value="후기">후기</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>subject</td><td><input type="text" name="subject" id="subject"/></td>
			</tr>
			<tr>	
				<td>content</td><td></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea id="content" class="materialize-textarea" name="content"></textarea>
					<label for="content">HTML</label>
				</td>
			</tr>
			<tr>
				<td>link01</td><td><input type="text" name="link01" /></td>
			</tr>
			<tr>
				<td>file01</td><td><input type="text" name="file01" /></td>
			</tr>
			<tr>
				<td>file02</td><td><input type="text" name="file02"></td>
			</tr>
		</table>
		<div class="row" style="margin: 10px;">
			<div class="col s6 left-align">
			<input class="btn light-blue darken-4" type="button" value="목록" onclick="location='boardList.vol'" />
			</div>
			<div class="col s6 right-align">
			<input class="btn light-blue darken-4" type="submit" value="글쓰기">
			</div>
		</div>
	</form>
	</div>
	<div class="col s3"></div>
	</div>
	<%@ include file="../include/footer.jsp" %>
</body>
<script type="text/javascript">

	function checkForm() {
	   var subject = $('#subject').val();
	   var content = $('#content').val();
	   if(subject == ""){
	      alert("제목을 입력해주세요.");
	      return false;      
	   }
	   if(content == ""){
	      alert("내용을 입력해주세요.");
	      return false;      
	   }
	}
  document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('select');
    var instances = M.FormSelect.init(elems, options);
  });

  // Or with jQuery

  $(document).ready(function(){
    $('select').formSelect();
  });
</script>
<script type="text/javascript">
  $('#content').val('내용을 입력해주세요 뿌잉뿌잉');
  M.textareaAutoResize($('#content'));
</script>
</html>