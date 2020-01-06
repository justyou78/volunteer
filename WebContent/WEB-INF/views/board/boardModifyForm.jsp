<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>boardModifyForm</title>
	<%@ include file="../include/include.jsp" %>
</head>
<body class="grey lighten-3">
	<%@ include file="../include/vol_include.jsp" %>
	<div class="row" style="margin-bottom: 50px;">
	<div class="col s3"></div>
	<div class="col s6">
	<h3 class="center-align">게시판</h3>
	<form action="boardModifyPro.vol" method="post">
		<input type="hidden" name="views" value="${vo.views }" />
		<input type="hidden" name="bbsno" value="${vo.bbsno }" />
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
				<td>subject</td><td><input type="text" name="subject" value="${vo.subject }"/></td>
			</tr>
			<tr>	
				<td>content</td><td></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea id="textarea1" class="materialize-textarea" name="content">${vo.content }</textarea>
				</td>
			</tr>
			<tr>
				<td>link01</td><td><input type="text" name="link01" value="${vo.link01 }"/></td>
			</tr>
			<tr>
				<td>file01</td><td><input type="text" name="file01" value="${vo.file01 }"/></td>
			</tr>
			<tr>
				<td>file02</td><td><input type="text" name="file02" value="${vo.file02 }" /></td>
			</tr>
		</table>
		<div class="row" style="margin: 10px;">
			<div class="col s6 left-align">
			<input class="btn light-blue darken-4" type="button" value="목록" onclick="location='boardList.vol'" />
			</div>
			<div class="col s6 right-align">
			<input class="btn light-blue darken-4" type="submit" value="수정">
			</div>
		</div>
	</form>
	</div>
	<div class="col s3"></div>
	</div>
	<%@ include file="../include/footer.jsp" %>
</body>
<script type="text/javascript">
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
  $('#textarea1').val('${vo.content}');
  M.textareaAutoResize($('#textarea1'));
</script>
</html>