<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kh.model.vo.Person"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <h1>EL 기본구문</h1>
    <h3>1. 기존 방식대로 스크립틀릿과 표현식을 이용해서 각 영역에 담겨있는 값 뽑아서 화면에 출력</h3>

    <%
        //requestScope에 담긴 값 뽑기
        String classRoom=(String)request.getAttribute("classRoom");
        Person student=(Person)request.getAttribute("student");
        
        //sessionScope에 담긴 값 뽑기
        String academy=(String)session.getAttribute("academy");
        Person teacher=(Person)session.getAttribute("teacher");
    %>
    <p>
        학원명: <%=academy%> <br>
        강의장: <%=classRoom%> <br>
        강사정보: <%=teacher.getName() %>, <%=teacher.getAge()%>, <%=teacher.getGender()%> <br>
        수강생정보
        <ul>
            <li>이름: <%=student.getName()%></li>
            <li>나이: <%=student.getAge()%></li>
            <li>성별: <%=student.getGender()%></li>
        </ul>
    </p>

    <hr>
    <h3>2. EL을 이용해서 보다 쉽게 해당 Scope에 저장된 값들 출력하기</h3>
    <p>
        EL은 getXXX를 통해 값을 빼올 필요 없이 키값만 제시하면 바로 내가 원하는 값에 접근 가능 <br>
        내부적으로 해당 Scope 영역에 해당 키 값의 밸류값을 가져올 수 있음 <br>

        기본적으로 EL은 JSP 내장 객체를 구분하지 않고 자동적으로 모든 내장객체를 
        범위가 작은 순부터 순차적으로 키값을 검색하여 존재하는 경우에만 값을 가져옴 <br>
    </p>
    <p>
        학원명: ${academy} <br>
        강의장: ${classRoom} <br>
        강사정보: ${teacher.name}, ${teacher.age}, ${teacher.gender} <br>
        <%--
            teacher라는 키값으로 접근했을 때 밸류값은 Person 객체임
            해당 Person 객체의 각 필드의 담긴 값을 출력하고자 한다면,
            .필드명으로 접근하면 됨
            내부적으로 getter 메소드를 찾아서 실행됨
            (즉, VO 클래스를 만들 경우 getter 메소드는 항상 만들어야 함)
        --%>
        수강생정보
        <ul>
            <li>이름: ${student.name}</li>
            <li>나이: ${student.age}</li>
            <li>성별: ${student.gender}</li>
        </ul>
    </p>
    <hr>
    <h3>3. 단, EL 사용시 내장 객체들에 저장된 키값이 동일할 경우</h3>
    scope 값: ${scope} <br>
    test 값: ${test} <br>
    <%--
        EL은 공유 범위가 가장 작은 Scope에서부터 해당 키값을 순차적으로 검색함
        pageScope=>requestScope=>sessionScope=>applicationScope
        순으로 속성을 찾음
        
        만약 모든 영역에서 해당 키값을 못찾는 경우에는 아무것도 출력이 되지 않음 (500 오류 안남)
    --%>
    <hr>
    <h3>4. 직접 Scope 영역을 지정해서 값에 접근하기</h3>
    <%
        //pageScope에 담기
        pageContext.setAttribute("scope", "page");
    %>
    scope 값: ${scope} <br>
    pageScope에 담긴 값: ${scope} 또는 ${pageScope.scope} <br>
    requestScope에 담긴 값: ${requestScope.scope} <br>
    sessionScope에 담긴 값: ${sessionScope.scope} <br>
    applicationScope에 담긴 값: ${applicationScope.scope} <br>

    잘못된 접근 예시(session의 classRoom): ${sessionScope.classRoom}<br>
    =>아무것도 출력이 되지 않음

    
</body>
</html>