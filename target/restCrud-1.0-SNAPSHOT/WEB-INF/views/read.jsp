<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RestCrud</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link href="../resources/css/Site.css" rel="stylesheet">
<%--<link href="../resources/css/bootstrap.css" rel="stylesheet">--%>
<link href="../resources/css/foundation.css" rel="stylesheet">
<link href="../resources/css/normalize.css" rel="stylesheet">

</head>

<body>
	<jsp:include page="header.jsp" />
	<div class="span3 text1">
	
					<form class="navbar-form pull-right">
	<span id="change-style" > <a href="?theme=black" ><img
								src="${pageContext.request.contextPath}/resources/img/click.jpg"
								width="62" height="62" /></a></span></form>
		<div class="well sidebar-nav">
			<ul class="nav nav-list">
				<li class="nav-header">
					<h2>${project.name}</h2>
				</li>
				<li class="nav-header"><spring:message code="label.tasks"></spring:message>
				</li>
			</ul>
			<select id="listTasks">
				<c:forEach items="${project.orderedTasks}" var="task">
					<option>${task.number}</option>
				</c:forEach>
			</select>
			<c:forEach items="${project.orderedTasks}" var="task">
				<input id="${task.number}listTitle" type="hidden"
					value="${task.title}">
				<input id="${task.number}listText" type="hidden"
					value="${task.jsText}">
			</c:forEach>

			<div id="markdown" class="span1 fullscreen text1"></div>
		</div>
	</div>
		
	<!--/.fluid-container-->

	<!-- javascript
    ================================================== -->

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="../resources/js/jquery.js"></script>
	<script type="text/javascript"
		src="../resources/js/vendor/modernizr.js"></script>
	<script src="../resources/js/foundation.min.js"></script>
    <script src="../resources/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="../resources/js/showdown.js"></script>
	<script src="../resources/js/jquery.js"></script>
	<script src="../resources/js/jquery.cookie.js"></script>
	<script>	
$(function () {
$("#change-style").click(function () {
$(".text1").addClass("black");
$(".text1 h3").removeClass("black2");
})
});
</script>
	<script>
		function redirectToSignUp() {
			window.location = "${pageContext.request.contextPath}/signup?id=0";
		}
	</script>
	<script type="text/javascript" src="../resources/js/search.js">
		
	</script>
	<script>
	$(function () {
		var isBig = false;
		
        $(".fullscreen").click(function () {
            if (!isBig) {
	        	$(this).css({
	                position: "fixed",
	                width: "100%",
	                zIndex: "9999",
	                top: "0px",
	                left: "0px",
	                bottom: "0px",
	                right: "0px",
	                background: "#fff",
	                color: "#000",
	                overflow: "auto"
	            });
	        	isBig = true;
            } else {
            	$(this).removeAttr("style");
            	isBig = false;
            }
            
        })
    });
	</script>
	
	<script>
		$(document).ready(function() {
			$('#task').on("click", function() {
				var date = new Date;
				date.setDate(date.getDate() + 1);
				var id = $("select#id").val();
				$.cookie("task", id, {
					expires : date
				});
			});
			$(window).on("scroll", function() {
				var date = new Date;
				date.setDate(date.getDate() + 1);
				$.cookie("scroll", $(document).scrollTop(), {
					expires : date
				});
			});
		});
	</script>
	<script>
	$("#listTasks").change(function() {
		$("select option:selected").each(function() {
			var optionTitle = $(this).text() + "listTitle";
			var optionText = $(this).text() + "listText";
			var textValue = ($('#'+ optionText).val()).replace(/\\\//g, "/")
			.replace(/\\r\\n/g, "<br>");
			var converter = new Showdown.converter();
			$('#markdown').html('<h2>' + $('#'+ optionTitle).val()
			+ '</h2><hr><p>'
			+ converter.makeHtml(textValue.replace(/\\"/g, ""))
			+ '</p>');
			});
		}).change();
	</script>
</body>
</html>
