package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

//인터페이스를 구현해야함=> implements 키워드 이용
public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		//전달받은 원본파일로부터 파일명 부분만 추출
		String originName=originFile.getName(); //"aaa.jpg"
		
		//수정파일명: 파일업로드된시간(년월일시분초)+5자리 랜덤값(10000~99999)
		//				=>최대한 파일명이 겹치지 않게
		//확장자: 원본파일의 확장자 그대로
		
		//원본명	-->	수정명
		//aaa.jpg	-->	2022051311143012345.jpg
		
		//1.파일이 업로드된 시간(년월일시분초)
		String currentTime=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		//2.5자리 랜덤값(10000~99999)
		int ranNum=(int)(Math.random()*90000)+10000;
		
		//3.원본파일명으로부터 확장자 부분만 추출
		//aaa.jpg=>.jpg
		//aaa.bbb.jpg=>.jpg
		//파일명 중간에 .이 들어가는 경우도 있기 때문에
		//원본파일명으로부터 가장 마지막에 나오는 .의 인덱스를 기준으로 파일명과 확장자를 나눈다
		//문자열의 앞에서부터 인덱스값을 세서 알려주는 메소드:indexOf()
		//문자열의 뒤에서부터 인덱스값을 세서 알려주는 메소드:lastIndexOf()
		String ext=originName.substring(originName.lastIndexOf("."));
		
		//4.수정파일명 합체
		String changeName=currentTime+ranNum+ext;
		
		//원본파일을 수정된 파일명으로 적용시켜서 파일객체로 변환
		return new File(originFile.getParent(), changeName);
	}
	
}
