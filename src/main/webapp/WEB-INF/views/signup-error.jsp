<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Sign Up Error</title>
<link href="../../resources/css/Site.css" rel="stylesheet">
<spring:theme code='style'/>" rel="stylesheet">
</head>
<body>
    <h1>Sign Up Error</h1>

	<p>Unfortunately we failed to add you to a database</p>

	<a href="${pageContext.request.contextPath}/" title="Home">Home</a>
</body>
</html>