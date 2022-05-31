<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <!--url로는 localhost:8888/action/views/2_StandardAction/02_forward.jsp-->
    <h1>여기는 02_forward.jsp 페이지야</h1>
    <!--
        forward의 특징 상 url은 그대로고 화면만 바뀐다
        응답뷰 지정시 request.getRequestDispather("경로").forward(request, response);에 해당
    -->
    
    <jsp:forward page="footer.jsp"/>
</body>
</html>