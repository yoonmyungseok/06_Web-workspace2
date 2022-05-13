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
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 설정(POST 방식)
		request.setCharacterEncoding("utf-8");
		
		//2)요청시 전달값 뽑아서 변수에 기록 및 VO객체로 가공
		//String userNo=request.getParameter("userNo");
		//String category=request.getParameter("category");
		//String title=request.getParameter("title");
		//String content=request.getParameter("content");
		
		//System.out.println(userNo);
		//System.out.println(category);
		//System.out.println(title);
		//System.out.println(content);
		//폼 전송을 일반 방식이 아닌 multipart/form-data로 전송하는 경우
		//request로부터 곧바로 값을 뽑아내는것이 불가함
		//=>multipart라는 객체에 값을 이관시켜서 뽑아야한다
		
		//enctype이 multipart/form-data로 잘 전송되었을경우 먼저 검사
		if(ServletFileUpload.isMultipartContent(request)) {
			//System.out.println("잘 실행되나?");
			
			//1.전송되는 파일을 처리할 작업 내용 지정
			//(전송되는 파일의 용량 제한, 전달된 파일을 저장할 서버컴의 폴더 경로)
			
			//1_1. 전송파일의 용량 제한
			//(byte 단위의 값을 기술해야 한다)
			//=>10mbyte로 제한
			/*
			 * 단위 정리
			 * 8bit==1byte
			 * byte->kbyte->mbyte->gbyte->tbyte
			 * 1kbyte==1024byte(2의 10제곱)
			 * 1mbyte==1024kbyte(2의 10제곱)
			 * 10mbyte==1024kbyte*10==10*1024*1024byte
			 */
			int maxSize=10*1024*1024;
			
			//1_2.전달된 파일을 저장할 서버의 실제 경로를 알아내기
			//세션 객체에서 제공하는 getServletContext 메소드를 통해 application 내장객체 생성
			//application 내장객체에서 제공하는 getRealPath를 이용해서 경로 알아내기
			//=>이 경우 폴더의 시작점(/)은 WebContent로 잡힌다
			//=>또한 해당 경로 안에 파일들이 저장될것이기 때문에 끝에 /를 추가로 더 붙여줌
			
			String savePath=request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
			
			//System.out.println(maxSize);
			//System.out.println(savePath);
			
			//2.전달된 파일명을 수정 및 서버에 업로드 작업
			//HttpServletRequest request=>MultipartRequest multiRequest로 변환
			//[표현법]
			//MultipartRequest multiRequest=new MultipartRequest(request 객체, 저장할 폴더 경로, 용량제한, 인코딩값, 파일명을 수정시켜주는 객체);
			//=>위 구문 한줄만으로 request 객체가 MultipartRequest 객체로 변환되고, 파일도 업로드 됨
			
			//단, 이 기능을 활용하고자 한다면 외부 라이브러리를 끌어다 써야함
			//=>cos.jar(com.oreilly.servlet 의 약자)
			/*
			 * 기본적으로 파일명 수정 작업을 해주는 객체: DefaultFileRenamePolicy 객체 (cos.jar에서 제공)
			 * -내부적으로 rename() 메소드를 실행시키면서 파일명 수정이 진행됨
			 * -기본적으로 동일한 파일명이 존재할 경우 뒤에 카운팅된 숫자를 붙여서 파일명 수정을 진행함
			 * 예)aaa.jpg, aaa1.jpg, aaa2.jpg, ...
			 * 
			 * 하지만 우리 입맛대로 파일명이 절대 안겹치게끔, 어느정도 의미부여가 되게끔 rename 해볼것임
			 * 즉, DefaultFileRenamePolicy 객체 사용X
			 * 나만의 com.kh.common.MyFileRenamePolicy 라는 클래스를 만들어서 거기서 rename 메소드를 재정의
			 */
			MultipartRequest multiRequest=new MultipartRequest(request, savePath,maxSize,"utf-8",new MyFileRenamePolicy());
			
			//3.DB에 기록할 데이터들을 뽑아서 VO 객체로 가공(Board, Attacthment)
			//요청 시 전달값들: userNo, category, title, content, upfile
			String boardWriter=multiRequest.getParameter("userNo");
			String category=multiRequest.getParameter("category");
			String boardTitle=multiRequest.getParameter("title");
			String boardContent=multiRequest.getParameter("content");
			
			Board b=new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at=null;
			//첨부파일이 있는지 없는지 검사
			//multiRequest.getOriginalFileName("키")=>원본파일명 return
			if(multiRequest.getOriginalFileName("upfile")!=null) {
				//첨부파일이 있다면
				//Attachment 객체 가공
				at=new Attachment();
				
				//원본명, 수정명, 저장될 파일의경로
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); //원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); //수정명
				at.setFilePath("resources/board_upfiles/");
				
			}
			//이 시점 기준으로 첨부파일이 있을경우 at가 null이 아님
			//이 시점 기준으로 첨푸파일이 없을경우 at가 null임
			//=>하지만 첨부파일이 있던 없던간에 Board는 insert 꼭해줘야함
			//=>그 다음에 첨부파일이 있다면 Attachment를 insert 해줘야함(외래키 제약조건에 의해서)
			
			//4.Service로 요청 후 결과 받기
			int result=new BoardService().insertBoard(b,at);
			
			//5.결과에 따른 응답화면 지정
			if(result>0) {
				//성공=>list.bo?currentPage=1 요청(가장 최신글이므로)
				request.getSession().setAttribute("alertMsg", "게시글 작성 성공");
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
			}else {
				//실패=>에러페이지 포워딩
				//한 가지 고려해야할 사항: 첨부파일이 있었을 경우 이미 업로드된 첨부파일을 굳이 서버에 보관할 이유가 없다(용량만 차지)
				if(at!=null) {
					//첨부파일이 있었을 경우
					//삭제시키고자하는 파일 객체 생성=>delete 메소드 (해당 파일을 삭제시켜주는 역할)
					new File(savePath+at.getChangeName()).delete();
				}
				request.setAttribute("errorMsg", "게시글 작성 실패");
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
