<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>마커 클러스터러에 클릭이벤트 추가하기</title>
    
</head>
<body>
<p style="margin-top:-12px">
     사용한 데이터를 보시려면
    <em class="link">
       <a href="/volunteer/web/data/chicken.json" target="_blank">여기를 클릭하세요!</a>
    </em>
</p>
<div id="map" style="width:100%;height:350px;"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=494e02c4821bde94e76161ca7fd542b2&libraries=clusterer"></script>
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = { 
	    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	    level: 3 // 지도의 확대 레벨
	};
	
	//지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	var geocoder = new kakao.maps.services.Geocoder();

	var callback = function(result, status) {
	    if (status === kakao.maps.services.Status.OK) {
	        console.log(result);
	    }
	};

	geocoder.addressSearch('해남군 송지면', callback);
</script>

</body>
</html>