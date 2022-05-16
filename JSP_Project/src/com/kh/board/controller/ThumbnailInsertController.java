package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

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
