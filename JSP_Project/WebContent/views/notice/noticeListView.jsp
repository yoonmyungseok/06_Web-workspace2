<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
<%
    //조회된 전체 공지사항 리스트 뽑기
    ArrayList<Notice> list=(ArrayList<Notice>)request.getAttribute("list");
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
        <h2 align="center">공지사항</h2>
        <br>
        <!--글 작성 버튼은 관리자만 보이게끔 처리-->
        <!--글 작성 버튼-->
        <% if(loginUser!=null&&loginUser.getUserId().equals("admin")){%>
        <div align="right" style="width: 850px;">
            <!--<button type="button" onclick="location.href=''">글작성</button>-->
            <a href="" class="btn btn-secondary btn-sm">글작성</a>
            <!--
                a 태그는 href 속성이 있지만 버튼은 없으므로 이 경우, 다른페이지로 이동하고자 한다면 
                클릭 이벤트 발생 시 onclick 이벤트 속성으로 location.href 속성값을 url로 지정하거나, 
                a태그를 버튼 형식으로 만들어 주면 된다.
            -->
            <br><br>
        </div>
        <%}%>
        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th width="400">글제목</th>
                    <th width="100">작성자</th>
                    <th>조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <!--보통 작성일 기준 내림차순, 즉, 최신글이 가장 위에 오게끔 구현-->
                <%if(list.isEmpty()){%>
                    <!-- 리스트가 비어있을 경우 -->
                    <tr>
                        <td colspan="5">존재하는 공지사항이 없습니다.</td>
                    </tr>
                <%}else{ %>
                    <!-- 리스트가 존재할 경우 -->
                    <%for(Notice n:list){%>
                        <tr>
                            <td><%=n.getNoticeNo()%></td>
                            <td><%=n.getNoticeTitle() %></td>
                            <td><%=n.getNoticeWriter() %></td>
                            <td><%=n.getCount() %></td>
                            <td><%=n.getCreateDate() %></td>
                        </tr>
                    <%} %>
                <%} %>
                <!--
                <tr>
                    <td>3</td>
                    <td>제목입니닼ㅋㅋ</td>
                    <td>admin</td>
                    <td>120</td>
                    <td>2021-03-21</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>공지사항임</td>
                    <td>admin</td>
                    <td>889</td>
                    <td>2021-03-01</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>공지사항 서비스를 시작합니다</td>
                    <td>admin</td>
                    <td>230</td>
                    <td>2021-01-21</td>
                </tr>
                -->
            </tbody>
        </table>
    </div>
</body>
</html>