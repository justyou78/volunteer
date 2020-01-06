<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환영합니다!</title>
<%@ include file="../include/include.jsp" %>

   <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    
    <!-- icon -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>
   
<body class="grey lighten-3">
   <header></header>
   <section>
   <!-- 슬라이드쇼 -->
   <div class="slider">
    <ul class="light-blue darken-4 slides">
      <li>
        <img src="/volunteer/img/jange1.png">
        <div class="caption left-align">
          <h3>세상을 똑바로 정면으로 바라보십시오.</h3>
          <h5 class="light grey-text text-lighten-3">- 헬런 켈러 -</h5>
        </div>
      </li>
      <li>
        <img src="/volunteer/img/jange2.png">
        <div class="caption left-align">
          <h3>고난의 시기에 동요하지 않는 것, 이것은 진정 칭찬받을 만한 뛰어난 인물의 증거다.</h3>
          <h5 class="light grey-text text-lighten-3">- 베토벤 -</h5>
        </div>
      </li>
      <li>
        <img src="/volunteer/img/jange3.png">
        <div class="caption left-align">
          <h3>사막이 아름다운 것은 어딘가에 샘이 숨겨져 있기 때문이다.</h3>
          <h5 class="light grey-text text-lighten-3">- 생떽쥐베리 -</h5>
        </div>
      </li>
      <li>
        <img src="/volunteer/img/jange4.png">
        <div class="caption left-align">
          <h3> 최고에 도달하려면 최저에서 시작하라.</h3>
          <h5 class="light grey-text text-lighten-3">- P.시루스 -</h5>
        </div>
      </li>
    </ul>
   </div>
    <br/>
    
    
    <!-- 장애인 봉사자 버튼 -->
    <div style="text-align: center; margin-bottom: 20px;">   
       <br/>
       
        <c:if test="${id !=null }">

    	 <a class="light-blue darken-4 waves-effect waves-light btn-large" href="/volunteer/main_join/join.vol?page=2"><i class="material-icons right">accessible</i>장애인</a>
    	 <a class="light-blue darken-4 waves-effect waves-light btn-large" href="/volunteer/main_join/join.vol?page=1"><i class="material-icons right">group</i>봉사자</a>
    	
	    </c:if>    
	    <c:if test="${id ==null }">
	    	
	    	 <a class="light-blue darken-4 waves-effect waves-light btn-large" href="/volunteer/main_join/login.vol"><i class="material-icons right">accessible</i>장애인</a>
    	 <a class="light-blue darken-4 waves-effect waves-light btn-large" href="/volunteer/main_join/login.vol"><i class="material-icons right">group</i>봉사자</a>
    	
	    </c:if>    
       
         <br/>
         <br/>
    </div>
      <c:if test="${isLogin !=null}">
         
         <script>
            window.alert('${isLogin}');
         </script>
      </c:if>
          
   
     
      </section>
      <footer>
    <%@ include file="../include/footer.jsp" %>
      </footer>
</body>
   <script type="text/javascript">
      document.addEventListener('DOMContentLoaded', function() {
      var elems = document.querySelectorAll('.slider');
      var instances = M.Slider.init(elems);
      
      if(${moneySuccess == 'success'}){
    	  alert("기부가 성공적으로 마무리되었습니다.");
      }
      else if(${moneySuccess == 'fail'}){
    	  alert("기부가 정상적으로 수행되지 않았습니다 다시 시도 해주세요..");
      
      }
      else if(${moneySuccess == 'cancel'}){
    	  alert("기부가 취소되었습니다.");
      
      }
      });
      
      
      // Or with jQuery
      
      $(document).ready(function(){
         $('.slider').slider({ 
               indicators: false, // default - indicators : true
               height: 600, // default - height : 400
               duration: 1000, // default - duration : 500
               interval: 4000 // default - interval : 6000
           });
      });
   </script>
</html>