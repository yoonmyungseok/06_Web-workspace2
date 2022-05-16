<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <div class="thumbnail" align="center">
                <img src="/jsp/resources/thumbnail_upfiles/" width="200" height="150" alt="">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="/jsp/resources/thumbnail_upfiles/" width="200" height="150" alt="">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="/jsp/resources/thumbnail_upfiles/" width="200" height="150" alt="">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
            <div class="thumbnail" align="center">
                <img src="/jsp/resources/thumbnail_upfiles/" width="200" height="150" alt="">
                <p>
                    No.123 제목입니다. <br>
                    조회수: 230
                </p>
            </div>
        </div>
    </div>
</body>
</html>