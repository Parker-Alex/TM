<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/22
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入标签库--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>模仿天猫后端</title>
    <link href="img/icon/tmall.png" rel="icon"/>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/admin/style.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script>
        function checkEmpty(id, name) {
            var value = $("#"+id).val();
            if (value.length == 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        function checkNumber(id, name) {
            var value = $("#"+id).val();
            if (value.length == 0) {
                alert(name+"不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            if (isNaN(value)) {
               alert(name+"必须是数字");
               $("#"+id)[0].focus();
               return false;
            }
            return true;
        }

        function checkInt(id, name) {
            var value = $("#"+id).val();
            if (value.length == 0) {
                alert(name+"不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            if (parseInt(value) != value) {
                alert(name+"必须是整数");
                $("#"+id)[0].focus();
                return false;
            }
            return true;
        }

        $(function () {
            $("a").click(function(){
                var deleteLink = $(this).attr("deleteLink");
                console.log(deleteLink);
                if ("true" == deleteLink) {
                    var confirmDelete = confrim("确认要删除");
                    if (confirmDelete)
                        return true;
                    return false;
                }
            });
        })
    </script>
</head>
<body>

