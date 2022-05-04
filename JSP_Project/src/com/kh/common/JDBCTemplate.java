package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
    //1.Connection 객체 생성(DB 접속)한 후 해당 Connection 객체를 반환하는 메소드
	public static Connection getConnection(){
		//동적 코딩 방식으로 설정값 가져오기
		Properties prop=new Properties();
		//읽어들이고자하는 driver.properties 파일의 경로 알아오기
        //배포되는 시점에서는 src라는 폴더는 WebContent 폴더 내에 없기때문에
        //src를 시작점으로 잡으면 오류 발생=>classes 폴더를 시작점으로 잡는다
        String fileName=JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
        try {
			prop.load(new FileInputStream(fileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn=null;
        	//1)JDBC Driver등록
		try {
			Class.forName(prop.getProperty("driver"));
			//2)DB와의 접속 정보를 갖고 있는 Connection 객체 생성
			conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
    }
    //2.전달받은 Connection 객체를 가지고 commit 해주는 메소드
    public static void commit(Connection conn){
        try {
			if(conn!=null&&conn.isClosed()){
			    conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //3.전달받은 Connection 객체를 가지고 rollback 해주는 메소드
    public static void rollback(Connection conn){
        try {
			if(conn!=null&&conn.isClosed()){
			    conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //4.전달받은 Connection 객체를 반납해주는 메소드
    public static void close(Connection conn){
        try {
			if(conn!=null&&conn.isClosed()){
			    conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //5.전달받은 Statement 객체를 반납해주는 메소드
    //=>다형성에 의해 자식클래스인 PreparedStatement 도 반납 가능
    public static void close(Statement stmt){
        try {
			if(stmt!=null&&stmt.isClosed()){
			    stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //6.전달받은 ResultSet 객체를 반납시켜주는 메소드
    public static void close(ResultSet rset){
        try {
			if(rset!=null&rset.isClosed()){
			    rset.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
