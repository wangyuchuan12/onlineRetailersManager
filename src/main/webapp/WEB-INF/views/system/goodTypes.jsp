<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">商品类别管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">商品类别管理</h1>
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
                                    <th>名称</th>
                                    <th>标题</th>
                                    <th>是否默认</th>
                                    <th>是否展示</th>
                                    <th>创建日期</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${goodTypes}" var="goodType">
                            		<tr class="odd gradeX">
                            			<td>${goodType.id}</td>
                            			<td><img src="${goodType.img}" style="width: 50px;height:50px;"></img></td>
                            			<td>${goodType.name}</td>
                            			<td>${goodType.title}</td>
                            			<td>
	                            			<c:if test="${goodType.isDefault}">
	                            				是
	                            			</c:if>
                            			</td>
                            			
                            			<td>
	                            			<c:if test="${goodType.isDisplay==1}">
	                            				是
	                            			</c:if>
                            			</td>
                            			<td><joda:format value="${goodType.createAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                            			<td>
                            				<a href="<c:url value="/manager/good_type_update_view?id=${goodType.id}"/>">修改</a>
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
	    	
	            "sSearch": "<button name='addButton' style='color:green ; width:100px;height:28px' >新增</button>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp搜索:",

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
	 var addButton = $("button[name=addButton]");
	 addButton.bind("click",function(){
	    	window.location="<c:url value='/manager/good_type_add_view'/>";
	 });
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
