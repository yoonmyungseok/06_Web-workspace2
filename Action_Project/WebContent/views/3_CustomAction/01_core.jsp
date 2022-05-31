<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <!--taglib 지시어 누락 또는 prefix, uri 속성 오타 시: Unknown Tag 오류 발생!!-->
    <h1>JSTL Core Library</h1>
    <h3>1. 변수(속성)</h3>
    <pre>
        * 변수선언 (&lt;c:set var="변수명" value="리터럴" scope="스코프영역지정(생략가능)"&gt;)
        -변수를 선언하고 초기값을 대입해두는 기능을 제공
        -해당 변수를 어떤 scope에 담아둘건지 지정 가능함
        (생략 시 기본적으로 pageScope에 담김)
        =>즉, 해당 scope 영역에 setAttribute("키", 밸류) 메소드가 내부적으로 실행되는 원리
        =>c:set을 통해 선언된 변수는 EL로 접근해서 사용 가능함

        * 주의사항
        -EL로 접근해서 사용 가능하므로, 스크립팅 원소를 이용하여 가져다 쓰고 싶은 경우에는 getAttribute 메소드를 꼭 써야함
        -변수의 타입을 별도로 지정하지는 않음
        -반드시 해당 변수의 담아두고자하는 초기값 속성은 무조건 셋팅해야함(즉, 선언과 동시에 초기화)
    </pre>
    <c:set var="num1" value="10" />
    <!--
        pageScope에 num1이라는 키값으로 10이라는 값이 담긴 상태
        =>pageContext.setAttribute("num1",10); 구문과 동일한 역할
    -->
    <c:set var="num2" value="20" scope="request" />
    <!--
        requestScope에 num2라는 키값으로 20이라는 값이 담긴 상태
        =>request.setAttribute("num2", 20); 구문과 동일한 역할
    -->
    num1 변수값: ${num1} <br>
    num2 변수값: ${num2} <br>
    
    <%= request.getAttribute("num2") %> <br>

    <c:set var="result" value="${num1+num2}" scope="session" />
    <!--
        sessionScope에 result라는 키값으로 num1 값과 num2값을 합친 결과물이 담긴 상태
        session.setAttribute("result", (int)pageContext.getAttribute("num1")+(int)request.getAttribute("num2")); 
    -->
    result 변수값: ${result} <br><br>

    <!--
        키값만 제시하면 공유범위가 가장 작은곳부터 찾아지게 됨
        : 티가 나지는 않지만 속도가 좀 느려질 수 있다 (스코프영역.키값을 권장)
    -->
    ${pageScope.num1} <br>
    ${requestScope.num2} <br>
    ${requestScope.result} <br>
    ${sessionScope.result} <br><br>

    <c:set var="result" scope="request">
        9999
    </c:set>
    <!-- value 속성 말고 시작태그와 종료태그 사이에 값을 기술도 가능-->
    ${requestScope.result}

    <hr>
    <pre>
        * 변수 삭제(&lt;c:remove var="제거하고자하는 변수명" scope="스코프 영역지정(생략가능)"&gt;)
        - 해당 변수를 scope에서 찾아서 제거해주는 태그
        - scope 지정 생략 시 모든 scope에서 해당 변수를 찾아서 전부 삭제
        =>즉, 해당 scope에 .removeAttribute("키")라는 메소드를 이용해서 제거하는 것
    </pre>
    삭제 전: ${result} <br><br>
    1) 특정 scope를 지정해서 삭제 <br>
    <c:remove var="result" scope="request" />
    request에서 삭제 후 result : ${result} <br><!-- 이 시점에서는 sessionScope에 담긴 값이 찍힐것임-->
    <!--
        request.removeAttribute("result");
    -->

    2) 모든 scope에서 삭제(scope 속성 생략) <br> 
    <c:remove var="result" />
    삭제 후 result: ${result} <br><br>
    <!--
        pageContext.removeAttribute("result");
        request.removeAttribute("result");
        session.removeAttribute("result");
        application.removeAttribute("result");
    -->
    <hr>
    <pre>
        * 변수 출력 (&lt;c:out value="출력하고자하는 값" default="기본값(생략가능)" escapeXml="true(기본값)/false&gt;)
        -데이터를 출력하고자 할 때 사용하는 태그
        -default: value에 출력하고자 하는 값이 없을 경우 기본값으로 출력할 내용물을 기술(생략 가능)
        -escapeXml: 태그로써 해석해서 출력할지의 여부 (생략 가능, 생략 시 true가 기본값)
    </pre>
    result: <c:out value="${result}" /> <br>
    default 설정한 result: <c:out value="${result}" default="없음" /> <br><br>

    <!--escapeXml 테스트-->
    <c:set var="outTest" value="<b>출력테스트</b>"/>
    <c:out value="${outTest}"/> <br>
    <!--escapeXml 생략 시 기본값이 true==태그해석안됨 (문자열로 취급)-->
    <c:out value="${outTest}" escapeXml="false" />

    <hr>

    <h3>2. 조건문</h3>
</body>
</html>