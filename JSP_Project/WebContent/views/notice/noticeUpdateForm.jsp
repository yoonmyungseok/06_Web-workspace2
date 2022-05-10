<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
<%
//현재 게시글 정보 뽑기
    Notice n=(Notice)request.getAttribute("n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 500px;
        margin:auto;
        margin-top: 50px;
    }
    #update-form>table{border: 1px solid white;}
    #update-form input, #update-form textarea{
        width: 100%;
        box-sizing: border-box; 
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">공지사항 수정하기</h2>
        <br>
        <form action="<%=contextPath%>/update.no" id="update-form" method="post">
            <input type="hidden" name="nno" value="<%=n.getNoticeNo()%>">
            
            <table align="center">
                <tr>
                    <th width="50">제목</th>
                    <td width="350"><input type="text" name="title" required value="<%=n.getNoticeTitle()%>"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize: none;" required><%=n.getNoticeContent()%></textarea>
                    </td>
                </tr>
            </table>
            <br><br>
            <div align="center">
                <button type="submit">수정하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button>
                <!--history.back();: 이전 페이지로 돌아가게 해주는 함수-->
            </div>
        </form>
    </div>
</body>
</html>