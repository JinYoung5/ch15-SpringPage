<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<div id="main">
	<div id="main_header">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="side-height">
		<div id="page_nav">
			<tiles:insertAttribute name="nav" />
		</div>
		<div id="page_body">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
	<div id="main_footer" class="page_clear">
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>