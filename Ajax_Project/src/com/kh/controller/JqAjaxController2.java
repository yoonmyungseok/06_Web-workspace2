package com.kh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class JqAjaxController2
 */
@WebServlet("/jqAjax2.do")
public class JqAjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post 방식으로 요청이 넘어올 경우에는 항상 인코딩설정부터 UTF-8로 지정해줬었음
		//=>AJAX는 기본 인코딩이 UTF-8임
		
		//요청시 전달값 뽑기
		String name=request.getParameter("name");
		int age=Integer.parseInt(request.getParameter("age"));
		
		//한 개의 응답을 보낼 경우
		//응답데이터
		//String responseData="이름: "+name+", 나이: "+age;
		
		//응답데이터 보내기
		//1. 응답데이터의 형식 지정
		//response.setContentType("text/html; charset=utf-8");
		
		//2. 보내기
		//response.getWriter().print(responseData);
		
		//여러개의 응답 데이터를 보낼 경우
		//요청 처리를 다 했다라는 가정하에
		//response.setContentType("text/html; charset=utf-8");
		//response.getWriter().print("이름: "+name);
		//response.getWriter().print("나이: "+age);
		
		//결론: ajax는 기본적으로 결과를 오로지 한개만 응답할 수 있음
		//=>단, 외부 라이브러리를 이용하면 응답 데이터를 여러개 전달할 수 있음
		//=>JSON(JavaScript Object Notation: 자바스크립트 객체 표기법)
		
		/*
		 * JSON
		 * - ajax 통신 시 데이터 전송에 사용되는 포맷 형식중 하나
		 * - 라이브러리 필요
		 *   다운로드 링크: https://code.google.com/archive/p/json-simple/downloads
		 *   json-simple-1.1.1.jar
		 * 
		 * -JSON 처리 시 사용되는 클래스 종류
		 * 1. JSONArray=>자바스크립트의 배열 형태로 넘어가게 됨=>[응답데이터, 응답데이터, ...]
		 * 2. JSONObject=>자바스크립트의 객체 형태로 넘어가게 됨=>{키:응답데이터, 키:응답데이터, ...}
		 */
		
		//JSON을 이용해서 여러개의 응답데이터 보내기
		//1.JSONArray를 이용한 방법
		//JSONArray jArr=new JSONArray(); // []
		//jArr.add("이름: "+name); // ["이름: 홍길동"]
		//jArr.add("나이: "+age); // ["이름: 홍길동", "나이: age"]
		
		//참고 자바스크립트의 배열==자바의 ArrayList(종류상관없음, 사이즈제한없음, 인덱스개념있음)
		//ArrayList에 값을 추가할 때 필요한 메소드: list.add(추가할값);
		
		//응답할 데이터를 전송시 데이터의 컨텐트타입을 제대로 지정해야
		//문자열 형식이 아닌 진짜 배열 형식으로 넘어가게 됨
		//response.setContentType("application/json; charset=utf-8");
		//response.getWriter().print(jArr);
		
		//2. JSONObject를 이용한 방법
		JSONObject jObj=new JSONObject(); //{}
		jObj.put("name", name); //{name:"홍길동"}
		jObj.put("age", age); //{name:"홍길동", age:20}
		
		//참고 자바스크립트의 객체==자바의 Map 계열(밸류종류 상관없음, 사이즈제한없음, 인덱스개념없음, 키는중복불가)
		//Map에 값을 추가할 때 필요한 메소드: hashMap.put("키", 밸류);
		
		//데이터 응답(application/json)
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(jObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
