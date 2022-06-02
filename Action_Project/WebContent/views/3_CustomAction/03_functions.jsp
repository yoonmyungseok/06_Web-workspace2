<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <h1>JSTL Functions Library</h1>
    <p>
        - EL 구문 안에서 사용할 수 있는 메소드들을 제공 <br>
        - 주로 문자열과 관련된 메소드들을 제공함<br>
        - 태그형식이 아니라 EL구문 형식
    </p>
    <!-- 테스트데이터 셋팅-->
    <c:set var="str" value="How are you?"/>
    str: ${str} <br>
    문자열의 길이: ${fn:length(str)}<br> <!--functions library 방식-->
    문자열의 길이: ${str.length()} <br> <!--자바 방식-->

    <%
        //ArrayList 생성
        ArrayList<String> list=new ArrayList<>();
        list.add("안녕");
        list.add("하세요");
        list.add("반가워요");

        request.setAttribute("list",list);
    %>
    리스트의 길이: ${fn:length(list)} <br> <!--functions library 방식 -->
    리스트의 길이: ${list.size()} <br> <!--자바방식-->
    <!--fn:length 메소드는 문자열의길이 뿐만 아니라 ArrayList의 사이즈도 알아낼 수 있는 메소드다 -->

    모두 대문자로 출력: ${fn:toUpperCase(str)} <br>
    모두 소문자로 출력: ${fn:toLowerCase(str)} <br>

    are의 시작 인덱스: ${fn:indexOf(str, 'are')} <br>
    <!--EL 표기법에서 문자열 리터럴을 다룰 때에는 홑따옴표도 사용 가능하다-->
    are을 were로 변환 : ${fn:replace(str,'are','were')} <br>

    str: ${str} <br>
    <!--원본값이 바뀌는 것은 아님-->

    str에 "are"이라는 문자열이 포함되어있나? : ${fn:contains(str, 'are')} <br>
    <!--응용-->
    str에 "are"이라는 문자열이 포함되어 있니? : 
    <c:if test="${fn:contains(str, 'are')}">
        포함되어 있음
    </c:if>

</body>
</html>