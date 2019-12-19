<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<%@ include file="../include/include.jsp"%>
<script>
	var timerId = null;
	var text = "";
	var static_id = "";
	var setting = "<tr><td>닉네임</td><td>성별</td><td>주소</td><td>전화번호</td><td>나이</td><td>연결</td></tr>"
	function ObjectFunc() {
		
	}
	ObjectFunc.exeConnect = function() {
		this.sendHelp();
		console.log(text);
		if(text=='success') {
			$('#connect_list').append(setting);
			console.log(text+'22');
			timerId = setInterval(this.getConnect, 2000);
			$("#sendHelp").hide();
			$("#wait").show();
			$("#fail").hide();
			$("#connect_list").show();
			
		} else if (text == 'fail') {
			$("#wait").show();
			$("#fail").show();
		}

	}

	ObjectFunc.sendHelp = function() {
		var form = document.sendHelp;

		$.ajax({
			url : "sendMessage.vol",
			dataType : "text",
			data : $("form").serialize(),
			async: false,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			type : "post",
			success : function(retVal) {
				text = retVal;
				console.log(retVal);
				alert(retVal);

			},
			error : function(error) {
				console.log(error);
			}
		});

	}

	ObjectFunc.result = function(id) {
		this.StopClock();
		static_id=id;
		console.log(id);
		$("#sendHelp").hide();
		$("#wait").hide();
		$("#fail").hide();
		$("#finish").show();
		this.resultMessage(id);
	}
	
	ObjectFunc.resultMessage = function(id){
		
		console.log(id);
		$.ajax({
			url:"resultMessage.vol",
			data: {id:id},
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			type:"post",
			success:function(retVal){
				alert('요청이 완료되었습니다. 봉사자가 오고있습니다.');
				
			},
			error:function(error){
				alert(error);
			}
		
	});
		
	
	}
	
	ObjectFunc.getConnect = function() {
		$.ajax({
					url : "/volunteer/disabled/getConnect.vol",
					dataType : "json",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					type : "post",
					success : function(retVal) {

						console.log(retVal);

						$.each(
										retVal.member,
										function(index, member) {
											var gender = "";

											if (member.gender == 1) {
												gender = '남자';

											} else {
												gender = '여자';
											}
											$('#connect_list')
													.append(
															"<tr><td name='name'>"
																	+ member.name
																	+ "</td><td name='gender'>"
																	+ gender
																	+ "</td><td>"
																	+ member.address
																	+ "</td>"
																	+ " <td>"
																	+ member.callnumber
																	+ "</td> <td>"
																	+ member.age
																	+ "</td> <td><input type='button' onclick='ObjectFunc.result("
																	+ member.id
																	+ ")' value='확인'</td></tr>");

										});

					},
					error : function(error) {
						console.log(error);
					}
				});

	}
	ObjectFunc.StopClock = function() {
		$("#sendHelp").show();
		$("#wait").hide();
		$("#fail").hide();
		$("#connect_list").html('');
		$("#connect_list").hide();

		if (timerId != null) {
			clearInterval(timerId);

		}

	}
	ObjectFunc.insert_vol= function(){
		
		$("#finish").hide();
		$("#sendHelp").show();
		$("#wait").hide();
		$("#fail").hide();
		$("#connect_list").html('');
		$("#connect_list").hide();
		
	
		console.log(static_id);
		console.log($("#vol_time").val());
		console.log($("#vol_job").val() );
		var member = {

	    		"vol_id": static_id,

	    		"vol_time": $("#vol_time").val(),
	    		"content": $("#vol_job").val()

	    	};
		console.log(member);
		$.ajax({

    		url : 'insert_vol.vol',

    		dataType : 'text',

    		type : 'POST',

    		data : JSON.stringify(member), 

    		contentType : 'application/json; charset=UTF-8',

    		success : function(result) {

    			console.log(result);

    		},
    		error : function(error) {
				console.log(error);
			}
    		

    	});
		
	}
</script>


<meta charset="utf-8">
<title>마커 클러스터러에 클릭이벤트 추가하기</title>

</head>
<body>
	<c:if test="${auth !=null }">
		<script>
			window.alert('${auth}');
		</script>

	</c:if>


	<p style="margin-top: -12px">
		사용한 데이터를 보시려면 <em class="link"> <a
			href="/volunteer/web/data/member.json" target="_blank">여기를
				클릭하세요!</a>
		</em>
	</p>
	
	
	<form name="finish" id="finish" style="display: none">
		<input type="button" value="봉사완료" onclick="ObjectFunc.insert_vol()" />
	</form>


	

	<form method="get" action="sendMessage.vol" name="sendHelp"
		id="sendHelp">
		<input type="text" placeholder="작업내용" name="vol_job" id="vol_job"/> <input
			type="text" placeholder="봉사시간" name="vol_time"  id="vol_time"/> <input
			type="button" value="도움 요청하기" onclick="ObjectFunc.exeConnect()" />

		<p id="fail" style="display: none">주변에 사람이 없습니다.</p>


	</form>

	<input type="button" value="응답대기중(취소)" style="display: none"
		onclick="ObjectFunc.StopClock();" id="wait" />


	<form method="post" name="result">
		<table id="connect_list" style="display:none">		</table>

	</form>

	   <!-- 맵 -->
   <div id="map" style="float: right; width: 80%; height: 700px;"></div>
   
   
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=494e02c4821bde94e76161ca7fd542b2&libraries=clusterer"></script>
<script type="text/javascript">
   function newopen(id) {
      window.open('http://192.168.0.48:8081/volunteer/disabled/memberInfo.vol?id='+id+'', 'info', 'width=600, height=600');
   }
</script>


<script>
   var x = ${x};
   var y = ${y};
   console.log("스크립트x >> " + x);
   console.log("스크립트y >> " + y);
   
    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng(y, x), // 지도의 중심좌표
        level : 6 // 지도의 확대 레벨
    });   

    // 마커 클러스터러를 생성합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom 값을 true로 지정하지 않은 경우
    // 클러스터 마커를 클릭했을 때 클러스터 객체가 포함하는 마커들이 모두 잘 보이도록 지도의 레벨과 영역을 변경합니다
    // 이 예제에서는 disableClickZoom 값을 true로 설정하여 기본 클릭 동작을 막고
    // 클러스터 마커를 클릭했을 때 클릭된 클러스터 마커의 위치를 기준으로 지도를 1레벨씩 확대합니다
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 10, // 클러스터 할 최소 지도 레벨
        disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
    });

    // 데이터를 가져오기 위해 jQuery를 사용합니다
    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
    $.get("/volunteer/web/data/member.json", function(data) {
       var markers = $(data.positions).map(function(i, position) {
           var marker = new kakao.maps.Marker({
                position : new kakao.maps.LatLng(position.lat, position.lng)
            });
           var iwContent = '<div style="padding:5px;">'
                +position.name+'<br/>'
                +position.gender+'<br/>'
                +position.age+'<br/>'
                +position.address+
                '<img src="'+position.img+'"/><input type="button" onclick="newopen('+position.id+')" value="상세정보"/></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                 iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

           var infowindow = new kakao.maps.InfoWindow({
               content : iwContent,
               removable : iwRemoveable
           });

           kakao.maps.event.addListener(marker, 'click', function() {
                 infowindow.open(map, marker);  
           });
            return marker;
        });
      
        clusterer.addMarkers(markers);
    });

    // 마커 클러스터러에 클릭이벤트를 등록합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
    // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
    kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {

        // 현재 지도 레벨에서 1레벨 확대한 레벨
        var level = map.getLevel()-1;

        // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
        map.setLevel(level, {anchor: cluster.getCenter()});
    });
</script>

<!--  필터 기능   -->
<input type="radio" name="gender" value="남자" />남자
<input type="radio" name="gender" value="여자" />여자 <br />
<input type="radio" name="age" value="10 20" />10대
<input type="radio" name="age" value="20 30" />20대 
<input type="radio" name="age" value="30 40" />30대 
<input type="radio" name="age" value="40 50" />40대 <br />

<button id="submit">submit</button>
<button id="clear">clear</button>
<script type="text/javascript">
$("#submit").click(function(){
   var gender = $(":input:radio[name=gender]:checked").val();
   console.log("성별 >> " + gender)
   var age1 = $(":input:radio[name=age]:checked").val().split(" ")[0];
   console.log("나이1 >> " + age1 + "부터");
   var age2 = $(":input:radio[name=age]:checked").val().split(" ")[1];
   console.log("나이2 >> " + age2 + "까지");
    clusterer.clear();
   $.ajax({
      url : "/volunteer/web/data/member.json",
      success : function(data){
         var markers = $(data.positions).map(function(i, position) {
            if(gender == position.gender && position.age >= age1 && position.age < age2){
                 var marker = new kakao.maps.Marker({
                      position : new kakao.maps.LatLng(position.lat, position.lng)
                  });
                 var iwContent = '<div style="padding:5px;">'
                      +position.name+'<br/>'
                      +position.gender+'<br/>'
                      +position.age+'<br/>'
                      +position.address+
                      '<img src="'+position.img+'"/><input type="button" onclick="newopen('+position.id+')" value="상세정보"/></div>', 
                       iwRemoveable = true; 
   
                 var infowindow = new kakao.maps.InfoWindow({
                     content : iwContent,
                     removable : iwRemoveable
                 });
   
                 kakao.maps.event.addListener(marker, 'click', function() {
                       infowindow.open(map, marker);  
                 });
                  return marker;
            }
           });
           clusterer.addMarkers(markers);
      }
   });
});
$("#clear").click(function(){
    clusterer.clear();
   $.ajax({
      url : "/volunteer/web/data/member.json",
      success : function(data){
         var markers = $(data.positions).map(function(i, position) {
              var marker = new kakao.maps.Marker({
                   position : new kakao.maps.LatLng(position.lat, position.lng)
               });
              var iwContent = '<div style="padding:5px;">'
                   +position.name+'<br/>'
                   +position.gender+'<br/>'
                   +position.age+'<br/>'
                   +position.address+
                   '<img src="'+position.img+'"/><input type="button" onclick="newopen('+position.id+')" value="상세정보"/></div>', 
                    iwRemoveable = true; 

              var infowindow = new kakao.maps.InfoWindow({
                  content : iwContent,
                  removable : iwRemoveable
              });

              kakao.maps.event.addListener(marker, 'click', function() {
                    infowindow.open(map, marker);  
              });
               return marker;
           });
           clusterer.addMarkers(markers);
      }
   });
});
</script>

</body>
</html>