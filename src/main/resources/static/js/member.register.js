$(function(){
	/*-------------------------
	 *			회원가입
	 *-------------------------*/
	//아이디 중복 여부 저장 변수 : 0은 아이디 중복 또는 중복 체크 미실행, 1은 아이디 미중복
	let checkId = 0;
	
	//아이디 중복 체크
	$('#confirmId').click(function(){
		if($('#id').val().trim()==''){
			$('#message_id').css('color','red').text('아이디를 입력하세요');
			$('#id').val('').focus();
			return;						//다른 코드를 싱행하지 않기위해 return
		}
		
		//서버와 통신
		$.ajax({
			url:'confirmId',
			type:'post',
			data:{id:$('#id').val()},
		});
	});
});