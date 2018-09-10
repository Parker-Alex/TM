<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<nav class="top ">
	<%--<a href="${contextPath}">--%>
    <a name="myTop"></a>
	<a href="home">
		<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
		天猫首页
	</a>
	<span>喵，欢迎来天猫</span>

	<c:if test="${!empty userinfo}">
		<a href="#nowhere" disabled="true">${userinfo.name}</a>
		<a href="forelogout">退出</a>
	</c:if>

	<c:if test="${empty userinfo}">
		<a href="loginpage">请登录</a>
		<a href="registerPage">免费注册</a>
	</c:if>


	<span class="pull-right">
		<a href="forebought" id="myOrder">我的订单</a>
		<a href="forecart" id="myCart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
		购物车<strong>${cartTotalItemNumber}</strong>件</a>
	</span>

</nav>

<script>
    $(function () {

//-----------------没有登录状态下，点击”我的订单“事件------------------
        $("#myOrder").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if (result == "success") {
                        location.href = $("#myOrder").attr("href");
                    } else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });
//----------------------无登录状态下，点击我的购物车事件----------------------
        $("#myCart").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if (result == "success") {
                        location.href = $("#myCart").attr("href");
                    }else {
                        $("#loginModal").modal("show");
                    }
                }
            );
            return false;
        });

//------------------------登录模态框提交事件---------------------
        $("button.loginSubmitButton").click(function () {
            var name = $("#name").val();
            var password = $("#password").val();

            if (name.length == 0 || password.length == 0) {
                $("span.errorMessage").html("请输入账号或密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }

            var page = "foreloginAjax";
            $.ajax({
                url:page,
                data:{"name":name,"password":password},
                type:"GET",
                success:function (result) {
                    if (result == "success") {
                        location.reload();
                    }else {
                        $("span.errorMessage").html("账号或密码错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            });
        });

//---------------------登录提交按钮事件---------------------------
//         $("button.loginSubmitButton").click(function () {
//             var name = $("#name").val();
//             var password = $("#password").val();
//
//             if (name.length == 0 || password.length == 0) {
//                 $("span.errorMessage").html("请输入账号或密码");
//                 $("div.loginErrorMessageDiv").show();
//                 return false;
//             }
//
//             var page = "foreloginAjax";
//             $.get(
//                 page,
//                 {"name":name,"password":password},
//                 function (result) {
//                     if (result == "success") {
//                         location.reload();
//                     }else {
//                         $("span.errorMessage").html("账号或密码错误");
//                         $("div.loginErrorMessageDiv").show();
//                     }
//                 }
//             );
//         });

//---------------------当在输入框输入数据时，错误信息提示框消失--------------------
        $("div.loginInput input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });
    })
</script>



