<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    span.error {
        color: red;
    }
</style>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><spring:message code="label.creatingProject"></spring:message></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 300px;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],.form-signin textarea {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
    </style>
    <%--<link href="../../resources/css/bootstrap.css" rel="stylesheet">--%>
    <link href="../../resources/css/foundation.css" rel="stylesheet">
    <link href="../../resources/css/normalize.css" rel="stylesheet">
    <link href="../../resources/css/Site.css" rel="stylesheet">
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <h2 class="form-signing-heading">
        <spring:message code="label.creatingProject"></spring:message>
    </h2>
    <form:form method="post" commandName="addProjectForm">
        <input type="text" class="input-block-level"
               placeholder=<spring:message code="label.projectName"></spring:message>
                       name="title">

        <textarea class="input-block-level" name="synopsis"
                  placeholder=<spring:message code="label.synopsis"></spring:message>></textarea>


        <button class="btn btn-large btn-success" type="submit">
            <spring:message code="label.crProj"></spring:message>
        </button>
    </form:form>
</div>
<jsp:include page="footer.jsp" />

<!-- /container -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="../../resources/js/jquery.js"></script>
<script src="../../resources/js/jquery.js"></script>
<script type="text/javascript"
        src="../../resources/js/vendor/modernizr.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/js/foundation.min.js"></script>
<script>
    function redirectToSignUp() {
        window.location = "signup?id=0";
    }
</script>
<input type="text" id="test" data-provide="typeahead" />

<script>
    $('.comments').typeahead({
        source : function(query, process) {
            return $.get('/autocomplete', {
                query : query
            }, function(data) {
                return process(data);
            });
        }
    });
</script>
</body>
</html>