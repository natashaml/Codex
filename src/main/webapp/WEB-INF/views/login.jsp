<%--suppress XmlPathReference --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Log In</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link
	href="./resources/css/foundation.css"
	rel="stylesheet"/>
<%--<link--%>
    <%--href="./resources/css/bootstrap.css"--%>
    <%--rel="stylesheet"/>--%>
<link
	href="./resources/css/normalize.css"
	rel="stylesheet"/>
<link href="./resources/css/Site.css" rel="stylesheet"/>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<c:url value='j_spring_security_check' var="securityCheck" />

		<form class="form-horizontal" name='login_form'
			action="${securityCheck}" method='POST'>
			<div class="control-group">
				<div class="controls">
					<input type="text" name='j_username'
						placeholder=<spring:message code="label.login1"></spring:message>>
				</div>
			</div>
			<div class="contol-group">
				<div class="controls">
					<input type="password" name='j_password'
						placeholder=<spring:message code="label.password"></spring:message>>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox" for="j_remember"><spring:message
							code="label.rememberMe"></spring:message> <input id="j_remember"
						name="j_spring_security_remember_me" type="checkbox" /> </label>
					<button type="submit" class="btn btn-success">
						<spring:message code="label.login"></spring:message>
					</button>
				</div>
			</div>
		</form>
	</div>

	<jsp:include page="footer.jsp" />

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./resources/js/jquery.js"></script>
	<script src="./resources/js/foundation.min.js"></script>
    <script src="./resources/js/bootstrap.min.js"></script>
	<script src="./resources/js/showdown.js"></script>

	<script>
		function redirectToSignUp() {
			window.location = "signup?id=0";
		}
	</script>

</body>
</html>
