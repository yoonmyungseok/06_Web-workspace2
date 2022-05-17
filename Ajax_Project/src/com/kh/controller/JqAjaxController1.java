package com.kh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JqAjaxController1
 */
@WebServlet("/jqAjax1.do")
public class JqAjaxController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청 시 전달값 뽑기 및 변수에 기록
		String str=request.getParameter("input");
		
		//System.out.println("요청시 전달값:"+str);
		
		//요청 처리를 다 했다라는 가정 하에 응답할 데이터
		String responseData="입력된 값: "+str+", 길이: "+str.length();
		
		//응답 데이터 보내기
		//1. 혹시라도 응답데이터에 한글이 있을 경우
		//	응답데이터에 대한 인코딩 설정(UTF-8)&MIMETYPE(text/html)
		response.setContentType("text/html; charset=utf-8");
		
		//2. jsp와의 통로를 열어서 데이터만 전송
		response.getWriter().print(responseData);
		//=>자바 코드 안에 html 코드 넣었을 때 썼던 메소드들 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
