<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/include.jsp"%>
<%@ include file="../include/vol_include.jsp"%>

<style>

	#mother { text-align: center; }
			img { height: 600px;}
		</style>

</head>

<body class="grey lighten-3">
	<c:if test="${auth !=null }">
		<script>
			window.alert('${auth}');
		</script>
		
	
	</c:if>
	<div class="row">
	<form action="/volunteer/volunteer/main.vol" method="post">
	
		<div class="col s9">
		
		
		<input type="date" name="sysdate"
			class="waves-effect waves-light  col s6 m4 white"
			style="height: 40px;" />
		<button
			class="btn waves-effect waves-light light-blue darken-4"
			type="submit" name="action">
			찾기 <i class="material-icons right">send</i>
		</button>
		<br/>
		<div id="mother">
		
				<c:if test="${imgName == null || imgName == '' }">
				<img src="/volunteer/img/b.png"  />
			</c:if>
			<c:if test="${imgName != null && imgName != '' }">
				<img src="/volunteer/img/graphimg/${imgName}" style="width: 100%" />
			</c:if>
		</div>
		
			
			
				
		
		
		
		<table class="highlight " style="text-align: right;">
			<thead class="light-blue darken-4 white-text text-darken-2">
				<tr>
					<th>봉사자</th>
					<th>장애인</th>
					<th>봉사시간</th>

					<th>내용</th>
					<th>봉사끝난시간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="vo" items="${list }">

					<tr class=>

						<td>${hm[vo.vol_id]}</td>
						<td>${hm[vo.disabled_id]}</td>
						<td>${vo.vol_time }</td>
						<td>${vo.content }</td>
						
						<td><fmt:formatDate value="${vo.time}" pattern="yyyy년 MM월 dd일 HH시 mm분" /></td>
					</tr>
				</c:forEach>
			</tbody>





		</table>
		
		
		</div>
		<div class="col s3 ">
		
		
		
		
		
		
		<ul class="collapsible">
    <li>
      <div class="collapsible-header "  ><i class="material-icons">favorite</i>후원왕!</div>
      <div class="collapsible-body white"><table id="rank" >
			
			<c:set var="count" value="1"></c:set>
			<c:forEach var="donationVO" items="${donationList}">
				<tr>
					<td style=""><c:if test="${ count == 1}">
							<i class="material-icons">looks_one</i>
						</c:if> <c:if test="${count == 2 }">
							<i class="material-icons">looks_two</i>
						</c:if> <c:if test="${count != 1 && count != 2 }">
							<i class="material-icons">looks_${count }</i>
						</c:if></td>

					<td style=""><c:if
							test="${donationVO.payment_method_type==null || donationVO.payment_method_type=='' }">
							<div class="chip white">
								<img src="/volunteer/img/bono.png" alt="">
								${donationVO.item_name} ${donationVO.total}원
							</div>
						</c:if> <c:if test="${donationVO.payment_method_type != null }">
							<div class="chip white">
								<img src="${donationVO.payment_method_type}" alt="">
								${donationVO.item_name} ${donationVO.total}원
							</div>

						</c:if></td>


				</tr>
				<c:set var="count" value="${count + 1}" />
			</c:forEach>
		</table></div>
    </li>
    <li>
      <div class="collapsible-header "><i class="material-icons ">favorite_border</i>봉사왕!</div>
      <div class="collapsible-body white">	<table id="rank"  class="table">
			
          
		
			<c:set var="count" value="1"></c:set>
			<c:forEach var="volListVO" items="${volList}">
				<tr>
					<td style=""><c:if test="${ count == 1}">
							<i class="material-icons">looks_one</i>
						</c:if> <c:if test="${count == 2 }">
							<i class="material-icons">looks_two</i>
						</c:if> <c:if test="${count != 1 && count != 2 }">
							<i class="material-icons">looks_${count }</i>
						</c:if></td>

					<td style=""><c:if
							test="${volListVO.picture==null || volListVO.picture=='' }">
							<div class="chip white">
								<img src="/volunteer/img/bono.png" alt="">
								${volListVO.name} ${volListVO.total_vol_time}시간
							</div>
						</c:if> <c:if test="${volListVO.picture != null }">
							<div class="chip white">
								<img src="${volListVO.picture}" alt="">
								${volListVO.item_name} ${volListVO.total_vol_time}시간
							</div>

						</c:if></td>


				</tr>
				<c:set var="count" value="${count + 1}" />
			</c:forEach>
		</table></div>
    </li>
  </ul>
		
		</div>
		</div>
	</form>
<%@ include file="../include/footer.jsp"%>
</body>



<script>
	document.addEventListener('DOMContentLoaded', function() {
		var elems = document.querySelectorAll('.carousel');
		var carousel = M.Carousel.init(elems);
		  var elems = document.querySelectorAll('.collapsible');
		    var instances = M.Collapsible.init(elems);
	});

	// Or with jQuery

	$(document).ready(function() {
		$('.carousel').carousel();
		 $('.collapsible').collapsible();
	});
</script>


</html>