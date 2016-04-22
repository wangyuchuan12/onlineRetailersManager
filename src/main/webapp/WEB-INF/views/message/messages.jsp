<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">消息管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">消息管理</h1>
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
                                	 <th>头像</th>
                                    <th>微信名称</th>
                                    <th>总信息</th>
                                    <th>发送信息</th>
                                    <th>接收信息</th>
                                    <th>未读信息</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dialogSessions}" var="dialogSession">
                            		<tr class="odd gradeX">
                            			<td><img src="${dialogSession.headimgurl}" style="width: 50px;height:50px;"></img></td>
                            			<td>${dialogSession.nickname}</td>
                            			<td>${dialogSession.receiveCount+dialogSession.sendCount}</td>
                            			<td>${dialogSession.sendCount}</td>
                            			<td>${dialogSession.receiveCount}</td>
                            			<td>${dialogSession.receiveCount+dialogSession.sendCount-dialogSession.readCount}</td>
                            			<td class="center">
                            				<a href="/manager/chat_view?admin_id=${dialogSession.adminId}&customer_id=${dialogSession.customerId}">聊天</a>
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
