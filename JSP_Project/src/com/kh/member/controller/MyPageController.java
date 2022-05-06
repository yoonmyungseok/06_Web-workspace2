package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//마이페이지 화면 포워딩 시
		//url로 직접 요청을 한다면 로그인하지 않은 상태에서도 마이페이지가 보여짐
		//=>session 객체에 loginUser 라는 키값으로 밸류가 존재 하는지 안하는지 검사
		HttpSession session=request.getSession();
		if(session.getAttribute("loginUser")==null) {
			//로그인 전
			session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스입니다");
			response.sendRedirect(request.getContextPath()); //메인페이지로
		}else {
			//로그인 후
			//마이페이지 화면 포워딩
			request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);;
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
