<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.Board" %>
<%
    ArrayList<Board> list=(ArrayList<Board>)request.getAttribute("list");
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
    .list-area{
        width: 760px;
        margin: auto;
    }
    .thumbnail{
        border: 1px solid white;
        width: 220px;
        display: inline-block;
        margin: 14px;
    }
    .thumbnail:hover{
        cursor: pointer;
        opacity: 0.7;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp"%>
    <div class="outer">
        <br>
        <h2 align="center">사진게시판</h2>
        <br>
        <%if(loginUser!=null){%>
        <!--로그인한 사용자만 보이게끔 글작성 버튼 만들기-->
        <div align="right" style="width: 850px;">
            <a href="<%=contextPath%>/enrollForm.th" class="btn btn-secondary btn-sm">글작성</a>
            <br><br>
        </div>
        <%}%>
        <div class="list-area">
        <%if(list!=null){ %>
            <%for(Board b:list){ %>
            <div class="thumbnail" align="center">
                <img src="<%=contextPath %>/<%=b.getTitleImg() %>" width="200" height="150">
                <input type="hidden" value="<%=b.getBoardNo()%>">
                <p>
                    No.<%=b.getBoardNo() %> <%=b.getBoardTitle() %><br>
                    조회수: <%=b.getCount() %>
                </p>
            </div>
            <%} %>
        <%}else{ %>
            등록된 게시글이 없습니다.
        <%} %>
        </div>
    </div>
    <script>
        $(function(){
            $(".thumbnail").click(function(){
                var bno=$(this).children().eq(0).val();
                location.href="<%=contextPath%>/detail.th?bno="+bno;
            })
        })
    </script>
</body>
</html>