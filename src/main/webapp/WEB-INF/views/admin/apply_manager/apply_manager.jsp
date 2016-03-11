<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">遥控器管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">申请管理</h1>
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
                                    <th>名称</th>
                                    <th>城市</th>
                                    <th>运营商</th>
                                    <th>类型</th>
                                    <th>格式</th>
                                    <th>协议</th>
                                    <th>编码</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${remotes}" var="remote" varStatus="">
                                <tr class="odd gradeX">
                                    <td>${remote.id}</td>
                                    <td>${remote.name}</td>
                                    <td>${remote.city}</td>
                                    <td>${remote.serviceProvider}</td>
                                    <c:if test="${remote.type==0}">
                                    	<td>风扇</td>
                                    </c:if>
                                    <c:if test="${remote.type==1}">
                                    	<td>空调</td>
                                    </c:if>
                                    <c:if test="${remote.type==2}">
                                    	<td>机顶盒</td>
                                    </c:if>
                                    <c:if test="${remote.type==3}">
                                    	<td>电视机</td>
                                    </c:if>
                                    
                                    <c:if test="${remote.type==4}">
                                    	<td>网络盒子</td>
                                    </c:if>
                                    
                                    
                                    <td>${remote.dataType}</td>
                                    <td>${remote.prot}</td>
                                     <td>${remote.codec}</td>
                                     <td>
                                    	<c:if test="${remote.status==0}">
                                    		未申请
                                    	</c:if>
                                    	<c:if test="${remote.status==1}">
                                    		申请中
                                    	</c:if>
                                    	<c:if test="${remote.status==2}">
                                    		上线
                                    	</c:if>
                                    	<c:if test="${remote.status==3}">
                                    		拒绝
                                    	</c:if>
                                    </td>
                                    <td class="center">                                   
                                       <a href="javascript:showHandler(2,${remote.id});">上线</a>                       
                                       <a href="javascript:showHandler(3,${remote.id});">驳回</a>                                                             
                                       <a  href="<c:url value="/apply_manager/list_view?edit_remote_id=${remote.id}"/>">详情</a>
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
	<div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            <div class="panel-heading">${edit_remote.name}</div>
                <div class="panel-body">
                
                 
                    <div class="table-responsive">
                   <div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTables-example_length">
                                
                                </div></div><div class="col-sm-6"><div id="dataTables-example_filter" class="dataTables_filter">
                                	</div></div></div>
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                        	
                            <thead>
                                <tr>
                                    <th>按键名称</th>
                                    <th>key</th>
                                    <th>systemCode</th>
                                    <th>dataCode</th>
                                    <th>data</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${remoteKeys}" var="remoteKey">
                                <tr class="odd gradeX">
                                    <td>${remoteKey.displayName}</td>
                                    <td>${remoteKey.remoteKey}</td>
                                    <td>${remoteKey.systemCode}</td>
                                    <td>${remoteKey.dataCode}</td>
                                    <td><textarea style="width:100%;height:25px;" disabled="disabled">${remoteKey.data}</textarea></td>
                                    
                                    <td class="center">                       
                                       <a onclick="return delConfirm();" href="<c:url value="/apply_manager/del_remote_key?id=${remoteKey.id}&remote_id=${edit_remote_id}"/>">删除</a>                                                             
                                       <a href="javascript:showAddRemoteKey('${remoteKey.id}','${remoteKey.remoteKey}','${remoteKey.data}','${remoteKey.systemCode}','${remoteKey.dataCode}');">修改</a> 
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
    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            <div class="panel-heading">${edit_remote.name}-申请详情</div>
                <div class="panel-body">
                
                 
                    <div class="table-responsive">
                   <div class="row"><div class="col-sm-6"><div class="dataTables_length" id="dataTables-example_length">
                                
                                </div></div><div class="col-sm-6"><div id="dataTables-example_filter" class="dataTables_filter">
                                	
                                	
                                	</div></div></div>
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                        	
                            <thead>
                                <tr>
                                    <th>id</th>
                                    <th>类型</th>
                                    <th>备注</th>
                                    <th>操作时间</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${handlerRecords}" var="handlerRecord">
	                                <tr class="odd gradeX">
	                                    <td>${handlerRecord.id}</td>
	                                    <td>
	                                    	<c:if test="${handlerRecord.type==0}">
	                                    		新增
	                                    	</c:if>
	                                    	
	                                    	<c:if test="${handlerRecord.type==1}">
	                                    		申请
	                                    	</c:if>
	                                    	
	                                    	<c:if test="${handlerRecord.type==2}">
	                                    		同意
	                                    	</c:if>
	                                    	
	                                    	<c:if test="${handlerRecord.type==3}">
	                                    		驳回
	                                    	</c:if>
	                                    </td>
	                                    <td>${handlerRecord.remark}</td>
	                                    <td><joda:format value="${handlerRecord.handleTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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

<div class="modal" id="mymodal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title">修改按键</h4>
						</div>
						<form role="form"
								action="<c:url value="/apply_manager/update_remote_key?remote_id=${edit_remote_id}"/>" method="post"
								onsubmit="return addSubmit();">
							<div class="modal-body">
								<input value="" id="remoteKeyId" name="remoteKeyId" hidden="true">
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
										<label>按键</label> 
										<select class="form-control" type="text" name="key_name" id="updateSelect">
											<c:forEach items="${allRemoteKeys}" var="remoteKey" varStatus="">
												<option value="${remoteKey.name}">${remoteKey.displayName}</option>
											</c:forEach>
										</select>
										
										<p class="help-block">遥控器按键</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>system_code</label> <input class="form-control" type="text" name="system_code" value="${remoteKey.systemCode}" id="systemCode">
									<p class="help-block">系统码</p>
								</div>
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
									<label>data_code</label> <input class="form-control" type="text" name="data_code" value="${remoteKey.dataCode}" id="dataCode">
									<p class="help-block">数据码</p>
								</div>
								
								<div class="form-group" name="affirm_passwordDiv" id="device_name_div">
										<label>data</label> <textarea class="form-control"  cols="20" rows="5" name="data" id="key_data"></textarea>
										<p class="help-block">数据</p>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="submit" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			
			
			
			
			
			<div class="modal" id="pass">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">通过申请</h4>
					</div>
					<form role="form"
						action="<c:url value="/apply_manager/handler?status=2"/>"
						method="post" onsubmit="return addSubmit();">
						<input name="remote_id" id="pass_remote_id" hidden="true">
						<div class="modal-body">
							<div class="form-group" name="affirm_passwordDiv"
								id="device_name_div">
								<label>备注</label>
								<textarea class="form-control" cols="20" rows="5" name="remark"></textarea>
								<p class="help-block">填写备注信息</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		
		
		
		<div class="modal" id="reject">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">驳回申请</h4>
					</div>
					<form role="form"
						action="<c:url value="/apply_manager/handler?status=3"/>"
						method="post" onsubmit="return addSubmit();">
						<input name="remote_id" id="reject_remote_id" hidden="true">
						<div class="modal-body">
							<div class="form-group" name="affirm_passwordDiv"
								id="device_name_div">
								<label>备注</label>
								<textarea class="form-control" cols="20" rows="5" name="remark"></textarea>
								<p class="help-block">填写备注信息</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>
function addSubmit(){
	return true;
}

function showHandler(type,remoteId){
	if(type==2){
		$("#pass_remote_id").val(remoteId);
		$("#pass").modal();
	}else if(type==3){
		$("#reject_remote_id").val(remoteId);
		$("#reject").modal();
	}
}


function delConfirm(content){
	if(!content){
		return confirm("是否删除");
	}else{
		return confirm(content);
	}
	
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
	 var addButton = $("button[name=addButton]");
	    addButton.bind("click",function(){
	    	window.location="<c:url value='/remote_manager/add_remote_view'/>";
	     });
});

function showAddRemoteKey(keyId, remoteKey , data,systemCode,dataCode){
	var mymodel = $("#mymodal");
	mymodel.modal();
	$("#updateSelect").val(remoteKey);
	$("#key_data").val(data);
	$("#remoteKeyId").val(keyId);
	$("#systemCode").val(systemCode);
	$("#dataCode").val(dataCode);
	
}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
