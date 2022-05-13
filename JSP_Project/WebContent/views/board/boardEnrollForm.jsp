<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.board.model.vo.Category, java.util.ArrayList" %>
    <%
    ArrayList<Category> list=(ArrayList<Category>)request.getAttribute("list");
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
        height: 550px;
        margin: auto;
        margin-top: 50px;
    }
    #enroll-form>table{border:1px solid white;}
    #enroll-form>table input, #enroll-form>table textarea{
        width: 100%;
        box-sizing: border-box;
    }
    #enroll-form>table textarea{
        resize: none;
    }
</style>
</head>
<body>
    <%@include file="../common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">일반게시판 작성하기</h2>
        <br>
        <!--파일을 서블릿으로 전달하려면 반드시 enctype="multipart/form-data" 속성을 작성해야함-->
        <form id="enroll-form" action="<%=contextPath %>/insert.bo" method="post" enctype="multipart/form-data">
            <!--
                카테고리, 제목, 내용, 첨부파일을 입력받고
                작성자의 회원번호는 hidden으로 넘기기
            -->
            <input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
            <table align="center">
                <tr>
                    <th width="100">카테고리</th>
                    <td width="500">
                        <select name="category">
                            <!-- 
                            <option value="10">공통</option>
                            <option value="20">운동</option>
                            <option value="30">등산</option>
                            <option value="40">게임</option>
                            <option value="50">낚시</option>
                            <option value="60">요리</option>
                            <option value="70">기타</option>
                            -->
                            <%for(Category c:list){ %>
                            <option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
                            <%} %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea name="content" rows="10" required></textarea></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td><input type="file" name="upfile"></td>
                </tr>
            </table>
            <br>
            <div align="center">
                <button type="submit">작성하기</button>
                <button type="reset">취소하기</button>
            </div> 
        </form>
    </div>
</body>
</html>