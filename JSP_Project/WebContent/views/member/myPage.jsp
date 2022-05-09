<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    .outer{
        background-color: black;
        color:white;
        width: 1000px;
        margin: auto;
        margin-top: 50px;
    }
    #mypage-form table{
        margin: auto;
    }
    #mypage-form input{
        margin: 5px;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp"%>
    <%
        //value 속성에 들어갈 현재 로그인한 회원의 정보들을 각 변수에 담아둘것
        //아이디, 이름, 전화번호, 이메일, 주소, 취미
        String userId=loginUser.getUserId();
        String userName=loginUser.getUserName();
        String phone=(loginUser.getPhone()==null) ? "": loginUser.getPhone();
        String email=(loginUser.getEmail()==null) ? "": loginUser.getEmail();
        String address=(loginUser.getAddress()==null) ? "": loginUser.getAddress();
        String interest=(loginUser.getInterest()==null) ? "": loginUser.getInterest(); // "운동, 등산";
        
        
    %>
    <div class="outer">
        <br>
        <h2 align="center">마이페이지</h2>

        <!--마이페이지의 역할:내 정보를 조회, 내 정보를 수정-->
        <form id="mypage-form" action="<%=contextPath%>/update.me" method="post">
            <!--아이디, 이름, 전화번호, 이메일, 주소, 취미 (조회)-->
            <!--이름, 전화번호, 이메일, 주소, 취미-->
            <table>
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" readonly value="<%=userId%>"></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="5" required value="<%=userName%>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력" value="<%=phone%>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email" value="<%=email%>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address" value="<%=address%>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                        <input type="checkbox" name="interest" id="sports" value="운동"><label for="sports">운동</label>
                        <input type="checkbox" name="interest" id="hiking" value="등산"><label for="hiking">등산</label>
                        <input type="checkbox" name="interest" id="fishing" value="낚시"><label for="fishing">낚시</label><br>
                        <input type="checkbox" name="interest" id="cooking" value="요리"><label for="cooking">요리</label>
                        <input type="checkbox" name="interest" id="movie" value="영화"><label for="movie">영화</label>
                        <input type="checkbox" name="interest" id="game" value="게임"><label for="game">게임</label>
                    </td>
                </tr>
            </table>
            <br><br>
            <div align="center">
                <button type="submit" class="btn btn-secondary btn-sm">정보변경</button>
                <button type="button" class="btn btn-warning btn-sm" data-toggle="modal" data-target="#updatePwdForm">비밀번호변경</button>
                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
            </div>
            <br><br>
        </form>
    </div>
    <!--모달창으로 회원 탈퇴 화면 구성-->
    <div class="modal" id="deleteForm">
        <div class="modal-dialog">
            <div class="modal-content">
    
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">회원탈퇴</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
    
                <!-- Modal body -->
                <div class="modal-body" align="center">
                    
                    <b>
                        탈퇴 후 복구가 불가능합니다<br>
                        정말로 탈퇴하시겠습니까?<br><br>                        
                    </b>
                    <form action="<%=contextPath%>/delete.me" method="post">
                        <table>
                            <tr>
                                <td>비밀번호</td>
                                <td><input type="password" name="userPwd" required></td>
                            </tr>
                        </table>
                        <br>
                        <button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!--모달창으로 비밀번호 변경 화면구성 -->
    <!-- The Modal -->
    <div class="modal" id="updatePwdForm">
        <div class="modal-dialog">
            <div class="modal-content">
    
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">비밀번호 변경</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
    
                <!-- Modal body -->
                <div class="modal-body text-center">
                    <form action="<%= contextPath %>/updatePwd.me" method="post">
                        <!--현재 비밀번호, 변경할 비밀번호, 변경할 비밀번호 재입력-->
                        <input type="hidden" name="userId" value="<%=userId%>">
                        <table>
                            <tr>
                                <td>현재 비밀번호</td>
                                <td><input type="password" name="userPwd" required></td>
                            </tr>
                            <tr>
                                <td>변경할 비밀번호</td>
                                <td><input type="password" name="updatePwd" required></td>
                            </tr>
                            <tr>
                                <td>변경할 비밀번호 재입력</td>
                                <td><input type="password" name="checkPwd" required></td>
                            </tr>
                        </table>
                        <br>
                        <button type="submit" class="btn btn-secondary btn-sm" onclick="return validatePwd();">비밀번호 변경</button>
                    </form>
                </div>    
            </div>
        </div>
    </div>

    <script>
        $(function(){
            //관심분야 checkbox에 동적으로 체크될 수 있게끔 처리
            var interest= "<%=interest%>"; // "" or "운동, 등산"
            
            //순차적으로 checkbox들에 접근 후 접근한 input 요소의 value값이 
            //interest라는 자바스크립트 변수에 포함되어있는지 검사
            $("input[type=checkbox]").each(function(){
                //search 메소드: 해당 문자열에 매개변수로 넘긴 문자열이 포함되어 있다면
                //              해당 그 위치 인덱스값을 반환
                //              아니라면 -1을 반환
                if(interest.search($(this).val())!=-1){
                    //포함되어 있을 경우
                    //=>해당 input 요소에 checked 속성을 부여
                    $(this).attr("checked",true);
                }
            })
        });
        function validatePwd(){
            //변경할 비밀번호와 변경할 비밀번호 재입력 부분이 일치하는지 검사
            if($("input[name=updatePwd]").val()!=$("input[name=checkPwd]").val()){
                alert("비밀번호가 일치하지 않습니다");
                return false;
            }
        }
    </script>
</body>
</html>