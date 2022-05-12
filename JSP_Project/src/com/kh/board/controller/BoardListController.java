package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//-----페이징 처리-----//
		//기본적으로 구해야 하는 변수들
		int listCount; //현재 총 게시글 갯수
		int currentPage; //현재 보여질 페이지(즉, 사용자가 요청한 페이지)
		int pageLimit; //페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit; //한 페이지에 보여질 게시글의 최대 갯수(한 페이지당 게시글이 몇개 단위씩 보여질건지)
		
		//위의 변수들을 이용해서 계산해서 구해야 하는 변수들
		int maxPage; //가장 마지막 페이지가 몇번 페이지인지(총 페이지수)
		int startPage; //페이지 하단에 보여질 페이징바의 시작수
		int endPage; //페이지 하단에 보여질 페이징바의 끝수
		
		//*listCount: 현재 총 게시글 갯수(단, STATUS 값이 'Y'일 경우)
		listCount=new BoardService().selectListCount();
		//System.out.println(listCount);
		
		//*currentpage: 현재페이지(즉, 사용자가 요청한 페이지)
		currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		//*pageLimit: 페이지 하단에 보여질 페이징바의 페이지 최대 갯수(페이지목록들이 몇개 단위씩 보여질건지 지정)
		pageLimit=10;
		
		//*boardLimit: 한 페이지에 보여질 게시글의 최대갯수(게시글을 몇개 단위씩 보여질건지 지정)
		boardLimit=10;
		
		//*maxPage:가장 마지막 페이지가 몇번 페이지인지(총 페이지수)
		/*
		 * listCount, boardLimit 변수에 영향을 받는 변수
		 * 단, boardLimit가 10이라는 가정하에 규칙을 구해보자
		 * 
		 * 총 갯수	boardLimit	maxPage
		 * 100	/	10	=>10.0	10번 페이지
		 * 101	/	10	=>10.1	11번 페이지
		 * 105	/	10	=>10.5	11번 페이지
		 * 110	/	10	=>11.0	11번 페이지
		 * 111	/	10	=>11.1	12번 페이지
		 * 
		 * =>실수로 형변환 후 나눗셈 처리한 결과를 올림처리 ==maxPage의 값이 나온다
		 * 1)listCount를 double로 형변환
		 * 2)listCount/boardLimit
		 * 3)결과에 올림처리=>Math.ceil(결과값)
		 * 4)결과값을 다시 int로 형변환
		 */
		maxPage=(int)(Math.ceil((double)listCount/boardLimit));
		
		//*startPage: 페이지 하단에 보여질 페이징바의 시작수
		/*
		 * pageLimit, currentPage에 영향을 받는 변수
		 * - 공식 구하기
		 * 단, pageLimit가 10이라는 가정하에 규칙을 구해보자
		 * 
		 * 만약 pageLimit가 10일 경우
		 * startPage:1,11,21,31,41,... => n*10+1 =>10의배수 +1
		 * 
		 * 만약 pageLimit가 5일 경우
		 * startPage:1,6,11,16,21,... =>n*5+1 =>5의 배수+1
		 * 
		 * 즉, startPage=n*pageLimit+1
		 * 
		 * currentPage	startPage	n*pageLimit+1
		 * 		1			1	=>	n*10+1	=>	n=0
		 * 		5			1	=>	n*10+1	=>	n=0
		 * 		10			1	=>	n*10=1	=>	n=0
		 * 		11			11	=>	n*10+1	=>	n=1
		 * 		15			11	=>	n*10+1	=>	n=1
		 * 		20			11	=>	n*10=1	=>	n=1
		 * 
		 * currentPage가
		 * 1~10인 경우: n=0
		 * 11~20인 경우: n=1
		 * 21~30인 경우: n=2
		 * =>currentPage/pageLimit =>1~9까지는 몫이 0, 10은 몫이 1
		 * 						   =>11~19까지는 몫이1, 20은 몫이 2
		 * 							=>21~29까지는 몫이2, 30은 몫이3
		 * => n=(currentPage-1)/pageLimit
		 * 
		 * 총 공식
		 * startPage=n*pageLimit+1=(currentPage-1)/pageLimit*pageLimit+1
		 */
		startPage=(currentPage-1)/pageLimit*pageLimit+1;
		
		//*endPage: 페이지 하단에 보여질 페이징바의 끝수
		/*
		 * startPage, pageLimit에 영향을 받는 변수
		 * -공식 구하기
		 * 단, pageLimit가 10이라는 가정 하에 구해보자
		 * startPage:1=>endPage:10
		 * startPage:11=>endPage:20
		 * 
		 * endPage=startPage+pageLimit-1
		 */
		endPage=startPage+pageLimit-1;
		
		//startPage가 11이여서 endPage를 20으로 계산함
		//근데 maxPage가 13페이지까지밖에 안된다면?
		//=>이런 경우에는 endPage==maxPage
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		//페이지 정보들을 하나의 공간에 담아서 보내자
		//페이지 정보들을 담을 VO 클래스를 하나 더 만들것(PageInfo)
		//=>일반게시판 뿐만 아니라 공지사항 게시판이나 사진게시판에서도 쓸 수 있게끔 common 패키지에 만들것임
		
		//1.페이징바 만들때 필요한 객체 셋팅
		PageInfo pi=new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
		
		//2.현재 사용자가 요청한 페이지(currentPage)에 보여질 게시글 리스트 요청하기
		ArrayList<Board> list=new BoardService().selectList(pi);
		//System.out.println(list);
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
