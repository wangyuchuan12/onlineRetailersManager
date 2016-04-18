<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">新增类别</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">新增类别</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">类别管理</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/manager/good_type_add_do"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								
								
								<div class="form-group">
									<label>图片</label>
									<input type="file" name="img">
									<p class="help-block">*请选择图片文件</p>
								</div>
								
								<div class="form-group">
									<label>名称</label>
									<input class="form-control" name="name">
									<p class="help-block">请输入商品名称</p>
								</div>
								
								<div class="form-group">
									<label>标题</label>
									<input class="form-control" name="title">
									<p class="help-block">请输入商品标题</p>
								</div>
								
								  
								<div class="form-group">
									<label>默认</label>
									<div>
										<div style="display: inline-block;"><input checked="checked" class="form-control" type="radio" name="idDefault" style="width:20px;display: inline-block;" value="true"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">是</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="idDefault" style="width:20px;display: inline-block;" value="false"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">否</label></div>
									</div>
									<p class="help-block">是否默认以该商品类别显示商品列表</p>
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