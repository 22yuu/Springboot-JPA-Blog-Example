let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{ // function(){}, ()=>{} this를 바인딩하기 위해서!
			this.save();
		});
	},
	
	save:function(){
		//alert('user의 svae함수 호출됨.');
		let data = {
			// joinForm의 input에서 id값을 불러옴.
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
		//console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data), // http body 데이터, JSON으로 변경해서 데이터를 날림
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType:"json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String, 생긴게 json이라면 => javascript 오브젝트로 변경해준다.)		
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
}

// index는 그냥 오브젝트이기 때문에
index.init(); // init을 해줘야지 joinForm에서 script를 읽을 때 함수가 수행된다.