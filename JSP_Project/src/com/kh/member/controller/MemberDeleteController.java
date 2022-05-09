package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		//2)요청시 전달값을 뽑아서 변수에 기록 및 VO객체로 가공
		//탈퇴하고자하는 회원의 아이디, 탈퇴하고자하는 회원의 비밀번호
		
		/*
		 * 로그인한 회원의 정보를 얻어내는 방법
		 * 방법1. input type="hidden"으로 애초에 요청시 숨겨서 전달하기
		 * 방법2. session 영역에 담긴 회원객체로부터 뽑기
		 */
		HttpSession session=request.getSession();
		String userId=((Member)session.getAttribute("loginUser")).getUserId();
		String userPwd=request.getParameter("userPwd");
		
		//3)MemberService 클래스의 어떤 메소드를 호출해서 요청 후 결과 받기
		int result=new MemberService().deleteMember(userId, userPwd);
		
		//4)성공 실패 여부에 따라 응답화면 지정
		if(result>0) {
			//성공->메인페이지 응답(로그아웃 되도록)
			session.setAttribute("alertMsg", "성공적으로 회원 탈퇴 되었습니다. 그동안 이용해주셔서 감사합니다");
			
			//로그아웃=>invalidate 메소드 활용
			//session.invalidate();
			//invalidate() 메소드를 사용 시 세션이 아예 만료되어
			//alertMsg 메시지 자체도 무효화 됨->alert가 되지 않음
			//removeAttribute("키값")을 이용해서 현재 로그인한 사용자의 정보만 지워주는 방식으로 로그아웃 시킴
			session.removeAttribute("loginUser");
			response.sendRedirect(request.getContextPath());
		}else {
			//실패->에러페이지 포워딩
			request.setAttribute("errorMsg", "회원 탈퇴에 실패했습니다");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
