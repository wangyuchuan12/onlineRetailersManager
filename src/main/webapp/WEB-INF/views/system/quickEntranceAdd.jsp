<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">新增流动栏</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">新增流动栏</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">流动栏管理</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/manager/quick_entrance_add_do"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								
								
								<div class="form-group">
									<label>图片</label>
									<input type="file" name="img">
									<p class="help-block">*请选择图片文件</p>
								</div>
								
								<div class="form-group">
									<label>名称</label>
									<input class="form-control" name="name">
									<p class="help-block">请输入流动栏名称</p>
								</div>
								
								<div class="form-group">
									<label>url</label>
									<input class="form-control" name="url">
									<p class="help-block">请输入该广告栏跳转的链接地址</p>
								</div>
								
								<div class="form-group">
									<label>级别</label>
									<input class="form-control" name="rank" type="number">
									<p class="help-block">请输入商品排列的级别</p>
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
	
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>