package com.kh.notice.model.service;

//JDBCTemplate 클래스의 모든 메소드들을 그냥 가져다가 쓰겠다
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {
	//공지사항 전체 조회 서비스
	public ArrayList<Notice> selectNoticeList(){
		//1)Connection 객체 생성
		Connection conn=getConnection();
		
		//2)Connection 객체와 전달값을 DAO로 넘기기 및 결과 받기
		ArrayList<Notice> list=new NoticeDao().selectNoticeList(conn);
		
		//3)commit, rollback 처리(select문이라 생략)
		//4)Connection 객체 반납
		close(conn);
		
		
		//5)결과 반환
		return list;
	}
	//공지사항 작성 서비스
	public int insertNotice(Notice n) {
		//1)Connection 객체 생성
		Connection conn=getConnection();
		
		//2)Connection 객체와 전달값을 DAO로 넘기기 및 결과 받기
		int result=new NoticeDao().insertNotice(conn,n);
		
		//3)결과에 따라 commit, rollback 처리
		if(result>0) {
			//성공
			commit(conn);
		}else {
			//실패
			rollback(conn);
		}
		//4)Connection객체 반납
		close(conn);
		
		//5)결과 반환
		return result;
	}
	//공지사항 조회수 증가용 서비스
	public int increaseCount(int noticeNo) {
		Connection conn=getConnection();
		
		int result=new NoticeDao().increaseCount(conn, noticeNo);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	//공지사항 상세보기용 서비스
	public Notice selectNotice(int noticeNo) {
		Connection conn=getConnection();
		
		Notice n=new NoticeDao().selectNotice(conn, noticeNo);
		
		close(conn);
		
		return n;
	}
	//공지사항 수정용 서비스
	public int updateNotice(Notice n) {
		Connection conn=getConnection();
		int result=new NoticeDao().updateNotice(conn, n);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	//공지사항 삭제용 서비스
	public int deleteNotice(int noticeNo) {
		Connection conn=getConnection();
		
		int result=new NoticeDao().deleteNotice(conn, noticeNo);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
