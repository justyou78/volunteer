<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>간략정보</title>
<%@ include file="../include/include.jsp" %>
<style>
	#div_star{
		font-size: 12px;	
		width: 110px;
		text-align: center;
	}
	#star{
		font-size: 10px;

	}
	
	
</style>

</head>
<body>
  <%--  이름 >> ${vo.name }<br />
   나이 >> ${vo.age }<br />
   성별 >> ${gender }<br />
   사진 >> <img src="${vo.picture }" style="width:50px; height:50px;">
   
    --%>
    <!-- 이름 우측에 화살표 만들어주자 -->
   <div class="grey lighten-3"style="width: 100%;height: 150px;">
		<div class="light-blue darken-4 white-text valign-wrapper" > <i class="material-icons offset">perm_identity</i>${vo.name }(${vo.age }세,${gender } )      </div>
		<div class="valign-wrapper" style="height: 120px;"> 
		
		<img class="circle" src="${vo.picture }" style="width:100px; height:80px;margin-right: 8px;">
		 <div id="div_star" >
		&nbsp;봉사시간&nbsp;	 <strong style="font-size: 14px;">${vo.total_vol_time } </strong>시간<br/> 
		 <hr>
		 &nbsp;
		 <c:if test="${vo.point ==null || vo.point==''  }">
		 	<span class="red-text">정보가 없습니다. </span>
		 </c:if>
		<c:if test="${vo.point != null && vo.point != '' }">
		 	
		 	
		 	<c:set var="star" value="${vo.point-(vo.point%1)}"></c:set>
			<c:set var="star_point" value="${vo.point - star }"></c:set>
			<c:set var="star_count" value="0"></c:set>
			
		
		 	<c:set var="blank_star" value="${vo.point / 1 }"></c:set>
		 	<c:forEach begin="1" end="${star }" step="1">
		 		<i class="material-icons red-text " id="star">star</i>
		 	
		 		<c:set var="star_count" value="${ star_count+1 }"></c:set>
		 	</c:forEach>
		 	<c:if test="${star_point >= 0.5 }">
		 		<i class="material-icons red-text" id="star">star_half</i>
		 			<c:set var="star_count" value="${ star_count+1 }"></c:set>
		 	</c:if>
		 	<c:forEach begin="1" end="${5-star_count }" step="1"> 
		 		
		 		<i class="material-icons red-text" id="star">star_border</i>
		 		
		 	</c:forEach>
		 </c:if>
		 <br/>
		
		 <div style="text-align:center; margin-top: 5px;"><a class="light-blue darken-4 btn-small" onclick="newopen(${vo.id})">상세정보</a></div>
		 </div>
		 
		
		 
		 
		</div>
	
  </div>
  
  
  
</body>
	<script>
	
	</script>
	
</html>