<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <!-- 
        [CRUD]
        웹사이트 입장에서 기본적으로 갖춰야할 데이터 처리 기능들
        C: CREATE(생성)의 약자 => Insert 구문
        R: READ(읽기)의 약자 => Select 구문
        U: UPDATE(갱신)의 약자 => Update 구문
        D: DELETE(삭제)의 약자 => Delete 구문

        *회원 서비스        |   C(Insert)    |   R(Select)   |   U(Update)   |   D(Delete)
        ==============================================================================================
            로그인          |                |      O        |               |         
            회원가입        |       O        |               |               |
            아이디 중복확인 |                |      O        |               |
            마이페이지      |                |      O        |               |
            회원정보변경    |                |               |      O        |
            회원탈퇴        |                |               |               O(Update 구문을 사용할것-복구의 여지를 남겨두기 위해)

        *공지사항 서비스-   공지사항리스트조회(R)   /   공지사항상세조회(R) /   공지사항작성(C) / 
                            공지사항수정(U) /   공지사항삭제(U-[D]) 

        *일반게시판서비스-  게시판리스트조회(R)- 페이징처리 / 게시판상세조회(R) /   게시글작성(C)- 첨부파일1개 업로드
                            게시글수정(U)   /   게시글삭제(U-[D])   /   댓글리스트조회(R) / 댓글작성(C)

        *사진게시판서비스-  사진게시판리스트조회(R)- 썸네일 /   게시판상세조회(R)   /   게시글작성(C)- 다중첨부파일업로드
                            
    -->
    <%-- <h1>잘 실행되나...?</h1>--%>
    <!-- 상단에는 menubar.jsp 를 include 시킬것임-->
    <%@ include file="views/common/menubar.jsp" %>
    
    <!-- 광고 팝업 쿠키 -->
    <!-- The Modal -->
    <div class="modal" id="adModal">
        <div class="modal-dialog">
            <div class="modal-content">
    
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">오늘 하루 특가!!!!!!!</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
    
                <!-- Modal body -->
                <div class="modal-body">
                    404 에러 오늘 하루만 10프로 할인해드립니다 ^.^
                </div>
    
                <!-- Modal footer -->
                <div class="modal-footer">
                    <input type="checkbox" name="mainPop" id="mainPop">
                    <label for="mainPop">24시간 동안 보지 않기</label>
                </div>
    
            </div>
        </div>
    </div>
    <script>
        $(function(){
            //체크박스에 클릭 이벤트가 걸릴 경우 모달 닫기 겸 쿠키 설정
            //사용자 입장에서 체크박스를 클릭==24시간 동안 광고창을 안보겠다
            $("#mainPop").click(function(){
                $("#adModal").modal("hide");
                
                //쿠키 설정=>단순히 상태값을 저장하기 위함이므로 형태에 맞게 하드코딩
                
                var today=new Date(); //현재 시간
                today.setDate(today.getDate()+1); //날짜를 셋팅해주는 메소드

                //자바스크립트 상에서 쿠기 생성하려면 document 객체에서 제공하는 cookie 속성에 접근한다
                //반드시 형식에 맞게 문자열로 표기
                //"키=밸류; expires=만료시간" 
                //UTF: 세계표준시(기준은 영국 그리니치 천문대, 우리나라는 +9를 쓴다)
                document.cookie="mainPop=n; expires="+today.toUTCString();
                //console.log(document.cookie); // "mainPop=n"
            })
            //클릭 이벤트 밖에 쿠키가 있나 검사하는 로직 추가
            //document.cookie가 "mainPop=n" 과 일치한다면 쿠키가 있음=>모달창 안열겠다
            if(document.cookie=="mainPop=n"){
                $("#adModal").modal("hide");
            }else{
                $("#adModal").modal("show");
            }

            //모달창을 꺼주는 메소드: .modal("hide");
            //모달창을 열어주는 메소드: .modal("show");
            

        })
    </script>
</body>
</html>