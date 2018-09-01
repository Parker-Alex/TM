<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/31
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品属性值</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="category_list">所有分类</a></li>
        <li><a href="product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品属性</li>
    </ol>

    <div class="row">
        <div class="col-md-12">
            <!-- Advanced Tables -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    属性值管理表
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th>属性名称</th>
                                <th>属性值</th>
                                <th>编辑属性值</th>
                                <th>删除属性值</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pvs}" var="pv">
                                <tr>
                                    <td>${pv.property.name}</td>
                                    <td>${pv.value}</td>
                                    <td>
                                        <a href="propertyvalue_edit?id=${pv.id}">
                                            <span class="glyphicon glyphicon-edit"></span>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="propertyvalue_delete?id=${pv.id}&pid=${p.id}">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
            <!--End Advanced Tables -->
        </div>
    </div>
    <%-- 属性增加表单 --%>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    增加属性值
                </div>
                <div class="panel-body">
                    <form action="properyvalue_add" role="form" name="addPropertyValueForm">
                        <input type="hidden" name="id" value="">
                        <input type="hidden" name="pdid" value="${p.id}">
                        <label>属性名称：</label>
                        <select class="form-control"
                                onchange="document.addPropertyValueForm.ppid.value=this.value">
                            <c:forEach var="p" items="${ps}">
                                <option value="${p.id}">${p.name}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="ppid" value="">
                        <label>属性值：</label>
                        <input type="text" name="value" class="form-control" placeholder="请在这里输入属性值"><br>
                        <input type="submit" class="btn btn-default pull-right" value="添加">
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
