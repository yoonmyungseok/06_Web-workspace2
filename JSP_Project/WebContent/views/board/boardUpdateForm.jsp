<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.board.model.vo.*" %>
<%
//카테고리 리스트, 게시글 상세조회, 첨부파일 상세조회
ArrayList<Category> list=(ArrayList<Category>)request.getAttribute("list");
Board b=(Board)request.getAttribute("b");
Attachment at=(Attachment)request.getAttribute("at");

%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
<style>
    .outer {
        background-color: black;
        color: white;
        width: 1000px;
        height: 550px;
        margin: auto;
        margin-top: 50px;
    }

    #update-form>table {
        border: 1px solid white;
    }

    #update-form>table input,
    #update-form>table textarea {
        width: 100%;
        box-sizing: border-box;
    }

    #update-form>table textarea {   
        resize: none;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">일반게시판 수정하기</h2>
        <br>
        <form id="update-form" action="<%=contextPath%>/update.bo" method="post" enctype="multipart/form-data">
            <!--
                카테고리, 제목, 내용, 첨부파일, 글번호을 입력
            -->
            <input type="hidden" name="bno" value="<%=b.getBoardNo()%>">
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
                                <option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName()%></option>
                            <%} %>
                        </select>
                        <script>
                            $(function(){
                                $('#update-form option').each(function(){
                                    if($(this).text()=="<%=b.getCategory()%>"){
                                        //현재 탐색중인 option태그의 내용물과 현재 게시글의 카테고리명이 일치한다면
                                        //selected 옵션 부여
                                        $(this).attr("selected", true);
                                    }
                                })
                            })
                        </script>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" required value="<%=b.getBoardTitle()%>"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea name="content" rows="10" required><%=b.getBoardContent()%></textarea></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td>
                    <%if(at!=null){ %>
                    <!--기존에 파일이 있다면 원본명을 보여주기-->
                    <%=at.getOriginName() %>
                    <!--백엔드 단에서 기존에 파일이 있는지 검사용도로 원본파일의 파일번호, 수정명(삭제용도)-->
                    <input type="hidden" name="originFileNo" value="<%=at.getFileNo()%>">
                    <input type="hidden" name="originFileName" value="<%=at.getChangeName()%>">
                    <%} %>
                    <input type="file" name="reUpfile">
                    </td>
                </tr>
            </table>
            <br>
            <div align="center">
                <button type="submit">수정하기</button>
            </div>
        </form>
    </div>
</body>
</html>

