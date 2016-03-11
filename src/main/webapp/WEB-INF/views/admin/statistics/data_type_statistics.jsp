<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<tiles:insertDefinition name="adminLayout">
	<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
	<tiles:putAttribute name="title">Dashboard</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">数据总揽</h1>
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
													<div class="huge">${irCount}</div>
													<div>红外遥控数量</div>
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
													<div class="huge">${a24gCount}</div>
													<div>2.4g遥控数量</div>
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
													<div class="huge">${other}</div>
													<div>其他遥控数量</div>
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
						<div class="panel-body">


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
											<th>近期数据导入</th>
											<th>数量</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${brands}" var="brand">
											<tr class="odd gradeX">
												<td>${brand.id}</td>
												<td>${brand.name}</td>
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
	</tiles:putAttribute>
	<tiles:putAttribute name="footerJavascript">
		<script>
			function addSubmit() {
				return true;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
