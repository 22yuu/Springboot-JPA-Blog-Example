package com.example.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;

//html 파일으 리턴해주는게 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {
			
	@Autowired // DummyControllerTest가 메모리에 뜰 때 userRepository 같이 메모리에 뜨게 만들어줌, 의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "해당 id는 존재하지 않습니다.";
		}
		return "삭제되었습니다. id : " + id;
	}
	// save함수는 id를 전달하지 않으면 insert를 해주고,
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	// email, password 수정
	@Transactional //함수 종료시에 자동 commit
	@PutMapping("/dummy/user/{id}") //주소가 같아도 get, put 요청이 달라서 알아서 구분함.
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 요청 => 스프링이 Java Object(MessageConvert의 jackson 라이브러리가 변환해서 받아준다.)로 변환해서 받아줌
		System.out.println("id : " + id);
		System.out.println("Password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		//requestUser.setId(id);
		//userRepository.save(requestUser); //save를 통해서 업데이트를 할 경우 다른 칼럼들의 값들이 null로 들어온다... -> 지금 현재 상태의 경우 username이 nullable=false 이므로 not-null 오류 발생.
		
		//따라서 업데이트 시
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());

		//userRepository.save(user); // save는 권장되지 않음
		
		//더티체킹 @Transactional을 사용해서 db 업데이트
		
		return user;
	}
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll(); // 리스트 타입
	}
	
	
	//한페이지당 2건의 데이터를 리턴받아 볼 예정
	//http://localhost:8000/blog/dummy/user/page
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4을 찾으면 데이터베이스에서 못 찾아오게되면 user가 null이 됨
		// 그럼 return 시 null이 리턴 됨
		// 그래서 Optional로 User객체를 감싸서 가져옴 -> null인지 아닌지 판단해서 return
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다.");
		}); // return : Optional
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json(gson라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user; 
	}
	
	
	/* //람다식
	 * @GetMapping("/dummy/user/{id}") public User detail(@PathVariable int id) { 
	 * userRepository.findById(id).orElseThrow(()->{ 
	 * return new IllegalArgumentException("해당 사용자는 없습니다."); });
	 * return user;
	 * }
	 */
	
	// http://localhost:8000/blog/dummy/join
	// http의 body에 username, password, eamil 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value (약속된 규칙)
		System.out.println("id:"+user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
