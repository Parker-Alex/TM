<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/8/31
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<div class="container">
    <div class="row ">
        <div class="col-md-3">
            <a href="javascript:history.back()" class="btn btn-success">返回上一页</a>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col-md-6">
            <!-- Advanced Tables -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    编辑属性值
                </div>
                <div class="panel-body">
                    <div class="row col-lg-12">
                        <form action="propertyvalue_update" role="form">
                            <div class="form-group">
                                <%-- 隐藏id属性，一并提交 --%>
                                <input type="hidden" name="id" value="${pv.id}">
                                <input type="hidden" name="pdid" value="${pv.pdid}">
                                <input type="hidden" name="ppid" value="${pv.ppid}">
                                <label>属性值：</label>
                                <input name="value" class="form-control" value="${pv.value}"> <br/>
                                <div class="pull-right">
                                    <input type="submit" class="btn btn-default">
                                </div>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
            <!--End Advanced Tables -->
        </div>
    </div>
</div>
