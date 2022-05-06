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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1) 인코딩 설정(POST 방식일때만)
		request.setCharacterEncoding("UTF-8");
		
		//2)요청 시 전달값들을 뽑아서 변수에 기록 및 VO 객체로 가공
		String userId=request.getParameter("userId"); //변경할 회원 구분용 (WHERE절)
		String userName=request.getParameter("userName");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String[] interestArr=request.getParameterValues("interest"); //바꿀내용(SET절)
		
		//String[] -->String
		String interest="";
		if(interestArr!=null) {
			interest=String.join(", ", interestArr);
		}
		
		Member m=new Member(userId, userName, phone, email, address, interest);
		
		//3)MemberService 클래스의 어떤 메소드를 호출하여 요청 처리(전달값을 넘기고 결과값 반환)
		Member updateMem=new MemberService().updateMember(m);
		
		//4)돌려받은 처리 결과를 가지고 사용자가 보게될 응답 뷰 지정
		
		if(updateMem==null) {
			//실패=>에러페이지 응답
			//4_1)응답페이지에서 필요로하는 데이터 담기
			request.setAttribute("errorMsg", "회원정보 수정에 실패했습니다.");
			
			//4_2, 4_3)포워딩 
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}else {
			//성공=>마이페이지 응답
			//바뀐 회원정보를 session에 옮겨담기
			HttpSession session=request.getSession();
			session.setAttribute("loginUser", updateMem);
			//같은 키값은 중복되어 존재할 수 없다=>같은 키값을 제시했을 때에는 value가 덮어씌워짐
			
			//성공 알림메시지
			session.setAttribute("alertMsg", "회원정보 수정에 성공했습니다");
			
			//url 재요청 방식으로 응답화면 보여주기
			response.sendRedirect(request.getContextPath()+"/myPage.me");
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
