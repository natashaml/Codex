<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html><html xmlns:jsp="http://java.sun.com/JSP/Page" class="wrapper"
xmlns:fn="http://java.sun.com/jsp/jstl/functions"
xmlns:spring="http://www.springframework.org/comments"
xmlns:sec="http://www.springframework.org/security/comments" version="2.0">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>springRestCrud</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript" src="resources/js/clock.js"></script>


    <%--<link href="resources/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="resources/css/foundation.min.css" rel="stylesheet">
    <link href="resources/css/foundation.css" rel="stylesheet">
    <link href="resources/css/Site.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">

        </div>
        <!--/span-->
        <div class="span9">
            <div class="hero-unit">
                <p align="center" id="con" name="${comments}"></p>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp" />
<script src="resources/js/jquery.js"></script>
<script src="resources/js/foundation.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>


<script>
    function redirectToSignUp() {
        window.location = "signup?id=0";
    }
</script>
<script type="text/javascript" src="http://freeviral.ru/swfobject.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/tagsCloud.js"></script>
<script type="text/javascript">
    function digitalClock() {
        var tag = 'div.time';
        var dots = digits = '';
        var digit = tag+' span';
        var span = digit+':nth-child';
        for (i=1; i<6; i++) for (k=1; k<6; k++) dots += '<b class="c'+i+k+'"/>';
        for (i=0; i<8; i++) digits += '<span/>';
        $(tag).append(digits);
        $(digit).append(dots);
        $(span+'(3), '+span+'(6)').removeAttr('class').addClass('colon');
        function time() {
            var date = new Date();
            var hou = date.getHours().toString();
            var min = date.getMinutes().toString();
            var sec = date.getSeconds().toString();
            hou = (hou<10)?0+hou:hou;
            min = (min<10)?0+min:min;
            sec = (sec<10)?0+sec:sec;
            $(digit+'.colon').css({opacity: 1}).each(function() {
                $(this).delay(400).animate({opacity: 0},250);
            })
            $(digit).removeAttr('class');
            $(span+'(1)').addClass('d'+hou.slice(0,1));
            $(span+'(2)').addClass('d'+hou.slice(1,2));
            $(span+'(3)').addClass('colon');
            $(span+'(4)').addClass('d'+min.slice(0,1));
            $(span+'(5)').addClass('d'+min.slice(1,2));
            $(span+'(6)').addClass('colon');
            $(span+'(7)').addClass('d'+sec.slice(0,1));
            $(span+'(8)').addClass('d'+sec.slice(1,2));
            setTimeout(time,1000);
        }
        time();
    } /* end of digitalClock() */

    (function($) {
        $(function() {

            digitalClock();

        })
    })(jQuery)
</script>
<div class="row">
</div>

</body>
</html>