<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
span.error {
	color: red;
}
</style>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RestCrud - Signup</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<%--<link href="./resources/css/bootstrap.css" rel="stylesheet">--%>
<link href="./resources/css/foundation.css" rel="stylesheet">
<link href="./resources/css/normalize.css" rel="stylesheet">
<link href="./resources/css/Site.css" rel="stylesheet">
<style type="text/css">


.form-signin .form-signin-heading,.form-signin input[type="text"],.form-signin input[type="text"],.form-signin input[type="text"],.form-signin input[type="password"]
	{
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>

<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<form:form method="post" commandName="signupForm">
			<form class="form-signin">
				<h2 class="form-signin-heading">
					<spring:message code="label.registration"></spring:message>
				</h2>
				<span class="error"> <form:errors path="username" /> <input
					type="text" class="input-block-level" placeholder="Login"
					name="username"> <form:errors path="email" /> <input
					type="text" class="input-block-level" placeholder="Email address"
					name="email"> <form:errors path="password" /> <input
					type="password" class="input-block-level" placeholder="Password"
					name="password"> <form:errors path="confirmPassword" /> <input
					type="password" class="input-block-level"
					placeholder="Repeat password" name="confirmPassword">
				</span>
				<button class="btn btn-large btn-success" type="submit">
					<spring:message code="label.submit"></spring:message>
				</button>
			</form>
		</form:form>

	</div>
	<!-- /container -->
	<jsp:include page="footer.jsp" />

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./resources/js/jquery.js"></script>
	<script
		src="./resources/js/bootstrap.min.js"></script>
    <script
            src="./resources/js/foundation.min.js"></script>
	<script
		src="./resources/js/showdown.js"></script>
	<script>
		function redirectToSignUp() {
			window.location = "signup?id=0";
		}
	</script>

</body>
</html>