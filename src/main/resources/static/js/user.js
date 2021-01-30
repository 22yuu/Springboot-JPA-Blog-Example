let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	
	save:function(){
		//alert('user의 svae함수 호출됨.');
		let data = {
			// joinForm의 input에서 id값을 불러옴.
			usernae:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		
		//console.log(data);
		
		//ajax 요청
		$.ajax().done().fail(); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!! 35강
	}
}

// index는 그냥 오브젝트이기 때문에
index.init(); // init을 해줘야지 joinForm에서 script를 읽을 때 함수가 수행된다.