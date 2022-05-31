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
    <h3>include</h3>
    <p>
        또 다른 페이지를 포함하고자 할 때 쓰이는 방식
    </p>
    <h4>1. 기존의 include 지시어를 이용한 방식(정적 include 방식==컴파일 시에 jsp파일이 포함되는 형태)</h4>
    <%--
    <%@ include file="footer.jsp" %>
    <br><br>
    특징: include 하고있는 페이지 상에 선언되어있는 변수를 현재 이 페이지에서도 사용 가능함<br>

    include한 페이지의 year라는 변수값: <%=year%> <br><br>

    단, 현재 이 페이지에서 동일한 이름의 변수를 선언할 수 없음 <br>
    <% String year="2022"; %>
    --%>
    <h4>2. JSP 표준 액션 태그를 이용한 방식(동적 include 방식==런타임 시에 jsp 파일이 포함되는 형태)</h4>
    <!--
        반드시 시작태그와 종료태그를 같이 써야 한다(XML 기술을 이용했으므로)
        단, 시작태그와 종료태그 사이에 넣을 값이 없다면 다음과 같이 생략해서 표현해도 된다.
    -->
    
    <jsp:include page="footer.jsp"/>

    <br><br>
    특징1: include 하고 있는 페이지에 선언된 변수를 공유하지 않음 (즉, 동일한 이름의 변수 선언 가능) <br>
    <%
        String year="2022";
    %>
    특징2: 포함 시 include 하는 페이지로 값을 전달할 수 있음 <br><br>
    <jsp:include page="footer.jsp">
        <jsp:param name="test" value="Hello..?" />
    </jsp:include>
    <br>
    <jsp:include page="footer.jsp">
        <jsp:param name="test" value="Goodbye!" />
    </jsp:include>
</body>
</html>