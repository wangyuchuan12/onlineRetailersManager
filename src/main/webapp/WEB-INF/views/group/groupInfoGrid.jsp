<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">组团详情</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">组团详情</h1>
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
                                	 <th>用户别昵</th>
                                    <th>用户名</th>
                                    <th>地址</th>
                                    <th>手机号码</th>
                                    <th>角色</th>
                                    <th>参团时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${partakes}" var="partake">
                            		<tr class="odd gradeX">
                            			<td>${partake.nickname}</td>
                            			<td>${partake.personName}</td>
                            			<td>${partake.address}</td>
                            			<td>${partake.phonenumber}</td>
                            			<td>
                            				<c:if test="${partake.role==1}">
                            					团长
                            				</c:if>
                            				<c:if test="${partake.role==2}">
                            					沙发
                            				</c:if>
                            				<c:if test="${partake.role==3}">
                            					普通参团
                            				</c:if>
                            			</td>
                            			<td><joda:format value="${partake.datetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            			<td></td>
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

}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
