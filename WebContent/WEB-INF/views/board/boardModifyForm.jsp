<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardModifyForm</title>
</head>
<body>
	<h1>boardModifyForm</h1>
	<form action="boardModifyPro.vol" method="post">
		<input type="hidden" name="id" value="sessionId" />
		<input type="hidden" name="views" value="${vo.views }" />
		<input type="hidden" name="bbsno" value="${vo.bbsno }" />
		<table>
			<tr>
				<td>category</td><td><input type="text" name="categor" value="${vo.categor }" /></td>
			</tr>
			<tr>
				<td>subject</td><td><input type="text" name="subject" value="${vo.subject }" /></td>
			</tr>
			<tr>
				<td>content</td><td><input type="text" name="content" value="${vo.content }" /></td>
			</tr>
			<tr>
				<td>link01</td><td><input type="text" name="link01" value="${vo.link01 }" /></td>
			</tr>
			<tr>
				<td>file01</td><td><input type="text" name="file01" value="${vo.file01 }" /></td>
			</tr>
			<tr>
				<td>file02</td><td><input type="text" name="file02" value="${vo.file02 }" /></td>
			</tr>
		</table>
		<input type="submit" value="수정" />
	</form>
</body>
</html>