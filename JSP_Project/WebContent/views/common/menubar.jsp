<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.member.model.vo.Member" %>
<%
    //로그인한 사용자의 정보 뽑기(session으로부터)
    Member loginUser=(Member)session.getAttribute("loginUser");
	
	
    //System.out.println(loginUser);
    //로그인전 menubar.jsp를 로딩 시 loginUser에 null이 담김
    //로그인후 menubar.jsp를 로딩 시 loginUser에 해당 회원의 정보가 담긴 Member객체가 담김
    
    //request.getContextPath() 메소드를 이용해서 context root 값을 알아오기
    String contextPath=request.getContextPath();
    //System.out.println(contextPath);
    
    //세션으로부터 알람메시지 뽑아내기
    String alertMsg=(String)session.getAttribute("alertMsg");
    //System.out.println(alertMsg);
    //로그인 서비스 요청 전 menubar.jsp 로딩 시 null
    //로그인 서비스 요청 후 성공시 menubar.jsp 로딩 시 alert로 띄워줄 메시지 문
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome D Class</title>
<style>
    #login-form, #user-info{
        float: right;
    }
    #user-info a{
        text-decoration:none;
        color:black;
        font-size:12px;
    }
    .nav-area{
        background-color:black;
    }
    .menu{
        display: table-cell;/*인라인 요소처럼 배치가능*/
        height: 50px;
        width: 150px;
    }
    .menu a{
        text-decoration:none;
        color: white;
        font: 20px;
        font-weight:bold;
        display:block;
        width: 100%;
        height: 100%;
        line-height: 50px; /*장평 조절: 위~아래에서 가운데 조정*/
    }
    .menu a:hover{
        background-color: darkgray;
    }
</style>
</head>
<body>
    <h1 align="center">Welcome D Class</h1>
    <div class="login-area">
        <!--두 가지 화면이 선택적으로 보여지게끔-->
        <!-- 로그인 전에 보여지는 로그인 form-->
        <%if(loginUser==null){%>
        <form id="login-form" action="<%=contextPath %>/login.me" method="post">
            <table>
                <tr>
                    <th>아이디: </th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호: </th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button">회원가입</button>
                    </th>
                </tr>
            </table>
        </form>
        <%}else{%>
        <!--로그인 성공 후 xxx님 환영합니다 화면-->   
        <div id="user-info">
            <b><%=loginUser.getUserName()%>님</b> 환영합니다 <br><br>
            <div align="center">
                <a href="">마이페이지</a>
                <a href="<%=contextPath%>/logout.me">로그아웃</a>
            </div>
        </div>
        
        <%}%>
    </div>
    
    <br clear="both">
    <!--메뉴바 영역-->
    <div class="nav-area" align="center">
        <div class="menu"><a href="">HOME</a></div>
        <div class="menu"><a href="">공지사항</a></div>
        <div class="menu"><a href="">일반게시판</a></div>
        <div class="menu"><a href="">사진게시판</a></div>
    </div>
    <script>
        var msg="<%=alertMsg%>"; //"성공적으로 로그인이 되었습니다." / "null"
        if(msg!="null"){
            alert(msg);
            //알림창을 띄워 준 후 session에 담긴 해당 메시지를
            //session.removeAttribute("키값"); 메소드로 지워줘야 함
            //안그러면 menubar.jsp가 로딩 될 때 마다 매번 alert가 계속 뜸
            <%session.removeAttribute("alertMsg");%>
        }
    </script>
</body>
</html>