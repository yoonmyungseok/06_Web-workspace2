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

/**
 * Servlet implementation class ThumbnailDetailController
 */
@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//글번호 뽑기
		int boardNo=Integer.parseInt(request.getParameter("bno"));
		
		//조회 요청
		//우선적으로 조회수 증가용 서비스 먼저 요청(기존의 메소드 호출)
		int result=new BoardService().increaseCount(boardNo);
		
		if(result>0) { //조회수 증가 성공
			//Board 테이블로부터 게시글 정보 조회(글번호, 글제목, 글내용, 작성일, 작성자아이디)
			//일반게시판용 selectBoard 메소드 재활용
			//기존의 쿼리문은 INNER JOIN을 했었지만
			//사진게시판에서는 카테고리 정보가 NULL이기 때문에 INNER JOIN시 조회가 되지 않는 이슈 발생
			//=>카테고리 컬럼 기준으로 일치하는 컬럼도, 일치하지 않는 컬럼도 가져오게끔 수정하기 위해서 LEFT JOIN으로 변경시켜줌
			//(수정하더라도 일반게시판 상세조회시 문제 없음, 사진게시판 상세조회시에도 활용 가능)
			Board b=new BoardService().selectBoard(boardNo);
			
			//해당 게시글에 딸린 첨부파일들 조회
			ArrayList<Attachment> list=new BoardService().selectAttachmentList(boardNo);		
			
			request.setAttribute("b", b);
			request.setAttribute("list", list);
			
			//상세보기 페이지 포워딩
			request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
			
		}else {//조회수 증가 실패=>에러페이지 포워딩
			request.setAttribute("errorMsg", "조회수 증가 실패");
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
