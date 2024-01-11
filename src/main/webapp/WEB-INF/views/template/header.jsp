<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<h2 class="align-center">SpringPage</h2>
<div class="align-right">
	<c:if test="${!empty user}">
	<a href="${pageContext.request.contextPath}/member/myPage">MY페이지</a>
	</c:if>
	
	<c:if test="${!empty user}">
	<a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
	</c:if>
	
	<c:if test="${empty user}">
	<a href="${pageContext.request.contextPath}/member/registerUser">회원가입</a>
	<a href="${pageContext.request.contextPath}/member/login">로그인</a>
	</c:if>
	
	<c:if test="${empty user || user.auth != 9}">
	<a href="${pageContext.request.contextPath}/main/main">홈으로</a>
	</c:if>
	<c:if test="${!empty user && user.auth == 9}">
	<a href="${pageContext.request.contextPath}/main/admin">관리자</a>
	</c:if>
</div>
<!-- 상단 끝 -->