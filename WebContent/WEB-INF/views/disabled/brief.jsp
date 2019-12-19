<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>간략정보</title>
</head>
<body>
   이름 >> ${vo.name }<br />
   나이 >> ${vo.age }<br />
   성별 >> ${gender }<br />
   사진 >> <img src="${vo.picture }" style="width:50px; height:50px;">
</body>
</html>