package com.example.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;

/* 서비스가 필요한 이유:
 * 1. 트랜잭션 관리하기 위해서
 * 2. 서비스 의미 
 * */

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // 전체의 서비스가 하나의 트랜잭션으로 묶임 -> 성공하면 commit이 되고 실패하면 rollback이 됨.(rollback에 관한 로직은 짜야됨.)
	public int 회원가입(User user) {
		try {
			String rawPassword = user.getPassword(); // 1234 원문 유저가 입력한 패스워드
			String encPassword = encoder.encode(rawPassword); // 암호 해쉬화
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("UserService:회원가입():"+e.getMessage());
		}
		return -1;
	}

//	@Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 -> 정합성 유지
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
