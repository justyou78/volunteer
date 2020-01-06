<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/include.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberInfo</title>
</head>
<body class="light-blue darken-4 center-align" >
  <div class="grey lighten-3 col s12 m7">
    <div class="grey lighten-3 card horizontal" style="margin:0px;">
      <div class="grey lighten-3 card-image valign-wrapper">
        <img src="${vo.picture }" style="height:350px;">
      </div>
      <div class="grey lighten-3 card-stacked">
        <div class="grey lighten-3 card-content">
            <table>
            <tr><td>이름</td><td>${vo.name }</td></tr>
            <tr><td>나이</td><td>${vo.age }</td></tr>
            <tr><td>성별</td><td>${gender }</td></tr>
            <tr><td>이메일</td><td>${vo.email }</td></tr>
            <tr><td>주소</td><td>${vo.address }</td></tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</body>
</html>