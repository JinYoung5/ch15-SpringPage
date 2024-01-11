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
				<input type="button" id=confirmId value="ID중복체크" class="default-btn">
				<span id="message_id"></span>
				<form:errors path="id" cssClass="error-color" />
			</li>
			<li>
				<form:label path="name">이름</form:label>
				<form:input path="name" />
				<form:errors path="name" cssClass="error-color" />
			</li>
			<li>
				<form:label path="nick_name">별명</form:label>
				<form:input path="nick_name" />
			</li>
			<li>
				<form:label path="passwd">비밀번호</form:label>
				<form:password path="passwd" />
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="phone">전화번호</form:label>
				<form:input path="phone" />
				<form:errors path="phone" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="email">이메일</form:label>
				<form:input path="email" />
				<form:errors path="email" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="zipcode">우편번호</form:label>
				<form:input path="zipcode" />
				<form:errors path="zipcode" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="address1">주소</form:label>
				<form:input path="address1" />
				<form:errors path="address1" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="address2">상세주소</form:label>
				<form:input path="address2" />
				<form:errors path="address2" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="default-btn">전송</form:button>
			<input type="button" value="홈으로" class="default-btn" onclick="location.href='${pageContext.request.contextPath}/main/main'">
		</div>
	</form:form>
</div>
<!-- 내용 끝 -->