package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	//로그인 요청 서비스
	public Member loginMember(String userId, String userPwd) {
		//1)Connection 객체 생성
		Connection conn=JDBCTemplate.getConnection();
		
		//2)만들어진 Connection 객체와 전달받았던 값들을 DAO한테 넘기기
		Member m=new MemberDao().loginMember(conn,userId,userPwd);

		//3)INSERT, UPDATE, DELETE 구문의 경우 commit 또는 rollback
		//=>SELECT 문일 경우는 생략

		//4)Connection 객체 반납
		JDBCTemplate.close(conn);

		//5)Controller로 결과를 반환
		return m;
		
	}
}
