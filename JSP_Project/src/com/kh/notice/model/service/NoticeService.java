package com.kh.notice.model.service;

//JDBCTemplate 클래스의 모든 메소드들을 그냥 가져다가 쓰겠다
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.getConnection;

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
}
