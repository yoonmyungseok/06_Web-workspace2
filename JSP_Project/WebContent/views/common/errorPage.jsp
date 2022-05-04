<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    //5)지금 이 페이지에서 필요로 하는 데이터를 뽑기(request의 attribute 영역으로부터)
	String errorMsg=(String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--6) 응답화면에 데이터 뿌리기 --%>
    <h1 align="center" style="color:red;"><%=errorMsg %></h1>
</body>
</html>