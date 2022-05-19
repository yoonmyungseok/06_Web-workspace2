package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("잘 실행되나?");
		/*
		 * <HttpServletRequest 객체와 HttpServletResponse 객체>
		 * -request: 사용자가 서버로요청할때의 전달값들이 담겨있음
		 * -response: 요청에 응답할때 필요한 객체
		 * <Get 방식과 Post 방식>
		 * Get 방식: 사용자가 입력한 값들이 url에 노출/데이터 길이제한 있음/ 대신 즐겨찾기 기능 사용할 때 편리 
		 * Post 방식: 사용자가 입력한 값들이 url에 노출되지 않음/ 데이터 길이제한 없음/  즐겨찾기 기능 불편 / Timeout이 존재
		 * */
		//로그인 기능=>POST 방식
		//1) UTF-8로 인코딩 처리(POST 방식일 경우)
		request.setCharacterEncoding("UTF-8");

		//2)요청시 전달된 값들을 뽑기(request의 parameter 영역으로부터)
		//및 변수에 기록하고, VO객체로 가공하기
		//request.getParameter("키값"): String(밸류값)
		//request.getParameterValues("키값"): String[] (밸류값들)
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		//System.out.println(userId);
		//System.out.println(userPwd);
		
		//saveId 체크 여부 골라내기
		String saveId=request.getParameter("saveId"); // "y" / null
		
		if(saveId!=null&&saveId.equals("y")) {
			//아이디를 저장하겠따=>saveId라는 키값으로 넘겨받은 아이디값을 쿠키로 저장
			
			//Cookie: 사이트가 사용하고있는 서버에서 사용자의 컴퓨터에 저장하는 정보
			//			주로 보안과 관련없는 기능을 다룰 때 사용한다
			/*
			 * 쿠키 생성
			 * -쿠키는 객체를 생성한 다음 응답정보에 첨부해야 완전히 생성된다
			 * -쿠키 생성 시 name, value는 필수(키-밸류 세트로 입력)
			 * -expires (만료기간)은 옵션
			 * -name, value는 모두 문자열이여야 한다
			 * -name이 중복될 시 덮어씌워짐
			 */
			Cookie cookie=new Cookie("saveId", userId);
			cookie.setMaxAge(1*60*60*24); //만료시간 1일 (초단위 작성)
			response.addCookie(cookie);
		}else {
			//아이디를 저장하지 않겠다=>saveId라는 키값을 가진 쿠키를 삭제시키겠다
			
			/*
			 * 쿠키 삭제
			 * -쿠키 삭제는 따로 명령이 없다
			 * -0초로 만료시간을 지정하고 똑같은 키값으로 쿠키를 생성해서 덮어씌우면 됨
			 */
			Cookie cookie=new Cookie("saveId", userId);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		//3) 해당 요청을 처리하는 서비스 클래스의 어떤 메소드를 호출
		Member loginUser=new MemberService().loginMember(userId,userPwd);

		
		//4)처리된 결과를 가지고 응답화면 지정
		//System.out.println(loginUser);
		
		if(loginUser==null) {
			//로그인 실패=>에러페이지 응답
			//4_1)응답 페이지에서 필요로 하는 데이터를 담기(request의 attribute 영역)
			request.setAttribute("errorMsg","로그인에 실패했습니다");

			//4_2)응답 페이지 위임을 위한 RequestDispatcher 객체 생성(jsp 경로 넘겨주기)
			RequestDispatcher view=request.getRequestDispatcher("views/common/errorPage.jsp");

			//4_3)request, response 객체를 넘겨주는 포워딩 과정
			view.forward(request, response);
			/*
			 *	응답페이지를 요청하는 방식
			 *	1.포워딩 방식: RequestDispatcher 객체를 이용하는 방법
			 *					url이 변경되지 않는 방식(요청했을 때의 url이 여전히 주소창에 남아있음)
			 *					=>현재 내가 작성중인 servlet의 url 매핑값이 유지
			 *	2.url 재요청방식(sendRedirect 방식)
			 *	:url 매핑값을 제시해서 화면을 띄우는 방식(일종의 새로고침 개념)
			 *	
			 */
		}else {
			//로그인 성공=>메인페이지 응답(index.jsp)
			//4_1)응답페이지에서 필요로 하는 데이터를 담기
			/*
			 *  응답페이지에 전달할 값이 있을 경우 어딘가에 담아야함
			 *  (데이터를 담아줄 수 있는 Servlet scope 내장객체 4종류)
			 *  1. application: application 에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내 쓸 수 있음
			 *  2. session: session 에 담은 데이터는 모든 jsp와 servlet에서 꺼내다 쓸 수 있음
			 * 				단, 한번 담은 데이터는 내가 직접 session으로부터 지우기 전까지
			 *				또는, 서버가 멈추기 전까지
			 *				또는, 브라우저가 종료되기 전까지
			 *  3. request: request에 담은 데이터는 해당 request를 포워딩한 응답 jsp에서만 꺼내다 쓸 수 있음
			 *  4. page: 해당 jsp 페이지에서만 꺼내다 쓸 수 있음 
			 *  => session, request를 주로 쓴다
			 *	객체들의 사용법
			 *	값을 담을 때:객체명.setAttribute("키",밸류);
			 *	값을 꺼낼 때:객체명.getAttribute("키");=>Object 형식의 밸류
			 *	값을 지울 때:객체명.removeAttribute("키");->키-밸류 세트로 지워줌
			 */
			//로그인한 회원의 정보를 로그아웃 하기 전까지
			//계속 가져다 써야하기 때문에 session에 담기

			//4_1_1)Servlet에서 JSP 내장객체인 session을 사용하고자 한다면
			//		우선적으로 session 객체를 만들어야 함
			HttpSession session=request.getSession();

			//4_1_2)만들어진 session 객체에 로그인한 회원 정보를 키-밸류 세트로 담기
			session.setAttribute("loginUser", loginUser);
			
			//4_1_3)로그인이 성공했을 경우 보여질 성공 메시지를 session에 담기
			session.setAttribute("alertMsg", "성공적으로 로그인이 되었습니다");

			/*
			//포워딩 방식으로 응답할 뷰 출력
			//4_2)RequestDisptcher 객체 생성
			RequestDispatcher view=request.getRequestDispatcher("index.jsp");
			//주소창 예측:localhost:8888/jsp
			//실제 주소창: locathost:8888/jsp/login.me
			
			//4_3)포워딩
			view.forward(request, response);
			*/
			
			//포워딩 방식으로 응답페이지를 요청했을 경우
			//해당 선택된 jsp가 보여질 뿐 url에는 여전히 현재 이 서블릿 매핑값이 남아있음
			//=url 재요청 방식으로 응답페이지 요청
			//response.sendRedirect("/jsp");
			response.sendRedirect(request.getContextPath());
			//request.getContextPath()
			//=>현재 이 프로젝트의 context root(==context path)를 반환해줌
			//예시)1_Servlet에서 이 메소드를 호출 시 "/1_Servlet" 이라는 context root가 반환
			//localhost:8888를 시작점으로 잡기
			//localhost:8888/jsp로 메인페이지로 접속이 됨

			//각 서비스마다 사용되는 방식 다름
			//=>로그인 시에는 2번 방법이 쓰임
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
