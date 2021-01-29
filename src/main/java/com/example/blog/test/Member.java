package com.example.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@RequiredArgsConstructor // final이 붙은 애들에 대한 생성자를 생성
@Data //Getters and Setters
//@AllArgsConstructor //생성자
@NoArgsConstructor //빈생성자
public class Member { // final을 붙이는 이유 : 불변성
	private int id;
	private String username;
	private String password; // 패스워드는 변경을 하기 때문에 final 쓰면 안됨
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) { // 생성자 선언 @AllArgsConstructor랑 똑같음
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	/*
	 * public Member(String username, String password, String email) { // 생성자
	 * this.username = username;
	 * this.password = password;
	 *  this.email = email;
	 *  }
	 *  
	 *  만약 id를 제외하고 다른 값을 넣고 싶으면 위의 주석처리가 된 것처럼 id 선언부를 뺀 생성자를 또 만들어줘서 오버로딩을 해야한다.
	 *  하지만 lombok 라이브러리의 @Builder 어노테이션을 사용하면
	 *  HttpControllerTest.java 파일에서 lombokTest() 메소드 부분에 선언된 것처럼
	 *  Member m = Member.builder().username("yuu").password("1234").email("aa@aa.aa").build();
	 *  따로 생성자를 오버로딩하지 않고, 원하는 값만 넣을 수 있다.
	 */
	
}
