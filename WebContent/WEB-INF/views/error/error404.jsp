<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404에러</title>
<%@ include file="../include/include.jsp" %>
</head>
<body style="text-align:center; margin:auto; ">
   
    <nav class="light-blue darken-4" style="margin-bottom: 20px;">
       <div class="nav-wrapper">
         <a href="http://localhost:8081/volunteer/main_join/main.vol" class="brand-logo ">  <i class="material-icons" style="font-size: 50px">&nbsphome</i></a>
         <ul id="nav-mobile" class="right hide-on-med-and-down ">
           <li><a href="/volunteer/board/boardList.vol" class="">게시판</a></li>
           <li><a href="/volunteer/sponsor.vol" class="">후원하기</a></li>           
         </ul>
       </div>
    </nav>
    <div class="container">
        <img src="/volunteer/img/bono404.png" style="width:100%; height:700px;"  />
      </div>      
<%@ include file="../include/footer.jsp" %>
</body>
</html>