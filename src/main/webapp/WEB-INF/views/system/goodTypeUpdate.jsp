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
								action="<c:url value="/manager/good_type_update_do?id=${goodType.id}"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								
								
								<div class="form-group">
									<label>图片</label>
									<img style="display: block;" class="img-thumbnail" src="${goodType.img}">
									<input type="file" name="img">
									<p class="help-block">*请选择图片文件</p>
								</div>
								
								<div class="form-group">
									<label>名称</label>
									<input class="form-control" name="name" value="${goodType.name}">
									<p class="help-block">请输入商品名称</p>
								</div>
								
								<div class="form-group">
									<label>标题</label>
									<input class="form-control" name="title" value="${goodType.title}">
									<p class="help-block">请输入商品标题</p>
								</div>
								
								
								<div class="form-group">
									<label>默认</label>
									<div>
										<div style="display: inline-block;"><input <c:if test="${goodType.isDefault}">checked="checked" </c:if> class="form-control" type="radio" name="idDefault" style="width:20px;display: inline-block;" value="true"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">是</label></div>
										<div style="display: inline-block;"><input <c:if test="${!goodType.isDefault}">checked="checked" </c:if> class="form-control" type="radio" name="idDefault" style="width:20px;display: inline-block;" value="false"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">否</label></div>
									</div>
									<p class="help-block">是否默认以该商品类别显示商品列表</p>
								</div>
								
								<div class="form-group">
									<label>是否展示</label>
									<div>
										<div style="display: inline-block;"><input <c:if test="${goodType.isDisplay==1}">checked="checked" </c:if> class="form-control" type="radio" name="isDisplay" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">是</label></div>
										<div style="display: inline-block;"><input <c:if test="${goodType.isDisplay==0}">checked="checked" </c:if> class="form-control" type="radio" name="isDisplay" style="width:20px;display: inline-block;" value="0"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">否</label></div>
									</div>
									<p class="help-block">是否展示该商品</p>
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