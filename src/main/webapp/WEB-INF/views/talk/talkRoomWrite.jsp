<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 내용 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/message.talk.js"></script>
<div id="talkWrite" class="page-main">
	<h2>채팅방 생성</h2>
	<form action="talkRoomWrite" method="post" id="talk_form">
		<input type="hidden" name="members" id="user" data-id="${user.id}" value="${user.mem_num}">
		<ul>
			<li>
				<label for="basic_name">채팅방 이름</label>
				<input type="hidden" name="basic_name" id="basic_name">
				<span id="name_span"></span>
				<input type="checkbox" checked id="name_checked">(자동생성)
			</li>
			<li>
				<label>채팅회원검색</label>
				<input type="text" id="member_search" autocomplete="off">
				<ul id="search_area"></ul>
				<div id="talk_member"></div>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="전송">
			<input type="button" value="목록" onclick="location.href='talkList'">
		</div>
	</form>
</div>
<!-- 내용 끝 -->
