<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/25
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="category_list">所有分类</a> </li>
        <li><a href="product_list?cid=${p.category.id}">${p.category.name}</a> </li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="product_update">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" value="${p.name}" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>产品标题</td>
                        <td><input id="title" name="title" value="${p.title}" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>原价</td>
                        <td><input id="originalprice" name="originalprice" value="${p.originalprice}" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>折扣价</td>
                        <td><input id="promoteprice" name="promoteprice" value="${p.promoteprice}" type="text" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input id="stock" name="stock" value="${p.stock}" type="text" class="form-control"/></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${p.id}">
                            <input type="hidden" name="cid" value="${p.category.id}">
                            <button type="submit" class="btn btn-success">提交</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
