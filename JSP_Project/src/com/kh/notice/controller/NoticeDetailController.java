package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전달받은 해당 공지사항 글번호 한 개를 이용해서 해당 글을 조회해와서 상세보기 페이지에 뿌려주기
		//1)인코딩 설정(POST 방식일때만)=>GET방식이므로 생략
		//2)요청 시 전달값을 뽑아서 변수에 기록 및 VO객체로 가공
		//클릭했을 때의 공지사항 글번호
		int noticeNo=Integer.parseInt(request.getParameter("nno"));
		
		//조회수 증가용 서비스 먼저 호출
		int result=new NoticeService().increaseCount(noticeNo);
		
		if(result>0){
			//조회수 증가가 잘되었다면=>상세조회 서비스 요청 후 상세보기 페이지로 포워딩
			//해당 글 내용물을 조회
			//공지사항 상세보기용 서비스를 호출
			Notice n=new NoticeService().selectNotice(noticeNo);
			request.setAttribute("n", n);
			//공지사항 상세보기 페이지 포워딩
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response);
		}else {
			//조회수 증가가 실패했다면=>에러페이지 포워딩
			request.setAttribute("errorMsg", "공지사항 조회 실패");
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
