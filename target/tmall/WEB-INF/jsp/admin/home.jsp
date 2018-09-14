<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/9/4
  Time: 23:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<title>管理界面</title>
<div class="container">
    <div class="col-md-6 col-md-offset-4">
        <div class="loginErrorMessageDiv navbar-fixed-top text-center">
            <div class="alert alert-danger" >
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>
        <form class="form-inline">
            <div class="form-group has-success has-feedback">
                <label class="control-label glyphicon glyphicon-user" for="name"></label>
                <input type="text" class="form-control" id="name" name="name" value="" aria-describedby="inputSuccess4Status" placeholder="用户名">
                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
                <span id="inputSuccess4Status" class="sr-only">(success)</span>
            </div>
        </form>
        <form class="form-inline">
            <div class="form-group has-success has-feedback">
                <label class="control-label glyphicon glyphicon-lock" for="password"></label>
                <%--<div class="input-group">--%>
                    <input type="password" class="form-control" id="password" name="password" value="" aria-describedby="inputGroupSuccess3Status" placeholder="密码">
                <%--</div>--%>
                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
                <span id="inputGroupSuccess3Status" class="sr-only">(success)</span>
            </div>
        </form>
        <button class="btn btn-success col-md-3 col-md-offset-1" id="login">登录</button>
    </div>
</div>

<script>
    $(function () {
        $("div.loginErrorMessageDiv").hide();
        var page = "foreloginAjax";

        $("#login").click(function () {
            if ($("#name").val() != "admin" && $("#password").val() != "admin") {
                $("span.errorMessage").html("用户名或密码错误");
                $("div.loginErrorMessageDiv").show();
                $("div.form-group").removeClass("has-success").addClass("has-error");
                $("span.glyphicon").removeClass("glyphicon-ok").addClass("glyphicon-remove");
                $("#login").removeClass("btn-success").addClass("btn-danger");
                return false;
            }
           $.ajax({
               url:page,
               data:{"name":$("#name").val(),
                   "password":$("#password").val()},
               type:"GET",
               success:function (result) {
                   if (result == "success") {
                       location.href = "category_list";
                   }else {
                       $("span.errorMessage").html("用户名或密码错误");
                       $("div.loginErrorMessageDiv").show();
                   }
               }
           });
        });

        $("form input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
            $("div.form-group").removeClass("has-error").addClass("has-success");
            $("#login").removeClass("btn-danger").addClass("btn-success");
            $("span.glyphicon").removeClass("glyphicon-remove").addClass("glyphicon-ok");
        });
    });
</script>