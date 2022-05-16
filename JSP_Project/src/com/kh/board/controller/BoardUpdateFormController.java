package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;

/**
 * Servlet implementation class BoardUpdateFormController
 */
@WebServlet("/updateForm.bo")
public class BoardUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//우선적으로 수정하기 폼에서 필요로하는 데이터들을 요청
		//카테고리 전체조회, 해당게시글 상세조회, 해당게시글에 딸린 첨부파일 상세조회
		BoardService bService=new BoardService();
		
		int boardNo=Integer.parseInt(request.getParameter("bno"));
		ArrayList<Category> list=bService.selectCategoryList();
		Board b=bService.selectBoard(boardNo);//글번호,카테고리명,제목,내용,작성자아이디,작성일
		
		Attachment at=bService.selectAttachment(boardNo);
		//첨부파일이 없을 경우:null
		//첨부파일이 있을 경우:파일번호,원본명,수정명,저장경로
		
		//수하물 붙이기
		request.setAttribute("list", list);
		request.setAttribute("b", b);
		request.setAttribute("at",at);
		
		//일반게시글 수정하기 폼 포워딩
		request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
