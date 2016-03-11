<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<tiles:insertDefinition name="adminLayout">
	<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
	<tiles:putAttribute name="title">机顶盒遥控数据</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">机顶盒遥控数据</h1>
				</div>

			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-primary">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-3">
													<i class="fa fa-comments fa-5x"></i>
												</div>
												<div class="col-xs-9 text-right">
													<div class="huge">${count}</div>
													<div>遥控器数量</div>
												</div>
											</div>
										</div>

									</div>
								</div>
								
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-green">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-3">
													<i class="fa fa-tasks fa-5x"></i>
												</div>
												<div class="col-xs-9 text-right">
													<div class="huge">${brandCount}</div>
													<div>覆盖品牌</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-lg-3 col-md-6">
									<div class="panel panel-red">
										<div class="panel-heading">
											<div class="row">
												<div class="col-xs-3">
													<i class="fa fa-tasks fa-5x"></i>
												</div>
												<div class="col-xs-9 text-right">
													<div class="huge">${fn:length(brands)}</div>
													<div>当前品牌数量</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">品牌总揽</div>
						<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="dataTables_length" id="dataTables-example_length">
									<select class="input-sm" style="width: 100px;"  id="city_select">
										<c:forEach items="${provinces}" var="province">
											
											<option <c:if test="${province.id==thisProvince}">selected = "selected"</c:if> value="${province.id}">${province.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>


							<div class="table-responsive">
								<div class="row">
									<div class="col-sm-6">
										<div class="dataTables_length" id="dataTables-example_length">

										</div>
									</div>
									
								</div>
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-admin">

									<thead>
										<tr>
											<th>id</th>
											<th>品牌</th>
											<th>城市</th>
											<th>遥控器数量</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${brands}" var="brand">
											<tr class="odd gradeX">
												<td>${brand.id}</td>
												<td>${brand.name}</td>
												<td>${brand.city}</td>
												<td>${brand.count}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input id="temp_url" value="<c:url value="/statistics/sp"/>" hidden="true"></input>
	</tiles:putAttribute>
	<tiles:putAttribute name="footerJavascript">
		<script>
			citySelect();
			function citySelect(){
				
				$("#city_select").change(function(a){
					window.location.href=$("#temp_url").val()+"?province_id="+$("#city_select").find("option:selected").val();
				});
			}
			function addSubmit() {
				return true;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
