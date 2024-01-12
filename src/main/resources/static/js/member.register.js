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
			dataType:'json',
			success:function(param){
				if(param.result == 'idNotFound'){
					$('#message_id').css('color','#000').text('등록 가능 ID');
					checkId = 1;
				}else if(param.result == 'idDuplicated'){
					$('#message_id').css('color','red').text('중복된 ID');
					$('#id').val('').focus();
					checkId = 0;
				}else if(param.result == 'notMatchPattern'){
					$('#message_id').css('color','red').text('영문,숫자만 4~12자 입력');
					$('#id').val('').focus();
					checkId = 0;
				}else{
					checkId = 0;
					alert('ID 중복 체크 오류');
				}
			},
			error:function(){
				checkId=0;
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
	});	//end of click
	
	//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
					//후손 선택자 #id
	$('#member_register #id').keydown(function(){
		checkId = 0;
		$('#message_id').text('');
	});	//end of keydown
	
	//submit 이벤트 발생시 아이디 중복 체크 여부 확인
	$('#member_register').submit(function(){
		if(checkId==0){
			$('#message_id').css('color','red').text('아이디 중복 체크 필수');
			if($('#id').val().trim()==''){
				$('#id').val('').focus();	
			}
			return false;
		}
	});	//end of submit
	
	
});