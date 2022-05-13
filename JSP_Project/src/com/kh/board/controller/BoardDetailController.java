package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//사용자가 요청 시 전달한 해당 글번호 뽑기
		int boardNo=Integer.parseInt(request.getParameter("bno"));
		
		//조회수1증가
		int result=new BoardService().increaseCount(boardNo);
		
		//조회수 증가가 성공했다면 상세조회
		if(result>0) {
			//조회수 증가 성공=>게시글 상세조회
			Board b=new BoardService().selectBoard(boardNo);
			Attachment at=new BoardService().selectAttachment(boardNo);
			
			//조회한 내용을 request에 담아서 전달
			request.setAttribute("b", b);
			request.setAttribute("at", at);
			
			//포워딩 처리
			request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
			//포워딩 테스트
			//request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
		}else {
			//에러페이지
			request.setAttribute("errorMsg", "게시글 상세조회 실패");
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
