<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RestCrud - Users</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">



<%--<link href="../../../resources/css/bootstrap.css" rel="stylesheet">--%>
<link href="../../../resources/css/normalize.css" rel="stylesheet">
<link href="../../../resources/css/Site.css" rel="stylesheet">
<link href="../../../resources/css/foundation.css" rel="stylesheet">

</head>

<body>
	<jsp:include page="header.jsp" />
	<div class="container-fluid">

		<c:forEach items="${users}" var="user">
			<div class="span4">
				<h2>${user.name}</h2>
				<p>${user.email}</p>
				<p>isAdmin = ${user.admin}</p>
				<p>
					<a class="btn" href="/users/${user.id}"><spring:message
							code="label.viewB"></spring:message> &raquo;</a>
				</p>
			</div>

		</c:forEach>
		<hr>

	</div>
	<!--/.fluid-container-->

	<jsp:include page="footer.jsp" />

	<script src="../../../resources/js/jquery.js"></script>
	<script src="../../../resources/js/bootstrap.min.js"></script>
    <script src="../../../resources/js/foundation.min.js"></script>
	<script>
		function redirectToSignUp() {
			window.location = "signup?id=0";
		}
	</script>


</body>
</html>
