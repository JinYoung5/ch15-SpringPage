<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 내용 시작 -->
<div class="page-main">
	<h2>글쓰기</h2>
	<form:form action="write" modelAttribute="boardVO" id="register_form" enctype="multipart/form-data">
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
			</li>
		</ul>
		<div class="align-center">
			<form:button>전송</form:button>
			<input type="button" value="목록" onclick="location.href='list'">
		</div>
	</form:form>
</div>
<!-- 내용 끝 -->
