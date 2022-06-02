<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.model.vo.Person"%>
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

    <h3>2. 조건문 - if(&lt;c:if test="조건식"&gt;)</h3>
    <pre>
        - JAVA의 단일 if문과 비슷한 역할을 하는 태그
        - 단, 조건식은 test 속성에 적되, EL 구문으로 작성해야 함
    </pre>
    <!-- 기존 방식-->
    <% if(true) {%>
        <!-- 조건식이 true일 경우 보여질 html 태그들-->    
    <%}%>

    <c:if test="${num1 gt num2}"> <!--조건식이 false이므로 해당 내용물이 안보임-->
        <b>num1이 num2보다 큽니다.</b>
    </c:if>
    <c:if test="${num1 le num2}"> <!--조건식이 true이므로 해당 내용물이 보임-->
        <b>num1이 num2보다 작거나 같습니다.</b>
    </c:if>
    <br>
    <c:set var="str" value="안녕하세요"/>
    <% if(((String)pageContext.getAttribute("str")).equals("안녕하세요")){ %>
        <!--str 키 값에 담긴 내용물과 "안녕하세요"가 일치할 경우 보여질 내용물-->
    <%}%>
    <c:if test="${str eq '안녕하세요'}">
        <mark>Hello World!</mark>
    </c:if>
    <c:if test="${str ne '안녕하세요'}">
        <mark>Goodbye World!</mark>
    </c:if>
    <br>
    <h3>3. 조건문 - choose, when, otherwise (c:choose, c:when test="조건식", c:otherwise)</h3>
    <pre>
        - JAVA의 if-else문, if-else if문, switch 문과 비슷한 역할을 하는 태그들
        - 각 조건들을 c:choose의 하위 요소로 묶어서 c:when으로 조건을 작성
    </pre>
    <!--기존 방법-->
    <%--
    <% if(num1==20){%>
    <%}else if(num1==10){%>
    <%}else{%>
    <%}%>
    --%>
    <c:choose>
        <c:when test="${num1 eq 20}"> <!--if-->
            <b>처음 봽겠습니다.</b>
        </c:when>
        <c:when test="${num1 eq 10}">
            <b>다시 봐서 반갑습니다.</b> <!--else if-->
        </c:when>
        <c:otherwise><!--else-->
            <b>안녕하세요</b>
        </c:otherwise>
    </c:choose>
    <hr>
    <h3>4. 반복문 - forEach</h3>
    <pre>
        - 속성을 어떻게 쓰느냐에 따라 두가지 용법으로 나뉨
        [표현법]
        for loop 문 (초기식, 조건식, 증감식)
        &lt;c:forEach var="변수명" begin="초기값" end="끝값" step="증가값(생략가능)"&gt;
            반복적으로 찍어내고자 하는 내용물
        &lt;/c:forEach&gt;

        =>var, begin: 기존의 일반 for문의 초기식에 해당되는 속성들
        =>end: 기존의 일반 for문의 조건식에 해당되는 속성
        =>step: 기존의 일반 for문의 증감식에 해당되는 속성(생략 가능, 생략 시 1이 기본값)

        향상된 for 문(변수선언부, 배열명 또는 컬렉션명)
        &lt;c:forEach var="변수명" items="순차적으로 접근할 배열명 또는 컬렉션명" varStatus="현재 접근된 요소의 상태값을 보관할 변수명(생략가능)" &gt;
            반복적으로 찍어내고자 하는 내용물
        &lt;/c:forEach&gt;
    </pre>
    <!--기존 방법-->
    <!--for loop문-->
    <%for(int i=1; i<=10; i++){%>

    <%}%>

    <c:forEach var="i" begin="1" end="10">
        반복 확인 : ${i} <br>
    </c:forEach>
    
    <!--2씩 증가할 경우-->
    <%for(int i=1; i<=10; i+=2){%>

    <%}%>
    <c:forEach var="i" begin="1" end="10" step="2">
        반복 확인 : ${i} <br>
    </c:forEach>
    <br>
    <!--var 속성에 작성한 변수는 태그 내에서도 사용 가능-->
    <c:forEach var="i" begin="1" end="6" step="1">
        <h${i}>태그 안에서도 적용 가능함</h${i}>
    </c:forEach>
    <br>

    <!--향상된 for문-->
    <%--
    <% for(Board b: list){%>
        <!--b.getter 메소드 이용해서 매번 테이블을 만들거나 했었음-->
    <%}%>
    --%>

    <!--테스트를 위한 변수 셋팅-->
    <c:set var="colors">
        red, yellow, green, pink
    </c:set><!--배열과 같은 역할-->
    colors 값: ${colors} <br>
    <ul>
        <c:forEach var="c" items="${colors}">
            <li style="color:${ c }">${c}</li>
        </c:forEach>
    </ul>
    <br>
    <%
        //서블릿에서 넘겨받았다는 가정 하에 작성
        ArrayList<Person> list=new ArrayList<>();
        list.add(new Person("홍길동",20,"남자"));
        list.add(new Person("김말순",30,"여자"));
        list.add(new Person("박말똥",40,"남자"));

        request.setAttribute("pList",list);
    %>
    <!--응용: DB로 부터 위의 내용들을 조회했다는 가정 하에 table로 구성하기-->
    <table border="1">
        <thead>
            <tr>
                <th>순번</th>
                <th>이름</th>
                <th>나이</th>
                <th>성별</th>
            </tr>
        </thead>
        <tbody>
            <!--기존 방법: if문으로 비어있나 안비어있나 검사 후 안비어있다면 그때 반복 -->
            <%--
            <% if(pList.isEmpty()){%>
            <%}else{%>
                <%for(Person p:pList){%>
                    
                <%}%>
            <%}%>
            --%>
            <!--core library를 이용한 방법 -->
            <c:choose>
                <c:when test="${empty pList}">
                    <tr align="center">
                        <td colspan="4">조회 결과가 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="p" items="${pList}" varStatus="status">
                        <tr align="center"> 
                            <td>${status.count}</td><!--index:0부터 / count: 1부터 시작 -->
                            <td>${p.name}</td>
                            <td>${p.age}</td>   
                            <td>${p.gender}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    <hr>
    <h3>5. 반복문 - forTokens</h3>
    <pre>
    - JAVA의 StringTokenizer 클래스, split("구분자"); 와 유사한 역할
    - 구분자를 통해서 분리된 각각의 문자열에 순차적으로 접근하면서 반복을 수행

    [표현법]
    &lt;c:forTokens var="값을 보관할 변수" items="분리시키고자하는 원본 문자열" delims="구분자"&gt;
        반복적으로 수행할 내용물들
    &lt;/c:forTokens&gt;
    </pre>

    <!--테스트용 데이터-->
    <c:set var="device" value="컴퓨터,휴대폰,TV,에어컨/냉장고.세탁기" />
    <ul>
        <c:forTokens var="d" items="${device}" delims=",/.">
            <li>${d}</li>
        </c:forTokens>
    </ul>
    <hr>
    <h3>6. 쿼리스트링 관련 태그 - url, param</h3>
    <pre>
    - url 경로를 생성하고, 쿼리스트링을 정의할 수 있는 태그
    - 넘겨야할 쿼리스트링이 길 경우 사용하면 편하다
    [표현법]
    &lt;c:url var="만들어진url을 담을 변수명" value="요청할 url"&gt;
        &lt;c:param name="키값" value="밸류값" /&gt;
        &lt;c:param name="키값" value="밸류값" /&gt;
    &lt;/c:url&gt;
    </pre>
    <!--기존 방식-->
    <a href="list.do?currentPage=1&num=2">기존 방식</a>

    <!--core library를 이용한 방식-->
    <c:url var="query" value="list.do">
        <c:param name="currentPage" value="1" />
        <c:param name="num" value="2" />
    </c:url> <!--내부적으로 list.do?currentPage=1&num=2가 만들어져서 query라는 변수에 담겨있음-->
    <a href="${query}">c:url을 이용한 방식</a>
</body>
</html>