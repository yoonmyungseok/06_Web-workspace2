<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <h1>1. formatNumber</h1>
    <p>
        - 숫자데이터의 포맷(형식)을 지정 <br>
        - 표현하고자 하는 숫자 데이터의 형식을 통화기호, % 등의 원하는 쓰임에 맞게 지정하는 태그 <br><br>

        [표현법]
        &lt;fmt:formatNumber value="포맷을지정하고자하는값" groupingUsed="true/false(생략가능)" type="percent/currency(생략가능)" currencySymbol="$(생략가능)"&gt;
        =>groupingUsed: 3자리마다 ,콤마로 구분짓는 역할 (생략 시 true가 기본값) <br>
        =>type: percent(소수점을 백분율로 변경해서) / currency(통화기호를 붙여서 보여주는 역할, 자동으로 로컬단위가 붙음)
        =>currencySymbol: 통화기호의 단위 지정 <br>
    </p>
    <!--테스트데이터 세팅-->
    <c:set var="num1" value="123456789"/>
    <c:set var="num2" value="0.75"/>
    <c:set var="num3" value="50000"/>
    그냥 출력: ${num1} <br>
    세자리마다 구분하여 출력: <fmt:formatNumber value="${num1}"/> <br>
    숫자 그대로 출력: <fmt:formatNumber value="${num1}" groupingUsed="false"/><br>
    <!--
        3자리마다 기본적으로 ,가 찍혀있음
        groupingUsed 속성의 기본값은 true, 3자리마다 구분지어주는 역할
    -->
    <br>
    그냥 출력: ${num2} <br>
    percent: <fmt:formatNumber value="${num2}" type="percent"/>
    <!--
        type="percent": 소숫점을 백분율로 변경해서 보여줌
    -->
    <br>
    그냥 출력: ${num3} <br>
    currency: <fmt:formatNumber value="${num3}" type="currency"/> <br>
    <!--
        type="currency": 통화(돈) 단위로 보여짐
                        현재 내가 쓰고있는 컴퓨터의 로컬 정보에 따라서 돈의 단위가 정해짐
                        +groupingUsed 속성이 기본값으로 true이기 때문에 3자리마다 구분되어있음
    -->
    currency $: <fmt:formatNumber value="${num3}" type="currency" currencySymbol="$" />
    <!--
        currencySymbol: 통화기호 문자의 종류를 지정
    -->

    <h1>2. formatDate</h1>
    <p>
        - 날짜 및 시간 데이터의 포맷(형식) 지정 <br>
        - 단, 무조건 날짜 형식(java.util.Date)으로 value를 지정해야 한다
    </p>
    <!--테스트 데이터 셋팅-->
    <c:set var="current" value="<%=new java.util.Date() %>" />

    그냥 출력: ${current} <br>
    <ul>
        <li>
            현재 날짜: <fmt:formatDate value="${current}" type="date"/>
            <!-- type="date" : 날짜를 출력하겠다. type 속성 생략 시 기본값은 "date"-->
        </li>
        <li>
            현재 시간: <fmt:formatDate value="${current}" type="time"/>
            <!--type="time" : 시간을 출력하겠다 -->
        </li>
        <li>
            현재 날짜 및 시간: <fmt:formatDate value="${current}" type="both"/>
            <!--type="both" : 날짜와 시간 둘다 출력하겠다. -->
        </li>
        <li>
            medium: <fmt:formatDate value="${current}" type="both" dateStyle="medium" timeStyle="medium" />
            <!--기본 길이 형식, medium이 기본값-->
        </li>
        <li>
            long: <fmt:formatDate value="${current}" type="both" dateStyle="long" timeStyle="long"/>
        </li>
        <li>
            full: <fmt:formatDate value="${current}" type="both" dateStyle="full" timeStyle="full"/>
            <!-- long보다도 더 긴 형식 -->
        </li>
        <li>
            short: <fmt:formatDate value="${current}" type="both" dateStyle="short" timeStyle="short"/>
        </li>
        <li>
            customizing: <fmt:formatDate value="${current}" type="both" pattern="yyyy-MM-dd(E) a HH:mm:ss"/>
            <!-- pattern 속성을 이용해서 커스터마이징이 가능하다 -->
        </li>
    </ul>
    <!--
        - 반드시 dateStyle과 timeStyle을 동일하게 맞출 필요는 없다
        - customizing 시 의미하는 것들
        yyyy: 년도 
        MM: 월
        dd: 일
        E: 요일
        a: 오전/오후
        HH: 시
        mm: 분
        ss: 초
    -->
</body>
</html>