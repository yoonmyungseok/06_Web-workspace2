<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.text.SimpleDateFormat, java.util.Date" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
</head>
<body>
    <%
        String year=new SimpleDateFormat("yyyy").format(new Date());
    %>
    Copyright © 1998-<%=year%> All rights reserved.
    <br>
    넘겨받은 test 값: ${param.test}
</body>
</html>