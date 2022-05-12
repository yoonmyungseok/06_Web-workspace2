<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Board, com.kh.common.model.vo.PageInfo" %>
<% 
    ArrayList<Board> list=(ArrayList<Board>)request.getAttribute("list");
    PageInfo pi=(PageInfo)request.getAttribute("pi");

    //페이징바 관련 변수
    int currentPage=pi.getCurrentPage();
    int startPage=pi.getStartPage();
    int endPage=pi.getEndPage();
    int maxPage=pi.getMaxPage();

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
        width: 1000px;
        height: 550px;
        background-color: black;
        color: white;
        margin: auto;
        margin-top: 50px;
    }
    .list-area{
        border: 1px solid white;
        text-align: center;
    }
    .list-area>tbody>tr:hover{
        background-color: gray;
        cursor: pointer;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">일반게시판</h2>
        <br>
        <!--로그인한 회원만 보이는 글 작성 버튼-->
        <%if(loginUser!=null){ %>
        <div align="right" style="width:850px;">
            <a href="<%=contextPath%>/enrollForm.bo" class="btn btn-secondary btn-sm">글작성</a>
            <br><br>
        </div>
        <%} %>
        <table align="center" class="list-area">
            <thead>
                <tr>
                    <th width="70">글번호</th>
                    <th width="70">카테고리명</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <%if(list.isEmpty()){ %>
                <tr>
                    <td colspan="6">조회된 리스트가 없습니다.</td>
                </tr>
                <%}else{ %>
                    <%for(Board b:list) {%>
                    <tr>
                        <td><%=b.getBoardNo() %></td>
                        <td><%=b.getCategory() %></td>
                        <td><%=b.getBoardTitle() %></td>
                        <td><%=b.getBoardWriter() %></td>
                        <td><%=b.getCount() %></td>
                        <td><%=b.getCreateDate() %></td>
                    </tr>
                    <%} %>
                <%} %>
            </tbody>
        </table>
        <br><br>
        <!--페이징바-->
        <div align="center" class="paging-area">
            <%if(currentPage!=1){ %>
                <button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=currentPage-1%>';">&lt;</button>
            <%}%>     
            <%for(int p=startPage;p<=endPage;p++){ %>
            <% if(p!=currentPage){ %>
                <button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=p%>';"><%=p %></button>
                <%}else{ %>
                <!-- 현재 내가 보고있는 페이지일 경우는 클릭 안되게끔 -->
                <button disabled><%=p %></button>
                <%} %>
            <%} %>
            <%if(currentPage!=maxPage){ %>
                <button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=currentPage+1%>';">&gt;</button>
            <%}%>
        </div>
    </div>
</body>
</html>