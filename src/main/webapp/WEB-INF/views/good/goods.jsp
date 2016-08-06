<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">商品管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">商品管理</h1>
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
                                	 <th>图片</th>
                                	 <th>id</th>
                                    <th width="20%">名称</th>
                                    <th width="30%">标题</th>
                                    <th>优先级</th>
                                    <th>组团时长</th>
                                    <th>类别</th>
                                    <th>展现</th>
                                    <th>库存</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${goods}" var="good">
                            		<tr class="odd gradeX">
                            			<td><img src="${good.headImgUrl}" style="width: 50px;height:50px;"></img></td>
                            			<td>${good.id}</td>
                            			<td>${good.name}</td>
                            			<td>${good.title}</td>
                            			<td>${good.rank}</td>
                            			<td>${good.timeLong}</td>
                            			<td>${good.goodTypeName}</td>
                            			<c:if test="${good.isDisplayMain==true}">
                            				<td>是</td>
                            			</c:if>
                            			<c:if test="${good.isDisplayMain==false}">
                            				<td>否</td>
                            			</c:if>
                            			
                            			<td>${good.stock}</td>
                            			<td class="center">
                            				<a href="/manager/good_del_do?id=${good.id}" style="display: none;">删除</a>
                            				<a href="/manager/good_update_view?id=${good.id}">修改</a>
                            				<a href="/manager/good_imgs?good_id=${good.id}">图片管理</a>
                            			</td>
                            		</tr>
                            		
                            	</c:forEach>
                                <!--  <tr class="odd gradeX">
                                    <td>${admin.username}</td>
                                    <td>${admin.realname}</td>
                                    <td>${admin.mobile}</td>
                                    <td class="center"><joda:format value="${admin.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td> 
                                    <td class="center"><joda:format value="${admin.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td class="center">                                   
                                       <a href="<c:url value="/admin/update?username=${admin.username}"/>">修改</a>                       
                                       <a href="<c:url value="/admin/delete?username=${admin.username}"/>">删除</a>                                                             
                                       <a href="<c:url value="/admin/reset?username=${admin.username}"/>">重置密码</a>
                               
                                    </td>
                                </tr>
                               -->
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
	    	window.location="<c:url value='/manager/good_add_view'/>";
	     });
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
