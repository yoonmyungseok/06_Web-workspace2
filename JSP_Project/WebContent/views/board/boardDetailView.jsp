<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kh.board.model.vo.*" %>
<%       
    Board b=(Board)request.getAttribute("b"); 
    //글번호, 카테고리명, 제목, 내용, 작성자아이디, 작성일
    Attachment at=(Attachment)request.getAttribute("at");
    //파일번호, 원본명, 수정명, 저장경로
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
        margin: auto;
        margin-top: 50px;
    }
    .outer table{border-color: white;}
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>
    <div class="outer"> 
        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>
        <table id="detail-area" align="center" border="1">
            <tr>
                <th width="70">카테고리</th>
                <td width="70"><%=b.getCategory() %></td>
                <th width="70">제목</th>
                <td width="350"><%=b.getBoardTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=b.getBoardWriter() %></td>
                <th>작성일</th>
                <td><%=b.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 200px;"><%=b.getBoardContent() %></p>
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                    <!--첨부파일이 없을 경우: 첨부파일이 없습니다-->
                    <%if(at==null){ %>
                    첨부파일이 없습니다.
                    <%}else{ %>
                    <!--첨부파일이 있을 경우: 첨부파일의 원본명-->
                    <a download="<%=at.getOriginName() %>" href="<%=contextPath%>/<%=at.getFilePath() + at.getChangeName()%>"><%=at.getOriginName() %></a>
                    <%} %>
                </td>
            </tr>
        </table>
        <br>
        <div align="center">
            <a href="javascript:history.back()" class="btn btn-secondary btn-sm">목록가기</a>

            <!--로그인한 사용자가 글 작성자와 일치할 경우만 보여지게끔-->
            <%if(loginUser!=null&&loginUser.getUserId().equals(b.getBoardWriter())){ %>
            <a href="<%=contextPath %>/updateForm.bo?bno=<%=b.getBoardNo() %>" class="btn btn-warning btn-sm">수정하기</a>
            <a href="<%=contextPath%>/delete.bo?bno=<%=b.getBoardNo()%>" class="btn btn-danger btn-sm">삭제하기</a>
            <%} %>
        </div>
        <br>
        <!--댓글창: 우선 화면구현만 하드코딩으로/기능구현은 AJAX 배우고나서-->
        <div id="reply-area">
            <table align="center" border="1">
                <thead>
                    <%if(loginUser!=null){ %>
                    <!--로그인이 되어있을 경우-->
                    <tr>
                        <th>댓글작성</th>
                        <td><textarea id="replyContent" cols="50" rows="3" style="resize:none;"></textarea></td>
                        <td><button type="button" onclick="insertReply();">댓글등록</button></td>
                    </tr>
                    <%}else{ %>
                    <!--로그인이 되어있지 않은 경우-->
                    <tr>
                        <th>댓글작성</th>
                        <td><textarea cols="50" rows="3" style="resize:none;" readonly>로그인 후 이용 가능한 서비스입니다</textarea></td>
                        <td><button disabled>댓글등록</button></td>
                    </tr>
                    <%} %>
                </thead>
                <tbody>

                </tbody>
            </table>
            <br><br>
        </div>
        <script>
            $(function () {
                    selectReplyList();

                    //setInterval(selectReplyList, 1000);
                    // 실시간으로 댓글보기 기능 추가
                    // 1초 간격마다 selectReplyList 함수 실행
                });

                function insertReply() {

                    $.ajax({
                        url: "rinsert.bo",
                        data: {
                            content: $("#replyContent").val(),
                            bno: '<%= b.getBoardNo() %>'
            			},
                        type: "post",
                        success: function (result) {
                            console.log(result);
                            // result 가 1 이라면 성공 / 0 이라면 실패
                            if (result > 0) { // 댓글작성 성공

                                // 갱신된 댓글 리스트 조회
                                selectReplyList();

                                // textarea 초기화
                                $("#replyContent").val("");
                            }
                            else { // 댓글작성 실패

                                alert("댓글 등록에 실패했습니다.");
                            }
                        },
                        error: function () {
                            console.log("댓글 작성용 ajax 통신 실패!");
                        }
                    });
                }

                function selectReplyList() {

                    $.ajax({
                        url: "rlist.bo",
                        data: { bno: '<%= b.getBoardNo() %>'},
                        success: function (result) {

                            // [{}, {}, {}, ...]

                            var resultStr = "";
                            for (var i = 0; i < result.length; i++) {

                                resultStr += "<tr>"
                                    + "<td>" + result[i].replyWriter + "</td>"
                                    + "<td>" + result[i].replyContent + "</td>"
                                    + "<td>" + result[i].createDate + "</td>"
                                    + "</tr>";
                            }

                            
                            $("#reply-area tbody").html(resultStr);
                        },
                        error: function () {
                            console.log("댓글리스트 조회용 ajax 통신 실패!");
                        }
                    });
                }
        </script>
    </div>
</body>
</html>