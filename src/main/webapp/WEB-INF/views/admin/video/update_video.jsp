<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">机顶盒云端管理系统 </tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">视频</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">修改视频信息</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/admin/video/do_update"/>" method="post"
								onsubmit="return addSubmit();">
								<input name="id" value="${video.id}" type="hidden"/>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>标题</label> <input class="form-control" type="text" name="title" value="${video.title}">
									<p class="help-block">标题</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>封面</label> <input class="form-control" type="text" name="cover" value="${video.cover}">
									<p class="help-block">封面</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>配置</label> <input class="form-control" type="text" name="config" value="${video.config}">
									<p class="help-block">配置</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv">
									<label>创建时间</label> <input class="form-control" type="text" name="created_at" value="<joda:format value='${video.createdAt}' pattern='yyyy-MM-dd HH:mm'/>" disabled="disabled">
									<p class="help-block">创建时间</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv">
									<label>修改时间</label> <input class="form-control" type="text" value="<joda:format value='${video.updatedAt}' pattern='yyyy-MM-dd HH:mm'/>" disabled="disabled" >
									<p class="help-block">修改时间</p>
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