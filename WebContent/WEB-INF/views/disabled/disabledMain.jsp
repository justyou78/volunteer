<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<%@ include file="../include/include.jsp" %>

<script>
   var timerId = null;
   var timerId2 = null;
   var text = "";
   var static_id = "";
   var SetTime = 300;	
   var setting = "<tr><td>닉네임</td><td>성별</td><td>주소</td><td>전화번호</td><td>나이</td><td>연결</td></tr>"
   function ObjectFunc() {}
   ObjectFunc.exeConnect = function() {
      this.sendHelp();
      if(text=='success') {
         $('#connect_list').append(setting);
         timerId = setInterval(this.getConnect, 2000);
         timerId2 = setInterval(this.msg_timer, 1000);
         $("#ViewTimer").show();
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
            alert(retVal);

         },
         error : function(error) {
            console.log(error);
         }
      });

   }

   ObjectFunc.result = function(member) {
	   console.log('여기다여기!');
      this.StopClock();
      
      static_id=member.id;
      $("#sendHelp").hide();
      $("#wait").hide();
      $("#fail").hide();
      
      $("#connect_info").html('<p><strong>'+member.address+'</strong>의 <strong>' +member.name+'</strong>님과 연결되었습니다.</p>'
    		  + 	'<img class="circle" src="'+member.picture+'" style="width:100px; height:80px;margin-right: 8px;">'
    		  + ' <input type="text" placeholder="별점주기 ex) 3.5" name="give_star" id="give_star"/> '
    		  );
      
      
      $("#finish").show();
      
      this.resultMessage(member.id);
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
                                                   + JSON.stringify(member)
                                                   + ")' value='확인'</td></tr>");
                              });

               },
               error : function(error) {
                  console.log(error);
               }
            });

   }
   ObjectFunc.StopClock = function() {
	  this.delete_connect();
	  $("#ViewTimer").hide();
	  
      $("#sendHelp").show();
      $("#wait").hide();
      $("#fail").hide();
      $("#connect_list").html('');
      $("#connect_list").hide();
      $("#timer").hide();

      if (timerId != null) {
         clearInterval(timerId);
         SetTime=300;
         clearInterval(timerId2);

      }

   }
   
   ObjectFunc.msg_timer = function(){
	   
			
			
			m = Math.floor(SetTime / 60) + "분 " + (SetTime % 60) + "초";	// 남은 시간 계산;
			
			var msg = "현재 남은 시간은 <font color='red'>" + m + "</font> 입니다.";
			
			 $("#ViewTimer").html(msg);		// div 영역에 보여줌 
					
			SetTime--;					// 1초씩 감소
			
			if (SetTime < 0) {			// 시간이 종료 되었으면..
				this.StopClock();
				clearInterval(tid);		// 타이머 해제
				alert("종료");
			}
			
		


	   
	   
   }
   
   ObjectFunc.delete_connect = function(){
	   $.ajax({

	          url : 'delete_connect.vol',
	          type : 'POST',


	          contentType : 'application/json; charset=UTF-8',
			
	          success : function(result) {

	             console.log(result);

	          },
	          error : function(error) {
	            console.log(error);
	         }
	          

	       });
   }
   
   ObjectFunc.insert_vol= function(){
      
      $("#finish").hide();
      $("#sendHelp").show();
      $("#wait").hide();
      $("#fail").hide();
      $("#connect_list").html('');
      $("#connect_list").hide();
      
   
      var member = {

             "vol_id": static_id,
             "vol_time": $("#vol_time").val(),
             "content": $("#vol_job").val(),
             "disabled_id" : $("#give_star").val()

          };
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
<title>장애인페이지</title>
   <c:if test="${auth !=null }">
      <script>
         window.alert('${auth}');
      </script>
   </c:if>
</head>
<body class="grey lighten-3">
<%@ include file="../include/vol_include.jsp" %>
<p style="margin-top:-12px">
     사용한 데이터를 보시려면
    <em class="link">
       <a href="/volunteer/web/data/member.json" target="_blank">여기를 클릭하세요!</a>
    </em>
</p>

   
<div class="row">
<div class="col s3">
	
   <div class="row" style="text-align: center;">
      <form method="post" name="result">
       <div id="ViewTimer" style="display: none"   ></div>
         <table id="connect_list" style="display:none">      </table>
      </form>
      <form method="get" action="sendMessage.vol" name="sendHelp"   id="sendHelp">
         <input type="text" placeholder="작업내용" name="vol_job" id="vol_job"/> 
         <input type="text" placeholder="봉사시간" name="vol_time"  id="vol_time"/> 
         <input class="btn-large light-blue darken-4" type="button" value="도움요청" onclick="ObjectFunc.exeConnect()" style="font-size: 20px;"/>
         <p id="fail" style="display: none">주변에 사람이 없습니다.</p>
      </form>   
       <form name="finish" id="finish" style="display: none">
      	<div id="connect_info"></div>
         <input class="btn-large light-blue darken-4" type="button" value="봉사완료" onclick="ObjectFunc.insert_vol()" />
      </form>
      <input class="btn-large light-blue darken-4" type="button" value="도움취소" style="display: none"   onclick="ObjectFunc.StopClock();" id="wait" />
     
   </div>
   
   <div class="row" style="height: 220px; text-align: center; margin-left: 1px;">
      <span class="col s12 light-blue darken-4 white-text text-darken-2" style="font-size: 30px">FILTER</span>
      <!--  필터 기능   -->
      <div class="col s6">
      <label>
         <p><input class="with-gap" type="radio" name="gender" value="남자" checked/>
         <span class="black-text text-darken-2">남자</span></p>
      </label>
      <label>
         <p><input class="with-gap" type="radio" name="gender" value="여자" />
         <span class="black-text text-darken-2">여자</span></p>
      </div>
      <div class="col s6">
      </label>
      <label>
         <p><input class="with-gap" type="radio" name="age" value="10 19" checked/>
         <span class="black-text text-darken-2">10대</span></p>
      </label>
      <label>
         <p><input class="with-gap" type="radio" name="age" value="20 29" />
         <span class="black-text text-darken-2">20대</span></p>
      </label>
      <label>
         <p><input class="with-gap" type="radio" name="age" value="30 39" />
         <span class="black-text text-darken-2">30대</span></p>
      </label>
      <label>
         <p><input class="with-gap" type="radio" name="age" value="40 49" />
         <span class="black-text text-darken-2">40대</span></p>
      </label>
      </div>
   </div>
   <div style="height: 70px; text-align: center;">
      <a class="light-blue darken-4 btn-large" href="#" id="submit">submit</a>
      <a class="light-blue darken-4 btn-large" href="#" id="clear">clear</a>
   </div>
</div>
<div class="col s9">
   <!-- 맵 -->
   <div id="map" style="width: 100%; height: 700px;"></div>
</div>
</div>
  <%@ include file="../include/footer.jsp" %>
</body>
   
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=494e02c4821bde94e76161ca7fd542b2&libraries=clusterer"></script>
<script type="text/javascript">
	function newopen(id) {
		console.log(id);
		
	   window.open('http://192.168.0.48:8081/volunteer/commons/memberInfo.vol?id='+id, 'info', 'width=700, height=350');
	}
   // 제이슨에서 불러온 데이터로 마커 생성후 인포윈도우 생성
   function makeMarker(){
      $.get("/volunteer/web/data/member.json", function(data) {          
         var markers = $(data.positions).map(function(i, position) {
        	 if(position.type == 1){
              var marker = new kakao.maps.Marker({
                   position : new kakao.maps.LatLng(position.lat, position.lng)
               });
              var infowindow = null;
              var result = true;
              kakao.maps.event.addListener(marker, 'click', function() {
                 if(result){              
                  $.ajax({
                     url : "http://192.168.0.48:8081/volunteer/disabled/brief.vol?id="+position.id,
                     success : function(data){
                          var iwContent = data; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                          if(infowindow == null){
                              infowindow = new kakao.maps.InfoWindow({
                                 content : iwContent
                             });
                          }
                           infowindow.open(map, marker);
                           result = false;
                     }
                  });
                 }else{
                    infowindow.close();
                    result = true;
                 }
             });
               return marker;
        	 }
           });
         
           clusterer.addMarkers(markers);

      });
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
    makeMarker();

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
<script type="text/javascript">
// 필터 적용
$("#submit").click(function(){
   var gender = $(":input:radio[name=gender]:checked").val();
   console.log("성별 >> " + gender)
   var age1 = $(":input:radio[name=age]:checked").val().split(" ")[0];
   console.log("나이1 >> " + age1 + "부터");
   var age2 = $(":input:radio[name=age]:checked").val().split(" ")[1];
   console.log("나이2 >> " + age2 + "까지");
    clusterer.clear();
       $.get("/volunteer/web/data/member.json", function(data) {          
         var markers = $(data.positions).map(function(i, position) {
        	 if(position.type == 1){
            if(gender == position.gender && age1 <= position.age && age2 >= position.age){
            	
               var marker = new kakao.maps.Marker({
                    position : new kakao.maps.LatLng(position.lat, position.lng)
               });
                 var infowindow = null;
                 var result = true;
                 kakao.maps.event.addListener(marker, 'click', function() {
                    if(result){              
                     $.ajax({
                        url : "http://192.168.0.48:8081/volunteer/disabled/brief.vol?id="+position.id,
                        success : function(data){
                             var iwContent = data; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                              var iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
                             if(infowindow == null){
                                 infowindow = new kakao.maps.InfoWindow({
                                    content : iwContent,
                                    removable : iwRemoveable
                                });
                             }
                              infowindow.open(map, marker);
                              result = false;
                        }
                     });
                    }else{
                       infowindow.close();
                       result = true;
                    }
                });
                  return marker;
            }
        	 }
           });
         
           clusterer.addMarkers(markers);
           

      });
    

    
});
// 필터 초기화
$("#clear").click(function(){
    clusterer.clear();
   makeMarker();
});
  
</script>
</html>