package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListController
 */
@WebServlet("/list.no")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//우리가 원하는거: 응답페이지에 DB로부터 조회한 내용을 찍어내는 것
		//요청 시 전달값이 없기 때문에 1,2번 단계는 건너뜀=>href 속성값에 쿼리스트링이 없음
		//3)NoticeService 클래스의 어떤 메소드를 호출해서 결과값을 받기
		ArrayList<Notice> list=new NoticeService().selectNoticeList();
		
		//4)결과값에 따라 응답화면 지정
		//System.out.println(list);
		request.setAttribute("list", list);
		
		//응답페이지: 공지사항 리스트 페이지(noticeListView.jsp)
		request.getRequestDispatcher("views/notice/noticeListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
