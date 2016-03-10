<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">微纳遥控器后台管理系统</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">遥控器信息管理</h1>
        </div>
     
    </div>
    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            <div class="panel-heading">遥控器协议格式管理</div>
                <div class="panel-body">
                
                 
                    <div class="dataTable_wrapper">
                                <div id="dataTables-example_wrapper" class="dataTables_wrapper form-inline dt-bootstrap no-footer"><div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTables-example_length">
                                
                                </div></div><div class="col-sm-6"><div id="dataTables-example_filter" class="dataTables_filter">
                                	<label><button type="button" class="btn btn-info" onclick="javascript:window.location.href='<c:url value="/remote_info/code_add_view" />'" >新增</button></label>
                                	
                                	</div></div></div><div class="row"><div class="col-sm-12"><table class="table table-striped table-bordered table-hover dataTable no-footer" id="dataTables-example" role="grid" aria-describedby="dataTables-example_info">
                                    <thead>
                                        <tr role="row">
                                        	<th class="sorting_asc" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending" style="width: 293px;">id</th>
                                        	<th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 331px;">协议格式</th>
                                        	<th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 303px;">key</th>
                                        	<th class="sorting" tabindex="0" aria-controls="dataTables-example" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 256px;">操作</th>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${remoteCodes}" var="remoteCode" varStatus="">
                                    	<tr class="gradeA odd" role="row">
                                            <td class="sorting_1">${remoteCode.id}</td>
                                            <td>${remoteCode.displayName}</td>
                                            <td>${remoteCode.name}</td>
                                            <td class="center">
                                            	<a href="<c:url value="/remote_info/code_update_view?id=${remoteCode.id}"/>">修改</a>
									<a href="<c:url value="/remote_info/code_del_do?id=${remoteCode.id}" />">删除</a>
                                            
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    
                                        
                                   </tbody>
                                </table></div></div></div>
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
	 
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
