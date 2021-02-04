package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC 관리)
@EnableWebSecurity  // security가 모든 리퀘스트 요청을 가로챈다. 이때 컨트롤러에 가서 함수가 실행되기 전에 얘가 동작해서 /auth/** 이하의 경로로 들어오는 것을 허락하는 필터링한다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
// 위의 3개의 어노테이션을 거의 한 세트이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean //IoC가 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); //Spring이 관리
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**","/css/**", "/image/**")
				.permitAll()
				.anyRequest()
				.authenticated() // /auth/** 이하의 경로는 모두 허가
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}
}
