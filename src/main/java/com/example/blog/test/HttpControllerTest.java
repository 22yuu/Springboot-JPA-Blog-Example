package com.example.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest:";

	//localhost:8000/blog/http/lombok
	@GetMapping("http/lombok")
	public String lombokTest() {
		//Member m = new Member(1, "yuu", "1234", "aa@aa.aa");
		// 빌더를 사용하면 생성자 파라미터 순서를 안지켜도 상관없는 장점도 있다
		Member m = Member.builder().username("yuu").password("1234").email("aa@aa.aa").build(); 
		System.out.println(TAG + "getter : " + m.getUsername());
		m.setUsername("yuu2");
		System.out.println(TAG + "setter : " + m.getUsername());
		return "lombok test 완료";
	}
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8080/http/get (select)
	
	/* 하나씩 호출
	 * @GetMapping("/http/get") public String getTest(@RequestParam int
	 * id, @RequestParam String username) { return "get 요청 : "+id+", "+username; }
	 */
	
	// 한꺼번에 호출해서 사용하는 방법
	@GetMapping("/http/get")
	public String getTest(Member m) {//get?id=1&username=yuu&password=1234&email=aa@aa.aa
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		//@Data //Getters and Setters
		System.out.println(TAG + "setter : ");
		Member m1 = new Member(1, "yuu", "1234", "aa@aa.aa"); // @AllArgsConstructor //생성자
		Member m2 = new Member(); //@NoArgsConstructor //빈생성자 
		return "get 요청 : "+ m.getId() + ", " + m.getUsername() + ", " + m.getPassword()+", "+m.getEmail();
	}
	
	//http://localhost:8080/http/post (insert) 데이터를 추가
	/*
	 * @PostMapping("/http/post") public String postTest(Member m) { 
	 * return "post 요청 : "+ m.getId() + ", " + m.getUsername() + ", " +
	 * m.getPassword()+", "+m.getEmail(); }
	 */
	
	@PostMapping("/http/post") // text/plain, application/json
	public String postTest(@RequestBody Member m){//@RequestBody String text) { //MessageConverter (스프링부트)
		//return "post 요청 : " + text;
		return "post 요청 : "+ m.getId() + ", " + m.getUsername() + ", " + m.getPassword()+", "+m.getEmail();
	}
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+ m.getId() + ", " + m.getUsername() + ", " + m.getPassword()+", "+m.getEmail();
	}
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
