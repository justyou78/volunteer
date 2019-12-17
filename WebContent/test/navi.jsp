<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width"/>
<title>API Demo - Kakao JavaScript SDK</title>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

</head>
<body>
<a id="navi" href="#" onclick="navi();">
<img src="/assets/img/about/buttons/navi/kakaonavi_btn_medium.png"/>
</a>
<script type='text/javascript'>
  //<![CDATA[
    // 사용할 앱의 JavaScript 키를 설정해 주세요.
    Kakao.init('494e02c4821bde94e76161ca7fd542b2');
    // 카카오 로그인 버튼을 생성합니다.
    function navi(){
        Kakao.Navi.start({
            name: "현대백화점 판교점",
            x: 127.11205203011632,
            y: 37.39279717586919,
            coordType: 'wgs84'
        });
    }
  //]]>
</script>

</body>
</html>