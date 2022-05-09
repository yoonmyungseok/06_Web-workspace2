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
 * Servlet implementation class MemberUpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class MemberUpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 설정(Post 방식일 때만)
		request.setCharacterEncoding("utf-8");
		
		//2)요청 시 전달된 값들을 뽑아서 변수에 기록 및 VO 객체로 가공
		//아이디, 기존 비밀번호, 변경할 비밀번호
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		String updatePwd=request.getParameter("updatePwd");
		
		//3)MemberService의 어떤 메소드를 호출하여 요청 및 결과 받기
		Member updateMem=new MemberService().updatePwdMember(userId,userPwd,updatePwd);
		
		//4)전달받은 결과에 따라 응답화면 지정
		HttpSession session=request.getSession();
		if(updateMem==null) {
			//실패->마이페이지 응답(실패메시지 alert)
			session.setAttribute("alertMsg", "비밀번호 변경에 실패했습니다");			
		}else {
			//성공->마이페이지 응답(성공메시지 alert)
			session.setAttribute("alertMsg", "성공적으로 비밀번호가 변경되었습니다");
			session.setAttribute("loginUser", updateMem);
		}
		response.sendRedirect(request.getContextPath()+"/myPage.me");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
