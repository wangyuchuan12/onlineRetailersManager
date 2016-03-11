<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/admin/"/>">微纳-后台管理系统</a>

        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" ></a>
        <a class="navbar-brand" name="password_adjust" href="<c:url value="/admin/password"/>"><span id="adjust" style="color:red"></span></a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="<c:url value="/admin/password"/>"><i class="fa fa-gear fa-fw"></i> 修改密码</a>
                </li>
                <li class="divider"></li>
                <li><a href="<c:url value="/admin/logout"/>"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                </li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a class="" href="<c:url value="/statistics/data_type"/>"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                </li>
                 <li>
                    <a class="" href="<c:url value="/statistics/sp"/>"><i class="fa fa-hdd-o fa-fw"></i> 机顶盒</a>
                </li>
                <li>
                    <a class="" href="<c:url value="/statistics/tv"/>"><i class="fa fa-laptop fa-fw"></i> 电视</a>
                </li>
                <li>
                    <a class="" href="<c:url value="/statistics/netbox"/>"><i class="fa fa-inbox fa-fw"></i> 网络盒子</a>
                </li>
                <li>
                    <a class="" href="<c:url value="/statistics/air"/>"><i class="fa fa-crosshairs fa-fw"></i> 空调</a>
                </li>
                
                <li>
                    <a class="" href="<c:url value="/statistics/fan"/>"><i class="fa fa-crosshairs fa-fw"></i> 风扇</a>
                </li>
                <shiro:hasRole name="god">
                <li>
                    <a href="#"><i class="fa fa-plus fa-fw"></i>添加遥控器信息<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="<c:url value="/remote_info/type_list"/>">遥控器数据格式</a>
                        </li>
                        <li>
                            <a href="<c:url value="/remote_info/code_list"/>">遥控器数据协议格式</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/remote_info/codec_list"/>">遥控器编码格式</a>
                        </li>
                        
                        <li>
                            <a href="<c:url value="/remote_info/key_list"/>">遥控器按键定义</a>
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                </shiro:hasRole>
                <li>
                     <a href="<c:url value="/remote_manager/list_view"/>"><i class="fa fa-database fa-fw"></i>遥控器数据管理</a>
                </li>
                <shiro:hasRole name="god">
	                <li>
	                     <a href="<c:url value="/apply_manager/list_view"/>"><i class="fa fa-edit fa-fw"></i>申请管理</a>
	                </li>
                </shiro:hasRole>
                <li>
                     <a href="<c:url value="#"/>"><i class="fa fa-male fa-fw"></i>API授权管理</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
