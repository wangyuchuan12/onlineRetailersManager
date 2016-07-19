<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">组团管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">组团管理</h1>
        </div>
     
    </div>
    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            
                <div class="panel-body">
                
                 
                    <div class="table-responsive">
                   
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                            <thead>
                                <tr>
                                	<th>id</th> 
                                	<th>图片</th>
                                    <th width="10%">商品名称</th>
                                    <th>类别</th>
                                    <th>组团状态</th>
                                    <th>价格</th>
                                    <th>组团数</th>
                                    <th>已组团</th>
                                    <th>开始时间</th>
                                    <th>组团时长</th>
                                    <th>团长</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${groups}" var="group">
                            		<tr class="odd gradeX">
                            			<td>${group.id}</td>
                            			<td><img src="${group.goodHeadImgUrl}" style="width: 50px;height:50px;"></img></td>
                            			<td>${group.goodName}</td>
                            			<td>${group.goodTypeName}</td>
                            			<td>
                            				<c:if test="${group.result==0}">
                            					组团失败
                            				</c:if>
                            				<c:if test="${group.result==1}">
                            					正在组团
                            				</c:if>
                            				<c:if test="${group.result==2}">
                            					组团成功
                            				</c:if>
                            				<c:if test="${group.result==3}">
                            					组团超时
                            				</c:if>
                            				
                            				<c:if test="${group.result==4}">
                            					废弃
                            				</c:if>
                            			</td>
                            			<td>${group.totalPrice}</td>
                            			<td>${group.num}</td>
                            			<td>${group.groupCount}</td>
                            			<td><joda:format value="${group.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            			<td>${group.timeLong}</td>
                            			<td>${group.groupHeadUserName}</td>
                            			<td>
                            				<a href="/manager/orders_by_group_id?group_id=${group.id}">订单管理</a>
                            				<a href="/manager/partakes?group_id=${group.id}">查看组团情况</a>
                            				<!--  
                            				<a href="javascript:derelect('${group.id}');">废弃</a>
                            				-->
                            			</td>
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

function derelect(groupId){
	$(document).ready(function() {
		var a=confirm("是否真的废弃该团并该团所有的订单？");
		if(a){
			window.location.href = "/manager/derelect_orders_by_group_id?group_id="+groupId;
		}
	});
}

$(document).ready(function() {
	 var table = $('#dataTables-admin').dataTable({
	    	"oLanguage": {
	    	
	            "sSearch": "搜索:",

	            "sLengthMenu": "每页显示 _MENU_ 条记录",
	                "sZeroRecords": "Nothing found - 没有记录",
	            "sInfo": "显示第  _START_ 条到第  _END_ 条记录,一共  _TOTAL_ 条记录",
	            "sInfoEmpty": "显示0条记录",
	            "oPaginate": {
	                "sPrevious": " 上一页 ",
	                "sNext":     " 下一页 "
	                }
	            }
	        });
	
});

</script>
</tiles:putAttribute>
</tiles:insertDefinition>
