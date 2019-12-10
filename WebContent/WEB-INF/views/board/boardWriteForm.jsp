<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardWriteForm</title>
</head>
<body>
	<h1>boardWriteForm</h1>
	<form action="boardWritePro.vol" method="post">
		<input type="hidden" name="id" value="sessionId" />
		<input type="hidden" name="views" value="0" />
		<table>
			<tr>
				<td>category</td><td><input type="text" name="categor" /></td>
			</tr>
			<tr>
				<td>subject</td><td><input type="text" name="subject" /></td>
			</tr>
			<tr>
				<td>content</td><td><input type="text" name="content" /></td>
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
		<input type="submit" value="글쓰기">
	</form>
</body>
</html>