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
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 셋팅(POST 방식인 경우에만 해당)
		request.setCharacterEncoding("utf-8");
		
		//2)요청시 전달값을 뽑아서 변수에 기록하고 VO 객체로 가공하기
		String userId=request.getParameter("userId"); 
		String userPwd=request.getParameter("userPwd");
		String userName=request.getParameter("userName");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String[] interestArr=request.getParameterValues("interest");
		//String[] --> String
		//["운동","등산"] --> "운동, 등산"
		String interest="";
		if(interest!=null) {
			interest=String.join(", ", interestArr);
		}
		//매개변수 생성자를 이용해서 Member 객체에 담기
		Member m=new Member(userId, userPwd, userName, phone, email, address, interest);
		
		//3) 요청 처리(MemberService 클래스의 어떤 메소드를 호출해서 결과 받기)
		int result=new MemberService().insertMember(m);
		
		//4)처리 결과를 가지고 사용자가 보게 될 응답 뷰를 지정
		if(result>0) {
			//성공=>메인페이지(url 재요청 방식)
			
			//우선적으로 session에 현재 회원가입에 성공한 회원의 정보를 담아둘것
			//=>메인페이지로 이동시 자동으로 로그인한 효과
			HttpSession session=request.getSession();
			
			//session.setAttribute("loginUser", m);
			//=>자동로그인 이후 게시글 작성이 안되는 이슈가 있었음
			
			//=>성공 알람메시지도 같이 담기
			session.setAttribute("alertMsg", "회원가입에 성공했습니다");
			
			//response.sendRedirect("url 요청값");
			response.sendRedirect(request.getContextPath());
			
			
		}else {
			//실패=>에러페이지 포워딩
			
			//4_1)응답페이지에서 필요로 하는 데이터를 담기(request의 attribute 영역에)
			request.setAttribute("errorMsg", "회원가입에 실패했습니다");
			
			//4_2)응답하고자 하는 jsp 파일 경로를 제시하면서 RequestDispatcher객체 생성 및 포워딩
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
