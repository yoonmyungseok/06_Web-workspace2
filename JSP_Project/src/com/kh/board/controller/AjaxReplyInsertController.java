package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.member.model.vo.Member;
import com.kh.member.model.vo.Reply;

/**
 * Servlet implementation class AjaxReplyInsertController
 */
@WebServlet("/rinsert.bo")
public class AjaxReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxReplyInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String replyContent=request.getParameter("content");
		int boardNo=Integer.parseInt(request.getParameter("bno"));
		
		//추가적으로 필요한 값: 현재 로그인한 사용자의 회원번호
		//=>세션객체로부터 얻어냄
		int userNo=((Member)request.getSession().getAttribute("loginUser")).getUserNo();
		
		//Reply 객체로 가공 후에 서비스로 넘기기
		Reply r=new Reply();
		r.setReplyContent(replyContent);
		r.setRefBoardNo(boardNo);
		r.setReplyWriter(String.valueOf(userNo));
		
		int result=new BoardService().insertReply(r);

		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
