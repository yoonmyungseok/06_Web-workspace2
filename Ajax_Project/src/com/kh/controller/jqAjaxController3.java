package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

/**
 * Servlet implementation class jqAjaxController3
 */
@WebServlet("/jqAjax3.do")
public class jqAjaxController3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jqAjaxController3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberNo=Integer.parseInt(request.getParameter("no"));
		
		//DB로부터 해당 회원 정보를 조회했다라는 가정 하에 Member 객체 값을 담아보자
		Member m=new Member(memberNo, "고길동", 50, "남");
		
		//응답데이터 전송
		//response.setContentType("text/html; charset=utf-8");
		//response.getWriter().print(m);
		//응답데이터가 하나여도 객체형태이기 때문에 내부적으로 toString()메소드가 호출되어 한개의 문자열로 응답데이터가 넘어감
		//=>프론트단에서 내가 원하는 필드값만 추출하려면 고생스러움
		//=>프론트단에서 내가 편하게 필드값을 추출하고자한다면 JSON을 사용해야함
		
		//Member 객체를 JSON으로 가공 후 전달
		//JSONObject로 가공=>{속성명:속성값}
		//					=>{필드명:필드값, 필드명:필드값}
		/*
		JSONObject jObj=new JSONObject();
		jObj.put("memberNo", m.getMemberNo());
		jObj.put("memberName", m.getMemberName());
		jObj.put("age", m.getAge());
		jObj.put("gender", m.getGender());
		
		//응답데이터 전송
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(jObj);
		*/
		
		//매번 위와 같이 가공하기 귀찮음(정석방법이긴 함)
		//=>외부 라이브러리를 더 이용하면 한번에 가공 가능
		//=>GSON: Google JSON의 약자
		//다운로드 링크:https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.2
		
		response.setContentType("application/json; charset=utf-8");
		
		//Gson gson=new Gson();
		//gson.toJson(m, response.getWriter());
		//toJson(응답하고자하는 객체, 응답할 스트림);
		//response.getWriter() (==PrintWriter 객체)라는 통로로
		//m이라는 객체를 가공해서 응답하겠다
		//단, 변환시 전달되는 각 키값은 VO 클래스에서 내가 정의한 필드명으로 자동으로 잡힘
		
		new Gson().toJson(m, response.getWriter());
		
		//VO 객체 하나만 응답시 JSONObject 형태로 가공되서 응답됨
		
		/*
		 * Member를 응답하고자함
		 * {필드명: 필드값, 필드명: 필드값, ...}=>JSONObject 타입으로
		 * 
		 * ArrayList<Member>를 응답하고자 함
		 * [{필드명:필드값, 필드명:필드값, ...},{},{},{}]=>JSONArray 타입으로
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
