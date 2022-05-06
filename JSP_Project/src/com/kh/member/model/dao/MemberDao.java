package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	private Properties prop=new Properties();
	public MemberDao() {
		//공통적인 코드를 빼둘곳
		//동적 코딩 방식
		String fileName=MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//로그인 관련 쿼리문을 실행시킬 DAO 메소드
	public Member loginMember(Connection conn, String userId, String userPwd){
		//SELECT문 => ResultSet 객체 (unique 제약조건에 의해 한 행만 조회됨)
		//=>Member 객체
		
		//1) 필요한 변수들 셋팅
		Member m=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;

		/*
		SELECT * 
		FROM MEMBER
		WHERE USER_ID =? AND USER_PWD =? AND STATUS ='Y'
		*/

		
		String sql=prop.getProperty("loginMember");

		try {
			//2) Connection 객체를 이용해서 PreparedStatement 객체를 생성
			pstmt=conn.prepareStatement(sql);
			
			//3) 쿼리문이 미완성된 경우 완성시키기(완성된 경우는 이 단계 생략)
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			//4,5) 쿼리문 실행 및 결과 받기
			rset=pstmt.executeQuery();

			//6_1) ResultSet 으로부터 결과값을 뽑아서 객체로 가공
			if(rset.next()){
				//만약에 조회가 잘 되었다면==로그인 할 수 있는 회원의 정보가 있다면
				m=new Member(
					rset.getInt("USER_NO"),
					rset.getString("USER_ID"),
					rset.getString("USER_PWD"),
					rset.getString("USER_NAME"),
					rset.getString("PHONE"),
					rset.getString("EMAIL"),
					rset.getString("ADDRESS"),
					rset.getString("INTEREST"),
					rset.getDate("ENROLL_DATE"),
					rset.getDate("MODIFY_DATE"),
					rset.getString("STATUS")
					);
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//7) 자원 반납(생성된 순서의 역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		//8) 결과 반환
		return m;
	}
	
	//회원가입용 쿼리문을 실행시켜주는 DAO 메소드
	public int insertMember(Connection conn, Member m) {
		//INSERT 문 =>int형 반환(처리된 행의 갯수)
		
		//1)필요한 변수들 셋팅
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("insertMember");
		
		try {
			//2)Connection 객체를 이용하여 PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//3)미완성된 쿼리문의 경우 구멍 메꿔주기
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());
			
			//4, 5)쿼리문 실행 및 결과 받기
			result=pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//6)자원 반납(생성된 순서의 역순)
			JDBCTemplate.close(pstmt);
		}
		//7)결과 반환
		return result;
	}
	//회원 정보 수정 쿼리문 실행용 DAO 메소드
	public int updateMember(Connection conn, Member m) {
		//UPDATE 문=>int형 반환(처리된 행의 갯수)
		
		//1)필요한 변수들 셋팅
		int result=0;
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("updateMember");
		
		try {
			//2)Connection 객체를 이용해서 PreparedSatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//3)미완성된 쿼리문 채우기
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			//4,5) 쿼리문 실행 및 결과 받기
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//6)자원 반납(생성된 순서의 역순)
			JDBCTemplate.close(pstmt);
		}
		//7)결과 반환
		return result;
	}
	//회원 한명 정보 조회용 DAO 메소드
	public Member selectMember(Connection conn, String userId) {
		//SELECT 문=> ResultSet 객체 반환(unique 제약조건에 의해 한 행만 조회)=>Member 객체
		
		//1)필요한 변수들 셋팅
		Member m=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String sql=prop.getProperty("selectMember");
		
		try {
			//2)Connection 객체를 이용하여 PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//3)미완성된 쿼리문 채우기
			pstmt.setString(1, userId);
			
			//4,5)쿼리문 실행 및 결과값 받기
			rset=pstmt.executeQuery();
			
			//6)결과를 VO로 가공
			if(rset.next()) {//결과는 최대 1행이기 때문에 if문 
				m=new Member(
						rset.getInt("USER_NO"), 
						rset.getString("USER_ID"),
						rset.getString("USER_PWD"),
						rset.getString("USER_NAME"),
						rset.getString("PHONE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("INTEREST"),
						rset.getDate("ENROLL_DATE"),
						rset.getDate("MODIFY_DATE"),
						rset.getString("STATUS")
						);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//7)자원반납(생성된 순서의 역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		//8)결과 반환
		return m;
	}
}
