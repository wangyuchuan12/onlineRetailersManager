<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">微纳遥控器后台管理系统<</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">遥控器信息管理</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">添加数据格式</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/remote_info/type_add_do"/>" method="post"
								onsubmit="return addSubmit();">
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>数据类型</label> <input class="form-control" type="text" name="display_name">
									<p class="help-block">数据类型,主要用于显示，可用中文表示</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>key</label> <input class="form-control" type="text" name="name">
									<p class="help-block">按键存储是所对应的key</p>
								</div>
								<button type="submit" class="btn btn-default">提交</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
	$(document).ready(function() {
//		check('usernameDiv');
	});
	function addSubmit() {
		var deviceDiv = $("#device_name_div");
		return true;

	}
	function check(name) {
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>