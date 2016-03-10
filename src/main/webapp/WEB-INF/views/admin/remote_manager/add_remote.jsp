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
			<h1 class="page-header">遥控器数据管理</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">添加遥控器数据</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/remote_manager/add_remote"/>" method="post"
								onsubmit="return addSubmit();">
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>名称</label> <input class="form-control" type="text" name="name">
									<p class="help-block">遥控器的名称</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>类型</label>
									<div>
										<div style="display: inline-block;"><input checked="checked" class="form-control" type="radio" name="type" style="width:20px;display: inline-block;" value="0"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">风扇</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="type" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">空调</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="type" style="width:20px;display: inline-block;" value="2"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">机顶盒</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="type" style="width:20px;display: inline-block;" value="3"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">电视机</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="type" style="width:20px;display: inline-block;" value="4"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">网络盒子</label></div>
									</div>
									<p class="help-block">什么类型的遥控器</p>
								</div>
								
								
								
								<div class="form-group" name="affirm_passwordDiv" id="city_div" >
									<label>城市</label>
									
									<select class="form-control" type="text" name="city_id">
										<c:forEach items="${cities}" var="city" varStatus="">
											<option value="${city.id }"><c:if test="${city.parentId==0}">-------------------------</c:if>${city.name}</option>
										</c:forEach>
									</select>
									<p class="help-block">该遥控器所在的城市</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="else_provider_div">
									<label>风扇,空调，电视机，网络盒子运营商</label> 
									<select class="form-control" type="text" name="manufacture_id">
										<c:forEach items="${manufactures}" var="manufacture" varStatus="">
											<option value="${manufacture.id}">${manufacture.name}</option>
										</c:forEach>
									</select>
									<p class="help-block">风扇，空调，电视机，网络盒子运营商</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="box_provider_div" >
									<label>机顶盒运营商</label> 
									<select class="form-control" type="text" name="service_provider_id">
										<c:forEach items="${serviceProviders}" var="serviceProvider" varStatus="">
											<option value="${serviceProvider.id}">${serviceProvider.name}</option>
										</c:forEach>
									</select>
									<p class="help-block">机顶盒运营商</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>dataType</label>
									<select class="form-control" type="text" name="data_type">
										<c:forEach items="${types}" var="type" varStatus="">
											<option value="${type.name}">${type.displayName}</option>
										</c:forEach>
									</select>
									<p class="help-block">数据类型</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>prot</label> 
									<select class="form-control" type="text" name="prot">
										<c:forEach items="${codes}" var="code" varStatus="">
											<option value="${code.name}">${code.displayName}</option>
										</c:forEach>
									</select>
									<p class="help-block">数据格式</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>codec</label> 
									<select class="form-control" type="text" name="codec">
										<option value="">无</option>
										<c:forEach items="${codecs}" var="codec" varStatus="">
											<option value="${codec.name}">${codec.displayName}</option>
										</c:forEach>
									</select>
									<p class="help-block">编码格式，仅限于红外</p>
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
		showView(1);
		$("input[name='type']").each(function(){
			$(this).click(function(){
				var type = $(this).val();
				showView(type);
			});
		});
	});
	
	function showView(type){
		if(type==2){
			$("#city_div").attr("hidden",false);
			$("#box_provider_div").attr("hidden",false);
			$("#else_provider_div").attr("hidden",true);
		}else{
			$("#city_div").attr("hidden",true);
			$("#box_provider_div").attr("hidden",true);
			$("#else_provider_div").attr("hidden",false);
		}
	}
	
	function addSubmit() {
		var deviceDiv = $("#device_name_div");
		return true;

	}
	function check(name) {
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>