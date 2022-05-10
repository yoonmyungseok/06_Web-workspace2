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
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 셋팅
		request.setCharacterEncoding("utf-8");
		
		//2)요청 시 전달값 뽑아서 변수에 기록 및 VO객체로 가공
		//글번호, 글제목, 글내용
		int noticeNo=Integer.parseInt(request.getParameter("nno"));
		String noticeTitle=request.getParameter("title");
		String noticeContent=request.getParameter("content");
		
		Notice n=new Notice();
		n.setNoticeNo(noticeNo);
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		
		//3)NoticeService로 요청처리 및 결과 받기
		int result=new NoticeService().updateNotice(n);
		
		//4)결과에 따른 응답화면 지정
		if(result>0) {
			//성공
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 수정되었습니다");
			response.sendRedirect(request.getContextPath()+"/detail.no?nno="+noticeNo);
		}else {
			//실패
			request.setAttribute("errorMsg", "공지사항 수정 실패");
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
