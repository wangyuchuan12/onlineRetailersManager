<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">订单管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="theme-popover" id="deviceCommitId">
	     <div class="theme-poptit">
	          <a href="javascript:;" title="关闭" class="close">×</a>
	          <h3>发货</h3>
	     </div>
	     <div class="theme-popbod dform">
	           <form class="theme-signin" name="loginform" action="/manager/device_handler" method="post">
	           	<input type="hidden" name="group_partake_id">
	                <ol>
	                     <li><h4>填写发货信息</h4></li>
	                     <li><strong>发货单号</strong><input class="ipt" type="text" name="logistics_no" value="" size="20" /></li>
	                     <li><strong>物流公司</strong>
	                     	<select class="form-control" name="com">
	                                	<c:forEach items="${companys}" var="company">
	                                		<option  value="${company.code}">${company.name}</option>
	                                	</c:forEach>
			               </select>
	                     
	                     </li>
	                     <li><strong>发货时间</strong><input class="ipt" type="text" name="device_time" value="" size="20" id="datetimepicker"/></li>
	                     <li><strong>备注</strong><input class="ipt" type="text" name="remarks" value="" size="20" /></li>
	                     
	                     <li><input class="btn btn-primary" type="submit" name="submit" value="确定" /></li>
	                </ol>
	           </form>
	     </div>
	</div>




    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">订单管理</h1>
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
                                    <th width="10%">商品名称</th>
                                    <th>类别</th>
                                    <th>支付状态</th>
                                    <th>支付时间</th>
                                    <th>退款金额</th>
                                    <th>退款时间</th>
                                    <th>发货状态</th>
                                    <th>发货时间</th>
                                    <th width="15%">发货地点</th>
                                    <th>支付金额</th>
                                    <th>订单状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${orders}" var="order">
                            		<tr class="odd gradeX">
                            			<td><img src="${order.goodHeadImgUrl}" style="width: 50px;height:50px;"></img></td>
                            			<td>${order.goodName}</td>
                            			<td>${order.goodTypeName}</td>
                            			<td>
                            				<c:if test="${order.payStatus==0}">未付款</c:if>
                            				<c:if test="${order.payStatus==1}">已付款</c:if>
                            				<c:if test="${order.payStatus==2}">已退款</c:if>
                            			
                            			</td>
                            			<td><joda:format value="${order.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            			<td>${order.refundAmount}</td>
                            			<td><joda:format value="${order.refundTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
                            			<td>
                            				<c:if test="${order.deliverStatus==0}">未发货</c:if>
                            				<c:if test="${order.deliverStatus==1}">已发货，未签收</c:if>
                            				<c:if test="${order.deliverStatus==2}">已签收</c:if>
                            			</td>
                            			<td><joda:format value="${order.deliverTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            			
                            			<td>${order.address}</td>
                            			<td>${order.cost}</td>
                            			<td>
                            				<c:if test="${order.status==0}">游离</c:if>
                            				<c:if test="${order.status==1}">待结算</c:if>
                            				<c:if test="${order.status==2}">结算中</c:if>
                            				<c:if test="${order.status==3}">结算成功</c:if>
                            				<c:if test="${order.status==4}"><font color="red">废弃</font></c:if>
                            			
                            			</td>
                            			<td class="center">
                            				<c:if test="${order.status==1}">
                            					<c:if test="${order.deliverStatus==0}">
                            						<a href="javascript:deviceClick('${order.groupPartakeId}')">发货</a>
                            					</c:if>
                            					<c:if test="${order.deliverStatus==2}">
                            						<a href="javascript:statementApply('${order.groupPartakeId}')">申请结算</a>
                            					</c:if>
                            					
                            				</c:if>
                            				<shiro:hasRole name="god">
	                            				<c:if test="${order.status==2}">
	                            					<a href="javascript:statementDo('${order.groupPartakeId}')">结算处理</a>
	                            				</c:if>
                            				</shiro:hasRole>
                            				<!--  
                            				<a href="#">订单详情</a>
                            				<a href="#">商品详情</a>
                            				<a href="#">客户详情</a>
                            				-->
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
	    	window.location="<c:url value='/manager/good_add_view'/>";
	     });
	    
	$('.theme-poptit .close').click(function(){
		$('#deviceCommitId').slideUp(200);
	});
	$('#datetimepicker').datetimepicker({
        lang:"ch",           //语言选择中文
        format:"Y-m-d h:i:s",      //格式化日期
        timepicker:true,    //关闭时间选项
        yearStart:2000,     //设置最小年份
        yearEnd:2050,        //设置最大年份
        todayButton:true    //关闭选择今天按钮
  	});
});

function statementApply(groupPartakeId){
	var a=confirm("是否确定申请结算");
	if(a){
		var remark = prompt("请输入备注");
		window.location.href = "/manager/apply_settlement?group_partake_id="+groupPartakeId+"&remark="+remark;
	}
}

function statementDo(groupPartakeId){
	var a=confirm("是否确定结算");
	if(a){
		var remark = prompt("请输入备注");
		window.location.href = "/manager/do_settlement?group_partake_id="+groupPartakeId+"&remark="+remark;
	}
}

function deviceClick(groupPartakeId){
	$(document).ready(function() {
		$("input[name=group_partake_id]").val(groupPartakeId);
		$('#deviceCommitId').slideDown(200);
	});
}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
