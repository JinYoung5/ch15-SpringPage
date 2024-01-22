$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	
	/*-----------------------------
	 *댓글 목록
	 *-----------------------------*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listReply',
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount,board_num:$('#board_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				//댓글수 읽어오기
				displayReplyCount(param);
				
				//댓글 목록 작업
				$(param.list).each(function(index,item){
					let output = '<div class = "item">';
					output += '<ul class="detail-info">';
					output += '<li>';
					output += '<img src="../member/viewProfile?mem_num='+item.mem_num+'" width="40" height="40" class="my-photo">';
					output += '</li>';
					output += '<li>';
					
					if(item.nick_name){
						output += item.nick_name + '<br>';
					}else{
						output += item.id + '<br>';
					}
					if(item.re_mdate){
						output += '<span class="modify-date">최근 수정일 : ' + item.re_mdate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					
					output += '</li>';
					output += '</ul>';
					output += '<div class="sub-item">';
					output += '<p>' + item.re_content.replace(/\r\n/g, '<br>') + '</p>';
					
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 댓글 작성자 회원번호가 같으면
						output += ' <input type="button" data-num="' +item.re_num+ '" value="수정" class="modify-btn">';
						output += ' <input type="button" data-num="' +item.re_num+ '" value="삭제" class="delete-btn">';
					}
					
					output += '<br size="1" noshade>';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				/*페이지 더보기가 안떠서 네트워크 콘솔에 오류 표시하는 문장 입력
				(count가 7개로 인식되어있어서 BoardAjaxcontroller에 댓글등록 페이지 처리에서 전체 레코드수를 int count = boardService.selectRowCount(map); 로 오타, CountReply 추가후 더보기 떠있음)
				 *console.log("currentPage:"+currentPage);
				 *console.log("count:"+count);
				 *console.log("rowCount:"+rowCount);
				*/
				
				//paging button 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
				
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	
	
	//다음 댓글 보기 버튼 클릭시 테이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	
	/*-----------------------------
	 *댓글 등록
	 *-----------------------------*/
	//댓글 등록
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#re_content').val('').focus();
			return false;
		}
		
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'writeReply',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글들을 다시 호출
					selectList(1);
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	
	/*-----------------------------
	 *댓글 수정
	 *-----------------------------*/
	//댓글 수정 버튼 클릭시 수정폼 노출
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	
	//댓글 수정폼 초기화
	
	//댓글 수정
	
	/*-----------------------------
	 *댓글 등록, 수정 공통
	 *-----------------------------*/
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구하기	this:textarea
		let inputLength = $(this).val().length;
		
		if(inputLength>300){	//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{	//300자 이하인 경우
			//남은 글자수 구하기
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id')=='re_content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			}else if($(this).attr('id')=='mre_content'){
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	
	/*-----------------------------
	 *댓글 삭제
	 *-----------------------------*/
	
	/*-----------------------------
	 *댓글수 표시
	 *-----------------------------*/
	function displayReplyCount(param){
		let count = param.count;
		let output;
		if(count > 0){
			output = '댓글수('+count+')';
		}else{
			output = '댓글수(0)';
		}
		//문서 객체의 추가
		$('#output_rcount').text(output);
	}


	/*-----------------------------
	 *초기 데이터(목록) 호출
	 *-----------------------------*/
	selectList(1);

});