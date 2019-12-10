<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardDeleteForm</title>
</head>
<body>
	<h1>boardDeleteForm</h1>
	삭제할려면 '삭제'를 입력하세요. <br />
	<input type="text" name="check" value="삭제"/> <br />
	<input type="button" value="삭제" onclick="location='boardDeletePro.vol?num=${num }'" />
</body>
</html>