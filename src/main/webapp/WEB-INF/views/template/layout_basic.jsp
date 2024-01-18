<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<tiles:insertAttribute name="css" ignore="true" />			<%-- name="css" 를 넣을때 ignore="true"가 없으면 에러뜸 --%>
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="main_body">
		<tiles:insertAttribute name="body" />
	</div>
	<div id="main_footer">
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>