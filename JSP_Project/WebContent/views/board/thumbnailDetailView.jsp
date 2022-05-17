<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.*" %>
<% 
Board b=(Board)request.getAttribute("b"); 
ArrayList<Attachment> list=(ArrayList<Attachment>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 800px;
        margin: auto;
        margin-top: 50px;
    }
    .detail-area td{
        border: 1px solid white;
        text-align: center;
        
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">사진게시판 상세보기</h2>
        <br>
        <table class="detail-area" align="center">
            <tr>
                <td width="70">제목</td>
                <td colspan="3" width="600"><%=b.getBoardTitle() %></td>
            </tr>
            <tr>
                <td>작성자</td>
                <td><%=b.getBoardWriter() %></td>
                <td>작성일</td>
                <td><%=b.getCreateDate() %></td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3">
                    <p style="height:50px;">
                        <%=b.getBoardContent() %>
                    </p>
                </td>
            </tr>
            <tr>
                <td>대표이미지</td>
                <td colspan="3">
                    <div>
                        <img src="<%=contextPath %>/<%=list.get(0).getFilePath()+list.get(0).getChangeName() %>" width="500" height="300">
                    </div>
                </td>
            </tr>
            <tr>
                <td>상세이미지</td>
                <td colspan="3">
                <%for(int i=1; i<list.size(); i++){ %>
                    <img src="<%=contextPath %>/<%=list.get(i).getFilePath()+list.get(i).getChangeName() %>" width="200" height="150">
                <%} %>
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
</body>
</html>