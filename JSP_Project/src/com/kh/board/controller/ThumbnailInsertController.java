package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//POST 방식이므로 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		//multipart/form-data로 전송되었는지부터 검사
		if(ServletFileUpload.isMultipartContent(request)) {
			//1.전송된 첨부파일에 대한 처리할 내용 지정
			//1_1.최대 용량 지정(byte 단위)
			int maxSize=10*1024*1024;
			
			//1_2.저장경로 지정(resources 폴더의 thumbnail_upfiles 폴더)
			String savePath=request.getSession().getServletContext().getRealPath("/resources/thumbnail_upfiles/");
			
			//2.전달된 파일명을 수정 후 서버에 업로드
			MultipartRequest multiRequest=new MultipartRequest(request,savePath,maxSize,"utf-8", new MyFileRenamePolicy());
			
			//3.요청 시 전달값 뽑기 
			//Board 테이블에 insert할 데이터 먼저 뽑기
			Board b=new Board();
			b.setBoardWriter(multiRequest.getParameter("userNo"));
			b.setBoardTitle(multiRequest.getParameter("title"));
			b.setBoardContent(multiRequest.getParameter("content"));
			
			//Attachment 테이블에 insert할 데이터 뽑기
			//단, 여러개의 첨부파일 있을 예정이기 때문에
			//Attachment 들을 ArrayList에 담기
			//=>적어도 1개 이상은 담김(대표이미지에만 required 속성을 부여했기 때문)
			ArrayList<Attachment> list=new ArrayList<>();
			
			//넘어올 수 있는 file의 갯수는
			//최소 1개~최대 4개
			//각 file의 key 값은
			//file1~file4까지
			for(int i=1; i<=4; i++) {
				String key="file"+i;
				//file1, file2, file3, file4
				//원본명=>multiRequest.getOriginalFileName(key)
				//수정명=>mulitRequest.getFilesystemName(key)
				
				//첨부파일이 존재할 경우
				if(multiRequest.getOriginalFileName(key)!=null) {
					//Attachment 객체 생성
					//=>원본명, 수정명, 저장경로, 파일레벨 필드 가공
					Attachment at=new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("resources/thumbnail_upfiles/");
					
					if(i==1) {//대표이미지일 경우
						at.setFileLevel(1);
					}else {//상세이미지일 경우
						at.setFileLevel(2);
					}
					list.add(at);//첨부파일의 개수만큼 담김
				}
			}
			//이 시점까지 도달했다면 b와 list에 첨부파일 객체가 다 담겨있을 것임
			int result=new BoardService().insertThumbnailBoard(b, list);
			if(result>0) {
				//성공=>list.th 요청
				request.getSession().setAttribute("alertMsg", "성공적으로 업로드 되었습니다");
				response.sendRedirect(request.getContextPath()+"/list.th");
			}else {
				//실패=>에러페이지로 포워딩
				request.setAttribute("errorMsg", "사진게시판 업로드 실패");
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
