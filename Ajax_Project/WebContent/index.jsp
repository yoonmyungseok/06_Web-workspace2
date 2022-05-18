<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>title</title>
<!--jQuery 방식으로 AJAX 요청 시 필수-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <h1>AJAX 개요</h1>
    <p>
        Asynchronous JavaScript And XML의 약자 <br>
        서버로부터 데이터를 가져와 전체 페이지를 새로 고치지 않고 
        일부만 로드할 수 있게 하는 기법<br>
        우리가 기존에 a 태그로 요청하거나 form 태그로 요청하는 방식은 동기식 요청이였음 <br>
        =>응답페이지가 돌아와야 결과를 볼 수 있음 (페이지 화면이 깜빡거림)<br>
        비동기식 요청을 보내기 위해서는 AJAX라는 기술이 필요함<br><br>
        
        *동기식/비동기식
        -동기식:    요청 처리 후 그에 해당되는 응답페이지가 돌아와야만 그 다음 작업도 가능<br>
                    만약 서버에서 호출된 결과까지의 시간이 지연되면 무작정 계속 기다려야 함(흰페이지로 보여질것임)<br>
                    전체 페이지 자체가 리로드(새로고침)됨, 즉 페이지가 기본적으로 깜빡거림<br>
        -비동기식:  요청 처리 후 현재페이지를 그대로 유지하면서 중간중간마다 추가적인 요청을 보내줄 수 있음<br>
                    요청을 한다고 해서 다른페이지로 넘어가지 않음(현재페이지 그대로임)
                    요청을 보내놓고 그에 해당되는 응답이 돌아올때 까지 
                    현재페이지에서 다른 작업을 할 수 있음<br><br>
        *사용예시<br>
        네이버 홈페이지의 실시간 검색어 기능, 검색어 자동완성 기능, 아이디 중복 체크기능 <br><br>

        *비동기식의 단점<br>
        -현재페이지에 지속적으로 리소스가 쌓임 => 페이지가 현저히 느려질 수 있음<br>
        -페이지 내에 복잡도가 기하급수적으로 증가=> 에러 발생시 디버깅이 어려움<br>
        -요청 후 돌아온 응답데이터를 가지고 현재페이지에서 새로운 요소를 만들어서 매번 뿌려줘야 함
        =>DOM 요소들을 새로이 만들어내는 구문을 잘 익혀둬야 함<br><br>

        *AJAX의 구현방식: JavaScript 방식/ jQuery 방식(코드가 간결하고 사용하기 쉬움)<br><br>
    </p>
    <pre>
        [표현법]
        $.ajax({
            속성명:속성값,
            속성명:속성값,
            ...
        });

        *주요속성
        -url: 요청할 url(필수입력사항)=>form 태그의 action 속성과 같은 역할
        -type/method: 요청 전송 방식(get/post, 생략시 기본값 get)=>form 태그의 method 속성과 같은 역할
        -data: 요청시 전달할 값(키-밸류 세트로 객체 형식으로 지정)=>form 태그 내부의 input 태그들
        -success: ajax 통신 성공 시 실행할 내용물을 익명함수로 작성
        -error: ajax 통신 실패 시 실행할 내용물을 익명함수로 작성
        -complete: ajax 통신을 실패했든 성공했든 간에 무조건 실행할 함수를 정의

        * 부수적인 속성
        - async : 서버와의 비동기 처리 방식 설정 여부 (기본값 true)
        - contentType : request 의 데이터 인코딩 방식 정의 (보내는 측의 데이터 인코딩)
        - dataType : 서버에서 response 로 오는 데이터의 데이터 형 설정, 값이 없다면 스마트하게 판단함
        xml : 트리 형태의 구조
        json : 맵 형태의 데이터 구조 (일반적인 데이터 구조)
        script : javascript 및 일반 String 형태의 데이터
        html : html 태그 자체를 return 하는 방식
        text : String 데이터
        - accept : 파라미터의 타입을 설정 (사용자 특화 된 파라미터 타입 설정 가능)
        - beforeSend : ajax 요청을 하기 전 실행되는 이벤트 callback 함수 (데이터 가공 및 header 관련 설정)
        - cache : 요청 및 결과값을 scope 에서 갖고 있지 않도록 하는 것 (기본값 true)
        - contents : jQuery 에서 response 의 데이터를 파싱하는 방식 정의
        - context : ajax 메소드 내 모든 영역에서 파싱 방식 정의
        - crossDomain : 타 도메인 호출 가능 여부 설정 (기본값 false)
        - dataFilter : response 를 받았을 때 정상적인 값을 return 할 수 있도록 데이터와 데이터 타입 설정
        - global : 기본 이벤트 사용 여부 (ajaxStart, ajaxStop) (버퍼링 같이 시작과 끝을 나타낼 때, 선처리 작업)
        - password : 서버에 접속 권한 (비밀번호) 가 필요한 경우
        - processData : 서버로 보내는 값에 대한 형태 설정 여부 (기본 데이터를 원하는 경우 false 설정)
        - timeout : 서버 요청 시 응답 대기 시간 (milisecond)
    </pre>
    <hr>
    <h1>jQuery 방식을 이용한 AJAX 테스트</h1>
    <h3>1. 버튼 클릭 시 get 방식으로 서버에 데이터 전송 및 응답</h3>
    입력: <input type="text" id="input1">
    <button id="btn1">전송</button>
    <br>
    응답: <label id="output1">현재 응답 없음</label>
    <script>
        $(function(){
            $("#btn1").click(function(){
                //동기식 통신(페이지가 전환됨)
                //location.href="jqAjax1.do?input="$("#input1").val();

                //비동기식 통신(페이지가 전환되지 않음)
                $.ajax({
                    url:"jqAjax1.do",
                    data:{
                        input:$("#input1").val()
                    },
                    type:"get",
                    success:function(result){
                        //console.log("응답성공");
                        //console.log(result);
                        $("#output1").text(result);
                        $("#output1").css("color","blue");
                    },
                    error:function(){
                        console.log("응답실패");
                    },
                    complete:function(){
                        console.log("성공 실패 여부랑 상관없이 무조건 호출");
                    }
                });
            })
        })
    </script>
    <hr>
    <h3>2. 버튼 클릭시 post 방식으로 서버에 데이터 전송 및 응답</h3>
    이름: <input type="text" id="input2_1"><br>
    나이: <input type="number" id="input2_2"><br>
    <button onclick="test2();">전송</button>
    <br>
    응답: <label id="output2">현재 응답 없음</label>

    <script>
        function test2(){
            $.ajax({
                url:"jqAjax2.do",
                data:{
                    name:$("#input2_1").val(),
                    age:$("#input2_2").val()
                },
                type:"post",
                success:function(result){
                    //console.log("응답 성공");
                    //console.log(result);
                    //$("#output2").text(result);
                    //$("#output2").css("color","red");

                    //여러개의 데이터가 JSONArray에 담겨서 응답된 경우
                    //console.log(result);
                    //result==배열==반복문 활용 가능
                    /*
                    var responseData="";
                    for(var i=0; i<result.length; i++){
                        responseData+=result[i]+"/";
                    }

                    $("#output2").text(responseData);
                    */
                    //여러개의 데이터가 JSONObject에 담겨서 응답된 경우
                    //console.log(result);
                    $("#output2").text("이름: "+result.name+", 나이: "+result.age);

                    //초기화 된 듯한 효과
                    $("#input2_1").val("");
                    $("#input2_2").val("");
                },
                error:function(){
                    console.log("응답 실패");
                },
                complete:function(){
                    console.log("성공이던 실패던 한번은 호출됨");
                }
            });
        }
    </script>
    <hr>
    <h3>3. 서버로 데이터 전송 후, 조회된 객체를 응답데이터로 받기</h3>
    회원번호 입력: <input type="number" id="input3">
    <button onclick="test3();">조회</button>
    
    <div id="output3"></div>
    
    <script>
        function test3() {
            $.ajax({
                url: "jqAjax3.do",
                data: { no: $("#input3").val() },
                success: function (result) {
                    //console.log(result);
                    var resultStr="회원번호: "+result.memberNo+"<br>"
                    +"이름: "+result.memberName+"<br>"
                    +"나이: "+result.age+"<br>"
                    +"성별: "+result.gender+"<br>";
                    $("#output3").html(resultStr);
                },
                error: function () {
                    console.log("응답 실패");
                }
            });
        }
    </script>

    <hr>
    <h3>4. 응답데이터로 여러개의 객체들이 담겨있는 ArrayList 받기</h3>
    <button onclick="test4();">회원 전체조회</button>

    <table id="output4" border="1" style="text-align: center;">
        <thead>
            <tr>
                <th>회원번호</th>
                <th>회원명</th>
                <th>나이</th>
                <th>성별</th>
            </tr>
        </thead>
        <tbody>

        </tbody>
    </table>

    <script>
        function test4(){
            $.ajax({
                url:"jqAjax4.do",
                success:function(result){
                    //console.log(result);
                    var str="";

                    for(var i=0; i<result.length; i++){
                        //console.log(result[i].memberName);
                        str+="<tr>"
                            +"<td>"+result[i].memberNo+"</td>"
                            +"<td>"+result[i].memberName+"</td>"
                            +"<td>"+result[i].age+"</td>"
                            +"<td>"+result[i].gender+"</td>"
                            +"</tr>";
                    }
                    $("#output4>tbody").html(str);
                    },
                error:function(){
                    console.log('응답실패');
                }
            })
        }
    </script>
</body>
</html>