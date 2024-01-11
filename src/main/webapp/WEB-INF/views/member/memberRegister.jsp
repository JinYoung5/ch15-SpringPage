<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 내용 시작 -->
<div class="page-main">
	<h2>회원가입</h2>
	<form:form action="registerUser" id="member_register" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color" />
		<ul>
			<li>
				<form:label path="id">아이디</form:label>
				<form:input path="id" placeholder="영문,숫자만 4~12자" autocomplete="off"/>
				<span id="message_id"></span>
				<form:errors path="id" cssClass="error-color" />
			</li>
		</ul>
	</form:form>
</div>
<!-- 내용 끝 -->