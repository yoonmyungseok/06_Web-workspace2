package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//POST방식이므로 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		//enctype이 multipart/form-data로 잘 전송이 되었는지 검사
		if(ServletFileUpload.isMultipartContent(request)) {
			//1.파일과 관련된 처리내용들 지정(파일의용량,저장경로)
			//1_1.파일의 용량(byte 단위로)
			int maxSize=10*1024*1024; //10mbyte
			
			//1_2.전달된 파일을 저장시킬 서버의 경로
			String savePath=request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
			
			//2.전달된 파일명을 수정하고 그리고 서버에업로드
			//MultipartRequest 객체 생성구문=>request 객체를 MultipartRequest 형식으로 변환
			//=>파일명 수정
			//=>서버로 업로드
			MultipartRequest multiRequest=new MultipartRequest(request, savePath, maxSize, "utf-8", new MyFileRenamePolicy());
			
			//3.본격적으로 SQL문을 실행할 때 필요한 요청 시 전달값들을 뽑아서 변수에 기록 및 VO객체로 가공
			//-공통적으로 수행: Board 테이블 Update 구문 실행
			int boardNo=Integer.parseInt(multiRequest.getParameter("bno"));
			String category=multiRequest.getParameter("category");
			String boardTitle=multiRequest.getParameter("title");
			String boardContent=multiRequest.getParameter("content");
			
			Board b=new Board();
			b.setBoardNo(boardNo);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setCategory(category);
			
			//-옵션으로 수행: 새로이 전달된 첨부파일이 있을 경우 필요한 값 뽑기
			//					Attachment 테이블에 Update 또는 Insert
			Attachment at=null;
			
			//우선적으로 전달된 첨부파일이 있는지 검사
			//전달된 원본파일명이 있는지
			if(multiRequest.getOriginalFileName("reUpfile")!=null) {
				//전달받은 첨부파일이 있다면
				//Attachment 관련한 쿼리문들 중에서 공통적으로 필요한 값들만 우선적으로 가공
				//원본명, 수정명, 저장경로
				at=new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("resources/board_upfiles/");
				
				//수정 전에 해당 글에 원래 첨부파일이 있었는지?
				//만약 원래 첨부파일이 있었다면 
				//원래 파일의 파일번호, 수정명을 넘겨줬었음
				if(multiRequest.getParameter("originFileNo")!=null) {//원래 파일이 있었다면
					//CASE1. Attachment 테이블 update
					//필요한 필드값만 셋팅
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
					
					//기존의 첨부파일 삭제
					new File(savePath+multiRequest.getParameter("originFileName")).delete();
				}else {//원래 파일이 없었다면
					//CASE2. Attachment 테이블 insert
					//필요한 필드값만 셋팅
					at.setRefNo(boardNo);
					
				}
			}
			//모두 한개의 트랜잭션으로 처리하기 위해 위 지점에서 Service로 요청	
			int result=new BoardService().updateBoard(b,at);
			if(result>0) {
				//성공=>해당 글의 상세보기페이지로(detail.bo?bno=x)
				request.getSession().setAttribute("alertMsg", "성공적으로 수정되었습니다");
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
				
			}else {
				//실패
				request.setAttribute("errorMsg", "게시글 수정 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
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
