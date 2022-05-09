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
}
