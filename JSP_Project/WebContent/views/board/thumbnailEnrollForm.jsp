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
        color:white;
        width: 1000px;
        height: 700px;
        margin: auto;
        margin-top: 50px;
    }
    #enroll-form table{border: 1px solid white;}
    #enroll-form input, #enroll-form textarea{
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp"%>
    <div class="outer">
        <br>
        <h2 align="center">사진게시판 작성하기</h2>
        <br>
        <form action="<%=contextPath%>/insert.th" id="enroll-form" method="post" enctype="multipart/form-data">
            <!--
                글제목, 글내용, 대표이미지, 상세이미지1, 2, 3 입력받음
                작성자 정보 hidden(작성자 정보==현재 로그인한 회원의 회원번호)
            -->
            <input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
            <table align="center">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3"><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"><textarea name="content" style="resize: none;" rows="5"></textarea></td>
                </tr>
                <tr>
                    <th>대표이미지</th><!--미리보기 기능-->
                    <td colspan="3"><img id="titleImg" width="250" height="170"></td>
                </tr>
                <tr>
                    <th>상세이미지</th>
                    <td><img id="contentImg1" width="150" height="120"></td>
                    <td><img id="contentImg2" width="150" height="120"></td>
                    <td><img id="contentImg3" width="150" height="120"></td>
                </tr>
            </table>
            <div id="file-area">
                <!--대표이미지(썸네일)은 필수-->
                <!--onchange: input 태그의 내용물이 변경될 때 발생하는 이벤트-->
                <input type="file" id="file1" name="file1" onchange="loadImg(this,1);" required>
                <input type="file" id="file2" name="file2" onchange="loadImg(this,2);">
                <input type="file" id="file3" name="file3" onchange="loadImg(this,3);">
                <input type="file" id="file4" name="file4" onchange="loadImg(this,4);">
            </div>
            <script>
                $(function(){
                    $("#file-area").hide();
                    $("#titleImg").click(function(){
                        $("#file1").click();
                    });
                    $("#contentImg1").click(function () {
                        $("#file2").click();
                    });
                    $("#contentImg2").click(function () {
                        $("#file3").click();
                    });
                    $("#contentImg3").click(function () {
                        $("#file4").click();
                    });
                });
                function loadImg(inputFile, num){
                    //inputFile: 현재 변화가 생긴 input type="file" 요소 객체 자체
                    //num: 몇번째 input type="file" 요소 객체인지 확인 후
                    //      그 영역에 미리보기 이미지를 뿌려주기 위한 매개변수
                    //console.log(inputFile.files.length);
                    //파일이 선택됐을 경우:1
                    //파일이 선택되지 않을 경우:0
                    //=>즉, 파일의 존재유무를 파악할 수 있다
                    //files 속성은 업로드된 파일의 정보들을 배열 형식으로 여러 개 묶어서 반환
                    //length는 배열 크기
                    if(inputFile.files.length==1){
                        //선택된 파일이 존재할 경우
                        //=>선택된 파일을 읽어들여서 그 영역에 맞는 img 태그 부분에 미리보기(src 속성 추가)

                        //파일을 읽어들일 자바스크립트의 FileReader 객체 생성
                        var reader=new FileReader();

                        //FileReader 객체에서 제공하는 readAsDataURL(파일정보)
                        //=>파일을 읽어들여서 
                        //  해당 그 파일의 고유한 url이 부여됨
                        //  해당 url을 src 속성에 대입하면 이미지 미리보기 가능
                        reader.readAsDataURL(inputFile.files[0]);

                        //파일 읽기가 완료되는 순간 실행할 함수를 정의
                        reader.onload=function(e){
                            //e.target==reader
                            //e.target.result에 각 파일의 url 값을 뽑아낼 것
                            //각 영역에 맞춰서 이미지 미리보기 
                            switch(num){
                                case 1:$('#titleImg').attr("src",e.target.result); break;
                                case 2:$("#contentImg1").attr("src",e.target.result); break;
                                case 3:$("#contentImg2").attr("src",e.target.result); break;
                                case 4:$("#contentImg3").attr("src",e.target.result); break;
                            }
                        }
                    }else{
                        //선택된 파일이 존재하지 않는 경우
                        //=>미리보기 사라지게 하기 (src 속성 제거)
                        switch(num){
                            case 1:$("#titleImg").attr("src",null); break;
                            case 2:$("#contentImg1").attr("src",null); break;
                            case 3:$("#contentImg2").attr("src",null); break;
                            case 4:$("#contentImg3").attr("src",null); break;
                        }
                    }
                }
            </script>
            <div align="center">
                <button type="submit">등록하기</button>
            </div>
        </form>
    </div>
</body>
</html>