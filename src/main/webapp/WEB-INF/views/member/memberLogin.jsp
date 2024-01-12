<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 내용 시작 -->
<div class="page-main">
	<h2>회원로그인</h2>
	<form:form id="member_login" action="login" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color" />
		<ul>
			<li class="floating-label">
				<form:input path="id" placeholder="아이디" cssClass="form-input" autocomplete="off" />
				<form:label path="id">아이디</form:label>
				<form:errors path="id" element="div" cssClass="error-color" />
			</li>
			<li class="floating-label">
				<form:password path="passwd" placeholder="비밀번호" cssClass="form-input" />
				<form:label path="passwd">비밀번호</form:label>
				<form:errors path="passwd" element="div" cssClass="error-color" />
			</li>
			<li>
				<label for="auto"><input type="checkbox" name="auto" id="auto">로그인상태유지</label>
			</li>
		</ul>
			<div>
				<form:button class="login-btn">로그인</form:button>
			</div>
	</form:form>
	<p class="align-center">
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main'">
	</p>
</div>
<!-- 내용 끝 -->
