package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;
import com.kh.member.model.vo.Reply;

public class BoardService {
	public int selectListCount() {
		Connection conn=getConnection();
		int listCount=new BoardDao().selectListCount(conn);
		close(conn);
		return listCount;
	}
	
	public ArrayList<Board> selectList(PageInfo pi){
		Connection conn=getConnection();
		ArrayList<Board> list=new BoardDao().selectList(conn,pi);
		close(conn);
		return list;
	}
	
	public ArrayList<Category> selectCategoryList(){
		Connection conn=getConnection();
		ArrayList<Category> list=new BoardDao().selectCategoryList(conn);
		close(conn);
		return list;
	}
	
	public int insertBoard(Board b, Attachment at) {
		//일반게시글 추가 트랜잭션
		//트랜잭션 하나에 두개의 insert문을 넣어줄 예정
		//1.BOARD 테이블에 INSERT
		//2.ATTACHMENT 테이블에 INSERT(첨부파일이 있다면)
		//3.커밋/롤백 처리(위 두개의 쿼리문이 둘다 성공했을경우만 커밋)
		Connection conn=getConnection();
		
		//1.BOARD 테이블에 INSERT 시켜주는 DAO 호출
		int result1=new BoardDao().insertBoard(conn,b);
		int result2=1; //미리 result2에 대해서 선언과 초기화
		//2.만약 첨부파일이 있는 경우 ATTACHMENT 테이블에 INSERT 시켜주는 DAO호출
		if(at!=null) {
			result2=new BoardDao().insertAttachment(conn,at);
		}
		
		//이 시점 기준으로 첨부파일이 없었지만 result1은 성공일 경우
		//result2를 0으로 초기화했었다면 else 블럭으로 빠져나갈것(롤백처리)
		//=>result2를 1로 초기화하기!!
		
		//3.커밋/롤백 처리(위 두개의 쿼리문이 둘다 성공했을경우만 커밋)
		if(result1>0&&result2>0) {
			//성공=>커밋
			commit(conn);
		}else {
			//실패=>롤백
			rollback(conn);
		}
		//4.자원반납
		close(conn);
		
		//5.결과반환
		return result1*result2;
	}
	
	public int increaseCount(int boardNo) {
		Connection conn=getConnection();
		int result=new BoardDao().increaseCount(conn, boardNo);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public Board selectBoard(int boardNo) {
		Connection conn=getConnection();
		Board b=new BoardDao().selectBoard(conn,boardNo);
		
		close(conn);
		return b;
	}
	
	public Attachment selectAttachment(int boardNo) {
		Connection conn=getConnection();
		Attachment at=new BoardDao().selectAttachment(conn, boardNo);
		close(conn);
		return at;
	}
	
	public int updateBoard(Board b, Attachment at) {
		Connection conn=getConnection();
		
		//우선적으로 공통적으로 실행해야하는 BOARD 테이블 UPDATE 구문
		int result1=new BoardDao().updateBoard(conn,b);
		int result2=1;
		//CASE0. BOARD 테이블만 UPDATE하고 마는 경우를 생각해서 result2는 1로 초기화
		
		//새롭게 첨부파일이 있을 경우
		if(at!=null) {
			if(at.getFileNo()!=0) {
				//CASE1. 기존에 첨부파일이 있었는데 새로운 첨부파일이 업로드 된 경우
				//=>Attachment 테이블 Update
				result2=new BoardDao().updateAttachment(conn, at);
			}else {
				//CASE2. 기존에 첨부파일이 없었는데 새로운 첨부파일이 업로드 된 경우
				//=>Attachment 테이블 Insert
				result2=new BoardDao().insertNewAttachment(conn, at);
			}
		}
		//트랜잭션 처리
		if(result1>0&&result2>0) {
			//성공
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result1*result2;
	}
	
	public int deleteBoard(int boardNo) {
		Connection conn=getConnection();
		int result=new BoardDao().deleteBoard(conn,boardNo);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {
		Connection conn=getConnection();
		
		//Board 테이블에 Insert 해주는 메소드
		int result1=new BoardDao().insertThumbnailBoard(conn, b);
		
		//Attachment 테이블에 Insert 해주는 메소드
		int result2=new BoardDao().insertAttachmentList(conn, list);
		
		if(result1>0&&result2>0) {
			//성공
			commit(conn);
		}else {
			//실패
			rollback(conn);
		}
		close(conn);
		return result1*result2;
	}
	
	public ArrayList<Board> selectThumbnailList(){
		Connection conn=getConnection();
		
		ArrayList<Board> list=new BoardDao().selectThumbnailList(conn);
		
		close(conn);
		return list;
	}
	
	public ArrayList<Attachment> selectAttachmentList(int boardNo){
		Connection conn=getConnection();
		ArrayList<Attachment> list=new BoardDao().selectAttachmentList(conn, boardNo);
		close(conn);
		return list;
	}
	
	public ArrayList<Reply> selectReplyList(int boardNo){
		Connection conn=getConnection();
		ArrayList<Reply> list=new BoardDao().selectReplyList(conn, boardNo);
		close(conn);
		return list;
	}
	
	public int insertReply(Reply r) {
		Connection conn=getConnection();
		int result=new BoardDao().insertReply(conn, r);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
