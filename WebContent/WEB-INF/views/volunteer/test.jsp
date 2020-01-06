<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp" %>
<%@ include file="../include/vol_include.jsp" %>
</head>
<style>
	#rank{
		float: right;
	}
	
</style>

<body>
	<c:if test="${auth !=null }">
		<script>
				window.alert('${auth}');
			
		</script>
		
	</c:if>
	<!-- <a href="change_info.vol">회원정보수정</a>
	<a href="/volunteer/board/boardList.vol">게시판</a>
	<a href="sponsor.vol">후원하기</a> -->
	<form action="/volunteer/volunteer/main.vol" method="post" >
	
	
	<input type="date"  name="sysdate" /> <br/>
	<table class="col s6">
		<tr>
			<td>봉사자</td>
			<td>장애인</td>
			<td>봉사시간</td>
			
			<td>내용</td>
			<td>봉사끝난시간</td>
		</tr>	
		<c:forEach var="vo" items="${list }">
		
		<tr>
			
			<td> ${hm[vo.vol_id]}</td>
			<td>${hm[vo.disabled_id]} </td>
			<td>${vo.vol_time } </td>
			<td>${vo.content } </td>
			<td>${vo.time } </td>
		</tr>
		</c:forEach>
		
		
		
		
	
	</table>
	
	<table  class="col s6"  id="rank" style="width: 30%">
		<tr>
			<td>랭킹</td>
			<td>후원자</td>
		</tr>	
		<c:set var="count" value="1"></c:set>
		<c:forEach var="donationVO" items="${donationList}">
		<tr>
			<td style="width: 40px;">
				<c:if test="${ count == 1}">
				<i class="material-icons">looks_one</i>
				</c:if>
				<c:if test="${count == 2 }">
				<i class="material-icons">looks_two</i>
				</c:if>
				<c:if test="${count != 1 && count != 2 }">
				<i class="material-icons">looks_${count }</i>
				</c:if>
				
				
			</td>
			
			
			<c:if test="${donationVO.payment_method_type==null || donationVO.payment_method_type=='' }" >
					<td>
					<div class="chip">
					<img src="/volunteer/img/bono.png" alt="">
   				 ${donationVO.item_name} ${donationVO.total}원 
  					</div>
  				</td>
			</c:if>
			<c:if test="${donationVO.payment_method_type != null }"  >
				<td>
					<div class="chip">
					<img src="${donationVO.payment_method_type}" alt="">
   				 ${donationVO.item_name} ${donationVO.total}원 
  					</div>
  				</td>
			</c:if>
    		
  			
		</tr>
		<c:set var="count" value="${count + 1}" />
		</c:forEach>
	</table>
	<input type="submit" value="찾기"/>
	</form>


	<img src="/volunteer/img/graphimg/${imgName}"/>
	
</body>



	<script>
	document.addEventListener('DOMContentLoaded', function() {
	    var elems = document.querySelectorAll('.carousel');
	    var instances = M.Carousel.init(elems, options);
	  });

	  // Or with jQuery

	  $(document).ready(function(){
	    $('.carousel').carousel();
	  });
	</script>
	

</html>