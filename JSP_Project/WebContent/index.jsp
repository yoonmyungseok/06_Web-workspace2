<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <h1>잘 실행되나...?</h1>
</body>
</html>