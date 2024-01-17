<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 내용 시작 -->
<div class="page-main">
	<h2>${board.title}</h2>
	<ul class="detail-info">
		<li>
			<img src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${board.mem_num}" width="40" height="40" class="my-photo">
		</li>
		<li>
			<c:if test="${empty board.nick_name}">${board.id}</c:if>
			<c:if test="${!empty board.nick_name}">${board.nick_name}</c:if>
			<br>
			<c:if test="${!empty board.modify_date}">
			최근 수정일 : ${board.modify_date}
			</c:if>
			<c:if test="${empty board.modify_date}">
			작성일 : ${board.reg_date}
			</c:if>
			조회 : ${board.hit}
		</li>
	</ul>
	<c:if test="${!empty board.filename}">
	<ul>
		<li>첨부파일 : <a href="file?board_num=${board.board_num}">${board.filename}</a></li>
	</ul>
	</c:if>
	<hr size="1" width="100%">
	<c:if test="${fn:endsWith(board.filename, '.jpg') ||
				fn:endsWith(board.filename, '.JPG') ||
				fn:endsWith(board.filename, '.jpeg') ||
				fn:endsWith(board.filename, '.JPEG') ||
				fn:endsWith(board.filename, '.gif') ||
				fn:endsWith(board.filename, '.GIF') ||
				fn:endsWith(board.filename, '.png') ||
				fn:endsWith(board.filename, '.PNG')}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img">
	</div>
	</c:if>
	<div class="detail-content">
		${board.content}
	</div>
	<div>
		<%-- 좋아요 --%>
		<%-- 댓글수 --%>
	</div>
	<hr size="1" width="100%">
	<div class="align-right">
		<c:if test="${!empty user && user.mem_num == board.mem_num}">
		<input type="button" value="수정" onclick="location.href='update?board_num=${board.board_num}'">
		<input type="button" value="삭제" id="delete_btn">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.href='delete?board_num=${board.board_num}';
				}
			};
		</script>
		</c:if>
		<input type="button" value="목록" onclick="location.href='list'">
	</div>
	<hr size="1" width="100%">
	<%-- 댓글 작성 --%>
</div>
<!-- 내용 끝 -->
