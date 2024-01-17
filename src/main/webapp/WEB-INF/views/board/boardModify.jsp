<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 내용 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<div class="page-main">
	<h2>글 수정</h2>
	<form:form action="update" modelAttribute="boardVO" id="update_form" enctype="multipart/form-data">
		<form:hidden path="board_num"/>								<%-- form:hidden 없으면 수정 불가 --%>
		<form:errors element="div" cssClass="error-color" />		<%-- 오류가 날 수 있기때문에 맨 위쪽에 표시 --%>
		<ul>
			<li>
				<form:label path="title">제목</form:label>
				<form:input path="title"/>
				<form:errors path="title" cssClass="error-color" />
			</li>
			<li>
				<form:label path="content">내용</form:label>
				<form:textarea path="content"/>
				<form:errors path="content" cssClass="error-color" />
			</li>
			<li>
				<form:label path="upload">파일업로드</form:label>
				<input type="file" name="upload" id="upload">
				<c:if test="${!empty boardVO.filename}">
				<div id="file_detail">(${boardVO.filename})파일이 등록되어 있습니다.
					<input type="button" value="파일삭제" id="file_del">
				</div>
				<script type="text/javascript">
					$(function(){									<%-- 위에있는 id="file_del" 을 클릭시 --%>
						$('#file_del').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									url:'deleteFile',
									data:{board_num:${boardVO.board_num}},
									type:'post',
									dataType:'json',
									success:function(param){
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요');
										}else if(param.result =='success'){
											$('#file_detail').hide();
										}else{
											alert('파일 삭제 오류 발생');
										}
									},
									error:function(){
										alert('네트워크 오류 발생');
									}
								});
							}
						});
					});
				</script>
				</c:if>
			</li>
		</ul>
		<div class="align-center">
			<form:button>수정</form:button>
			<input type="button" value="글 상세" onclick="location.href='detail?board_num=${boardVO.board_num}'">
		</div>
	</form:form>
</div>
<!-- 내용 끝 -->
