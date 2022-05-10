<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.notice.model.vo.Notice" %>
<%
    //조회된 전체 공지사항 리스트 뽑기
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
    #detail-area{border:1px solid white;}
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp"%>
    <div class="outer">
        <br>
        <h2 align="center">공지사항 상세보기</h2>
        <br>
        <table id="detail-area" align="center" border="1">
            <tr>
                <th width="70">제목</th>
                <td width="350" colspan="3"><%=n.getNoticeTitle()%></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=n.getNoticeWriter()%></td>
                <th>작성일</th>
                <td><%=n.getCreateDate()%></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 150px;"><%=n.getNoticeContent()%></p>
                </td>
            </tr>
        </table>
        <br><br>
        <div align="center">
            <a href="<%=contextPath%>/list.no" class="btn btn-secondary btn-sm">목록가기</a>
            <!--현재 로그인한 사용자가 해당 글을 작성한 작성자일 경우에만 수정하기, 삭제하기 버튼이 보이게끔-->
            <%if(loginUser!=null&&loginUser.getUserId().equals(n.getNoticeWriter())){ %>
            <a href="<%=contextPath%>/updateForm.no?nno=<%=n.getNoticeNo() %>" class="btn btn-warning btn-sm">수정하기</a>
            <a href="<%=contextPath%>/delete.no?nno=<%=n.getNoticeNo() %>" class="btn btn-danger btn-sm">삭제하기</a>
            <%} %>
        </div>
    </div>
</body>
</html>