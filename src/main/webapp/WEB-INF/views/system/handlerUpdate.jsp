<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">处理类管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">处理类管理</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">新增处理类管</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/manager/handler_update_do"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								<input type="hidden" name="id" value="${handler.id}">
								<div class="form-group">
                    					<label>名称</label>
                    					<input class="form-control" name="name" value="${handler.name}">
                    					<p class="help-block">处理类名称</p>
                					</div>
                					
                					<div class="form-group">
                    					<label>类路径</label>
                    					<input class="form-control" name="class_path" value="${handler.classPath}">
                    					<p class="help-block">类路径</p>
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

	function addSubmit(){
		return true;
	}
	$(document).ready(function() {

	});
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>