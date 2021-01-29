package com.example.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스를 통해서 자동으로 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // null인 필드를 제외해서 insert한다.
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto-increment, 비워놔도 자동으로 입력이 됨
	
	@Column(nullable=false, length=30)
	private String username; // 아이디
	
	@Column(nullable=false, length=100)// 12356 => 해쉬 (비밀번호 암호화) 때문에 넉넉하게 100
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;

	//private String role; // Enum을 쓰는게 좋다. 왜냐하면 Enum을 쓰면 어떤 데이터의 도메인을 만들어 줄 수 있음.
	//어떤 회원이 회원가입했을때, 이 회원의 Role은 Admin이다, 또는 일반 유저, 혹은 매니저 등등...
	//프로그래밍에서 도메인이라는 것은 어떤 범위를 뜻한다. 카테고리 범주
	
	// enum 사용
	//@ColumnDefault("'user")
	@Enumerated(EnumType.STRING) // DB에는 RoleType이 없으므로 해당 어노테이션을 사용해서 알려준다.
	private RoleType role; 
	
	
	@CreationTimestamp // 시간이 자동 입력, 비워놔도 자동으로 입력이 됨 
	private Timestamp createDate; //회원이 가입한 시간
}
