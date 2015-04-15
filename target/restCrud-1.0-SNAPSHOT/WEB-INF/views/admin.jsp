<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>springRestCrud - Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">


    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="resources/js/jquery.dataTables.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <%--<script src="resources/js/jquery-ui.js"></script>--%>
    <%--<script src="resources/js/jquery-1.10.2.js"></script>--%>
    <%--<script src="resources/js/jquery.js"></script>--%>

    <script type="text/javascript" src="resources/js/vendor/modernizr.js"></script>
    <script src="resources/js/foundation.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>


    <link href="resources/css/foundation.css" rel="stylesheet">
    <%--<link href="resources/css/bootstrap.css" rel="stylesheet">--%>
    <link href="resources/css/normalize.css" rel="stylesheet">
    <link href="resources/css/Site.css" rel="stylesheet">
    <link href="resources/css/dataTables.css" media="screen"/>
    <link href="resources/css/jquery-ui.css" media="screen"/>


    <style>
        div#users-contain { width: 350px; margin: 20px 0; }
        .ui-dialog .ui-state-error { padding: .3em; }
        .validateTips { border: 1px solid transparent; padding: 0.3em; }
        span.linkImitation {
            cursor: pointer;
            color: #222222;
            text-decoration: underline;
        }

        span.linkImitation:hover {
            text-decoration: none;
        }

            /* скрываем стандартную панель поиска в DataTables */
        .dataTables_filter, .dataTables_info {
            display: none;
        }
    </style>
</head>

<body>
<jsp:include page="header.jsp" />
<div id="dialog-form" title="Create new user">
    <p class="validateTips">All fields are required.</p>
    <form>
        <fieldset>
            <div style="margin-bottom: 5px;">
                <label for="login">Login</label>
            </div>
            <input type="text" name="login" id="login" class="text ui-widget-content ui-corner-all">

            <div style="margin-bottom: 5px;">
                <label for="password">Password</label>
            </div>
            <input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">

            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">

        </fieldset>
    </form>
</div>

<button id="create">Add developer</button>

<div class="container-fluid">
<div id="users-contain" class="ui-widget">
<table id="users" class="ui-widget ui-widget-content display" cellspacing="0" width="100%">
    <thead>
    <tr class="ui-widget-header ">
        <th>Name</th>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr class="ui-widget-content" id="user${user.id}">
            <td>${user.name}</td>
                <%--<div class="span4">--%>
                <%--<h2>${user.name}</h2>--%>
            <td>
                        <span class="linkImitation" onclick="editUserClick(${user.id})">
                                <spring:message code="label.editUser"/>
                        </span>
            </td>
            <td>
                          <span class="linkImitation" onclick="deleteUserClick(${user.id})">
                                <spring:message code="label.deleteUser"/>
                        </span>

            </td>
            <td>
                          <span class="linkImitation" onclick="assignTaskClick(${user.id})">
                                <spring:message code="label.assign"/>
                        </span>

            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<p>--%>
<%--<a class="btn" onclick="deleteUserClick(${user.id})"><spring:message--%>
<%--code="label.deleteUser"></spring:message> </a>--%>
<%--&lt;%&ndash;<span class="btn"  href="admin/delete/${user.id} onclick="deleteUserClick(${user.id})">&ndash;%&gt;--%>
<%--&lt;%&ndash;<spring:message code="label.deleteUser"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;</span>&ndash;%&gt;--%>
<%--</p>--%>
<%--</div>--%>

<%--</c:forEach>--%>
<hr>

<!--/.fluid-container-->
<jsp:include page="footer.jsp" />


<script>
var userIdToSave, usersDataTable;

var form,
        login = $( "#login" ),
        password = $( "#password" ),
        allFields = $( [] ).add( login ).add( password ),
        tips = $( ".validateTips" );

dialogUser = $( "#dialog-form" ).dialog({
    autoOpen: false,
    width: 350,
    modal: true,
    buttons: {
        "Create an new developer": addUser,
        Cancel: function() {
            dialogUser.dialog( "close" );
        }
    },


    close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
    }
});

form = dialogUser.find( "form" ).on( "submit", function( event ) {
    event.preventDefault();
    addUser();
});

$( "#create" ).button().on( "click", function() {
    dialogUser.dialog( "open" );
    userIdToSave = null;
    dialogUser.css({overflow:"auto"});
});

function addUser() {
    var valid = true;
    allFields.removeClass("ui-state-error");

    valid = valid && checkLength(login, "login", 3, 16);
    valid = valid && checkLength(password, "password", 5, 16);

    if (valid) {
        if (userIdToSave != null) {
            editUserAjaxCall();
        } else {
            addUserAjaxCall();
        }
        dialogUser.dialog("close");
    }
    return valid;
}

function updateTips(t) {
    tips
            .text(t)
            .addClass("ui-state-highlight");
    setTimeout(function () {
        tips.removeClass("ui-state-highlight", 1500);
    }, 500);
}

function checkLength( o, n, min, max ) {
    if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Длина " + n + " должна быть в пределах " +
                min + " и " + max + "." );
        return false;
    } else {
        return true;
    }
}



function redirectToSignUp() {
    window.location = "signup?id=0";
}
function deleteUserClick(id) {
    userIdToSave=id;
    deleteUserAjaxCall();

}
function isEmpty(str){
    return str == null || str.length === 0 || ($.trim(str).length===0);
}

function showError(errText, errorDiv) {
    errorDiv.empty();
    errorDiv.append(errText);

}


$(document).ready(function() {
    usersDataTable =  $('#users').dataTable({
        "searching": false,
        "lengthChange": false,
        "bInfo" : false,
        "iDisplayLength": 10,
        "language": {
            "paginate": {
                "previous": "Предыдущая",
                "next": "Следующая"
            }
        }
    });
} );
///////////////////////////////////////// ДЕВЕЛОПЕРЫ /////////////////////////////////////////



function deleteUserAjaxCall() {
    $.ajax({
        type: "post",
        url: "${pageContext.request.contextPath}/admin/delete.htm",
        cache: false,
        data: 'id=' + userIdToSave,

        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        },

        success: function (response) {
            if (!isEmpty(response.errorMessage)) {
                showError(response.errorMessage, $("#errorsUser"));
                return;
            }
            usersDataTable.fnDeleteRow('#user' + userIdToSave);
        },
        error: function (request, status, error) {
            showError("${unknownError}", $("#errorsUser"));
        }
    });
}

function addUserAjaxCall() {
    var login =$("#login").val();
    var password =$("#password").val();
    $.ajax({
        type: "post",
        url: "${pageContext.request.contextPath}/admin/addUser.htm",
        cache: false,
        data:"login=" + $("#login").val() +
                "&password=" + $("#password").val(),

        beforeSend:function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        },

        success: function (response) {

            if (!isEmpty(response.errorMessage)) {
                showError("${unknownError}", $("#errorsUser"));
                return;
            }

            var userIndex = usersDataTable.fnAddData([
                login,
                '<span class="linkImitation" onclick="editUserClick(' + response.id + ')"><spring:message code="label.editUser"/></span>',
                '<span class="linkImitation" onclick="deleteUserClick(' + response.id + ')"><spring:message code="label.deleteUser"/></span>',
                '<span class="linkImitation" onclick="editUserClick(' + response.id + ')"><spring:message code="label.assign"/></span>'

            ]);

            var row = usersDataTable.fnGetNodes(userIndex);
            $(row).attr('id', 'user' + response.id);

            $("#login").val("");
            $("#password").val("");
        },

        error: function () {
            showError("${unknownError}", $("#errorsUser"));
        }
    });
}

function editUserClick(id) {
    if (id == null || id.length == 0) {
        dialogUser.parent().find("span.ui-dialog-title").html("Add Developer");
    } else {
        dialogUser.parent().find("span.ui-dialog-title").html("Edit Developer");
    }

    $("#login").val($("#users tr#user" + id + " td:first-child").text());
    $("#password").val($("#users tr#user" + id + " td:nth-child(2)").text());


    dialogUser.dialog("open");
    userIdToSave = id;

}

function editUserAjaxCall() {
    var login = $("#login").val();
    var password = $("#password").val();
    $.ajax({
        type: "post",
        url: "${pageContext.request.contextPath}/admin/editUser.htm",
        cache: false,
        data:   "login=" + $("#login").val() +
                "&password=" + $("#password").val() +
                "&id=" + userIdToSave,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
        },

        success: function (response) {

            if (!isEmpty(response.errorMessage)) {
                showError("${unknownError}", $("#errorsUser"));
                return;
            }
            usersDataTable.fnDeleteRow($('#user' + userIdToSave));

            var userIndex = usersDataTable.fnAddData([
                login,
                '<span class="linkImitation" onclick="editUserClick(' + userIdToSave + ')"><spring:message code="label.editUser"/></span>',
                '<span class="linkImitation" onclick="deleteUserClick(' + userIdToSave + ')"><spring:message code="label.deleteUser"/></span>',
                '<span class="linkImitation" onclick="assignTaskClick(' + userIdToSave + ')"><spring:message code="label.assign"/></span>'
            ]);
            var row = usersDataTable.fnGetNodes(userIndex);
            $(row).attr('id', 'user' + userIdToSave);

            $("#login").val("");
            $("#password").val("");
        },

        error: function () {
            showError("${unknownError}", $("#errorsUser"));
        }
    });
}
///////////////////////////////////////// СОЗДАНИЕ ПРОЕКТА /////////////////////////////////////////




function redirectToSignUp() {
    window.location = "signup?id=0";
}
</script>

<div id="errorsUser"></div>
</body>
</html>
