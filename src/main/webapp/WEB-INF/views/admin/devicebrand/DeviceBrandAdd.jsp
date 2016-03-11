<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
    <tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
    <tiles:putAttribute name="title">机顶盒云端管理系统 </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">设备品牌管理</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form action="<c:url value="/admin/devicebrand/add_brand"/>" method="post" >                 

                                        <div class="form-group">
                                          <label>品牌名字</label>
                                            <input class="form-control" name="brandname" value="">
                                           <p class="help-block">请输入要新增的品牌名字</p>
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
</tiles:insertDefinition>
