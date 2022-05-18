package com.kh.model.vo;

public class Member {
	private int memberNo;
	private String memberName;
	private int age;
	private String gender;
	public Member() {
		super();
	}
	public Member(int memberNo, String memberName, int age, String gender) {
		super();
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.age = age;
		this.gender = gender;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberName=" + memberName + ", age=" + age + ", gender=" + gender
				+ "]";
	}
	
	
}
