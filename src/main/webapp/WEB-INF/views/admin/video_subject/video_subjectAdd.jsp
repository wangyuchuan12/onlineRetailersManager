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
					<h1 class="page-header">视频明细管理</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							视频明细新增
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form action="<c:url value="/admin/vedio_subject/do_add"/>" method="post" ;">				
										<div class="form-group">
                                          <label>视频名字</label>
                                            <input class="form-control" name="title" value="">
                                           <p class="help-block">请输入视频名字</p>
                                       </div>
                                       <div class="form-group">
                                          <label>视频封面</label>
                                            <input class="form-control" name="cover" value="">
                                           <p class="help-block">请输入视频封面</p>
                                       </div>
                                       <div class="form-group">
                                          <label>视频简介</label>
                                            <input class="form-control" name="description" value="">
                                           <p class="help-block">请输入视频简介</p>
                                       </div>
                                       <div class="form-group">
                                          <label>视频资源</label>
                                            <input class="form-control" name="source" value="">
                                           <p class="help-block">请输入视频资源</p>
                                       </div>
                                       <div class="form-group">
                                          <label>视频成员</label>
                                            <input class="form-control" name="matedata" value="">
                                           <p class="help-block">请输入视频成员</p>
                                       </div>
                                       <div class="form-group">
                                          <label>视频标识</label>
                                            <input class="form-control" name="tags" value="">
                                           <p class="help-block">请输入视频表识</p>
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
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
