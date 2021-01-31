package com.example.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.ResponseDto;
import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userSerivce;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UserApiController:save 호출됨.");
		// 실제로 DB에 INSERT를 하고 아래에서 return이 되면 된다.
		user.setRole(RoleType.USER);
		int result = userSerivce.회원가입(user); // 1 : 성공, -1 : 실패
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 자바오브젝트를 json으로 변환해서 리턴(Jackson)
	}
}
