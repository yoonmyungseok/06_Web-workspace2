package com.kh.notice.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.notice.model.vo.Notice;

public class NoticeDao {
	//전역변수로 Properties 타입의 객체 하나 만들어두기
	private Properties prop=new Properties();
	
	//공통적인 코드를 기본생성자에 빼기
	public NoticeDao() {
		//나중에 배포될 classes 폴더 기준으로 xml 파일의 경로 잡기
		String fileName=NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//공지사항 전체 조회 DAO
	public ArrayList<Notice> selectNoticeList(Connection conn){
		//SELECT 문=>ResultSet 객체로 반환(여러 행 조회)=>ArrayList
		
		//1)필요한 변수들 셋팅
		ArrayList<Notice> list=new ArrayList<>();
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String sql=prop.getProperty("selectNoticeList");
		
		
		try {
			//2)PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//3)미완성된 쿼리문 완성 
			//전달값도 없고, 위치홀더도 없기 때문에 생략
			
			//4,5)쿼리문 실행 및 결과값 받기
			rset=pstmt.executeQuery();
			
			//6)VO객체 또는 ArrayList로 가공
			while(rset.next()) {
				list.add(new Notice(rset.getInt("NOTICE_NO"),rset.getString("NOTICE_TITLE"),rset.getString("USER_ID"),rset.getInt("COUNT"),rset.getDate("CREATE_DATE")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//7)자원반납(생성된 역순)
			close(rset);
			close(pstmt);
		}
		//8)결과 반환
		return list;
	}
	
	//공지사항 작성 DAO
	public int insertNotice(Connection conn, Notice n) {
		//INSERT문=>int (처리된 행의 갯수)
		//1) 필요한 변수 셋팅
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("insertNotice");
		
		
		try {
			//2)PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//3)미완성된 쿼리문 채우기
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
			
			//4,5)쿼리문 실행 및 결과 받기
			result=pstmt.executeUpdate();
			
			//
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//6)자원 반납
			close(pstmt);
		}
		//7)결과 반환
		return result;
	}
	
	//공지사항 조회수 증가용 DAO
	public int increaseCount(Connection conn, int noticeNo) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("increaseCount");
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, noticeNo);
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	//공지사항 상세보기용 DAO
	public Notice selectNotice(Connection conn, int noticeNo) {
		Notice n=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String sql=prop.getProperty("selectNotice");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				n=new Notice(rset.getInt("NOTICE_NO"),rset.getString("NOTICE_TITLE"),rset.getString("NOTICE_CONTENT"),rset.getString("USER_ID"),rset.getDate("CREATE_DATE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return n;
	}
	
	//공지사항 수정용 DAO
	public int updateNotice(Connection conn, Notice n) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("updateNotice");
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	//공지사항 삭제용 DAO
	public int deleteNotice(Connection conn, int noticeNo) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("deleteNotice");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
