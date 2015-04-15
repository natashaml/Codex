<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RestCrud - Edit Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">


<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/skins/markitup/style.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/sets/markdown/style.css" />
<%--<link--%>
	<%--href="${pageContext.request.contextPath}/resources/css/bootstrap.css"--%>
	<%--rel="stylesheet"/>--%>
    <link
            href="${pageContext.request.contextPath}/resources/css/foundation.css"
            rel="stylesheet"/>
<link
	href="${pageContext.request.contextPath}/resources/css/normalize.css"
	rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/css/Site.css"
	rel="stylesheet"/>

</head>

<body>
	<jsp:include page="header.jsp" />
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header"><h2>${project.name}</h2></li>
						<li class="nav-header"><spring:message code="label.tasks"></spring:message></li>
					</ul>
					<ul id="sortable">
						<c:forEach items="${project.orderedTasks}" var="task">
							<li>
								<button type="submit" class="btn btn-success"
									onClick="javascript:window.location.href ='${task.id}';return false;">${task.number}</button>
							</li>
							<script></script>
						</c:forEach>
					</ul>


				</div>
				<!--/.well -->

				<button id="biber" type="submit" class="btn btn-success"
					onClick="javascript:window.location.href ='?action=addTask';return false; ">
					<spring:message code="label.addTask"></spring:message>
				</button>
				<button id="bi" type="submit" class="btn btn-danger"
					onClick="javascript:window.location.href ='?action=delete';return false;">
					<spring:message code="label.deleteTask"></spring:message>
				</button>
				<label class="lab"><spring:message
						code="label.currentTask"></spring:message>:
					${currentTask.number}</label>
			</div>

			<!--/span-->
			<form:form id="saveForm" method="post" commandName="saveTaskForm" >
				<input id="taskTitle" type="text" placeholder="Task name"
					name="title" value="${currentTask.title}">
				<p align="center">
					<textarea id="markdown" class="span1" placeholder="Task text"
						name="TaskText">${currentTask.text}</textarea>
				</p>
				<input id="indexChanges" name="indexChanges" type="hidden" value="" >
				<button id="saveTask" type="submit" class="btn btn-primary">
					<spring:message code="label.save"></spring:message>
				</button>
			</form:form>
		</div>
		<!--/row-->

		<jsp:include page="footer.jsp" />

	</div>
	<!--/.fluid-container-->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/foundation.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/showdown.js"></script>
	<script>
		function redirectToSignUp() {
			window.location = "signup?id=0";
		}
	</script>
	<script>
		$(function() {
			$("#sortable").sortable({
				handle : 'button',
				cancel : ''
			});
			$("#sortable").disableSelection();
			$('#saveForm').submit(function() {
				var indexes = new Array();
				for (var i = 0; i < $("#sortable button").length; i++) {
					indexes.push($("#sortable button")[i].innerText);
				}
				$('#indexChanges').val(indexes.join(","));

		        return true;
		    });
		});
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/search.js">

	</script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js">

	</script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery-ui.js">

	</script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery.markitup.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/set.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/vendor/modernizr.js"></script>
	<script>
		$(document).ready(function() {
			$('#markdown').markItUp(mySettings);
		});
	</script>
</body>
</html>
