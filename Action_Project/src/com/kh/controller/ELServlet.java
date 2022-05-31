package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.model.vo.Person;

/**
 * Servlet implementation class ELServlet
 */
@WebServlet("/el.do")
public class ELServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ELServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 데이터들을 담을 수 있는 JSP 내장객체 4종류(scope==영역)
		 * 1.ServletContext (application scope)
		 * 한 애플리케이션당 단 1개 존재하는 객체
		 * 이 영역에 데이터를 담으면 애플리케이션 전역에서 사용 가능
		 * =>공유 범위가 가장큼
		 * 
		 * 2.HttpSession(session scope)
		 * 한 브라우저당 단 1개 존재하는 객체
		 * 이 영역에 데이터를 담으면 jsp/servlet 단에서 골고루 사용 가능
		 * 단, 값이 한번 담기면 서버가 멈추거나, 브라우저가 종료되거나, 값을 지우기 전까지 사용 가능
		 * =>공유 범위가 다소 제한적
		 * 
		 * 3.HttpServletRequest(request scope)
		 * 요청 및 응답시 매번 1개 생성되는 객체
		 * 이 영역에 데이터를 담으면 해당 request 객체를 포워딩 받는 응답 jsp에서만 사용 가능 (1회성 사이클)
		 * =>공유 범위가 해당 요청에 대한 응답 jsp 단 하나뿐
		 * 
		 * 4.PageContext(page scope)
		 * 현재 그 jsp페이지에서만 사용 가능
		 * =>공유범위가 가장 작음(해당 그 페이지)
		 * 
		 * -위의 객체들에 값을 담을 때는: 객체명.setAttribute("키", 밸류);
		 * -값을 꺼내고자 할 때는: 객체명.getAttribute("키"); =>Object 타입
		 * -값을 지우고자 할 때는: 객체명.removeAttribute("키");
		 * 
		 *
		 */
		//requestScope에 값 담기
		request.setAttribute("classRoom", "D 강의장");
		request.setAttribute("student", new Person("홍길동",23,"남자"));
		
		//sessionScope에 값 담기
		HttpSession session=request.getSession();
		session.setAttribute("academy", "KH 정보교육원");
		session.setAttribute("teacher", new Person("김가현",20,"여자"));
		
		//requestScope와 sessionScope에 동일한 키값으로도 데이터 담기
		request.setAttribute("scope", "request");
		session.setAttribute("scope", "session");
		
		//applicationScope에 값 담기
		ServletContext application=request.getServletContext();
		application.setAttribute("scope", "application");
		
		request.getRequestDispatcher("views/1_EL/01_el.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
