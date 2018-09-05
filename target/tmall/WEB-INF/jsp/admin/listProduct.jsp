<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/25
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<script>
    $(function() {
        $("#addForm").submit(function() {
            if (!checkEmpty("name", "产品名称"))
                return false;
            if (!checkEmpty("title", "产品标题"))
                return false;
            if (!checkNumber("originalprice", "原价"))
                return false;
            if (!checkNumber("promoteprice", "折扣价"))
                return false;
            if (!checkInt("stock", "库存"))
                return false;
            return true;
        });
    });
</script>

<title>产品管理</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="category_list">所有分类</a> </li>
        <li><a href="product_list?cid=${c.id}">${c.name}</a> </li>
        <li class="active">产品管理</li>
    </ol>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <%--<th>图片</th>--%>
                <th>产品名称</th>
                <th>标题</th>
                <th>原价</th>
                <th>折扣价</th>
                <th>库存</th>
                <th>创建时间</th>
                <th>图片管理</th>
                <th>设置属性</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${productList}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <%--<td>--%>
                        <%--<img width="40px" src="img/product/${p.id}/1.jpg">--%>
                    <%--</td>--%>
                    <td>${p.name}</td>
                    <td>${p.title}</td>
                    <td>${p.originalprice}</td>
                    <td>${p.promoteprice}</td>
                    <td>${p.stock}</td>
                    <td>${p.createdate}</td>
                    <td><a href="picture_list?pid=${p.id}"><span class="glyphicon glyphicon-picture"></span></a> </td>
                    <td><a href="propertyvalue_list?pid=${p.id}"><span class="glyphicon glyphicon-th-list"></span></a> </td>
                    <td><a href="product_edit?id=${p.id}"><span class="glyphicon glyphicon-edit"></span></a> </td>
                    <td><a href="product_delete?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@ include file="../include/admin/adminPage.jsp"%>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="product_add">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>产品标题</td>
                        <td><input id="title" name="title" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>原价</td>
                        <td><input id="originalprice" name="originalprice" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>折扣价</td>
                        <td><input id="promoteprice" name="promoteprice" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input id="stock" name="stock" type="text" class="form-control"/></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>
