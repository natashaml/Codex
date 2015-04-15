<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html><html xmlns:jsp="http://java.sun.com/JSP/Page" class="wrapper"
xmlns:fn="http://java.sun.com/jsp/jstl/functions"
xmlns:spring="http://www.springframework.org/comments"
xmlns:sec="http://www.springframework.org/security/comments" version="2.0">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<c:url value='./j_spring_security_logout' var="securityLogout" />
<div class="nav-collapse collapse">

    <form class="navbar-form pull-right">

        <c:if test="${person eq 'Guest'}">
            <button type="submit" class="btn btn-success"
                    onClick="javascript:window.location.href ='${pageContext.request.contextPath}/login';return false;">
                <spring:message code="label.login"></spring:message>
            </button>

            <button type="submit" class="btn btn-warning"
                    onclick="javascript:redirectToSignUp();return false;">
                <spring:message code="label.registration"></spring:message>
            </button>
        </c:if>

        <c:if test="${!(person eq 'Guest')}">
            <button type="submit" class="btn btn-success"
                    onClick="javascript:window.location.href ='${pageContext.request.contextPath}/${securityLogout}';return false;">
                <spring:message code="label.logout"></spring:message>
            </button>
        </c:if>

    </form>
    <ul class="nav">
        <li class="active"><a href="${pageContext.request.contextPath}/"><spring:message
                code="label.home"></spring:message></a></li>

        <c:if test="${person eq 'Admin'}">
            <li><a href="${pageContext.request.contextPath}/admin"><spring:message
                    code="label.adminPage"></spring:message></a></li>
        </c:if>

        <c:if test="${(person eq 'User') || (person eq 'Admin')}">
            <li><a href="${pageContext.request.contextPath}/users/${user.id}"><spring:message
                    code="label.pageForCreateProjects"></spring:message></a></li>
        </c:if>

    </ul>

    <div class="time"></div>
    <div class="row"></div>
</div>
<!--/.nav-collapse -->
</div>
</div>
</div>
</html>