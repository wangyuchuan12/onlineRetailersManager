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
                   		<div class="col-sm-6">
											<div class="dataTables_length" id="dataTables-admin_length">
												<label>每页显示 <select name="dataTables-admin_length"
													aria-controls="dataTables-admin" class="input-sm">
														<option value="5">5</option>
														<option value="10">10</option>
														<option value="25">25</option>
														<option value="50">50</option>
														<option value="100">100</option>
												</select> 条记录
												</label>
											</div>
						</div>
						
						<div class="col-sm-6">
											<div class="dataTables_length">
												<label>状态 <select name="menu_table_status"
													aria-controls="dataTables-admin" class="input-sm">
														<option value="1">正在组团</option>
														<option value="0">组团失败</option>
														<option value="2">组团成功</option>
														<option value="3">组团超时</option>
														<option value="4">废弃</option>
														
												</select>
												</label>
											</div>
						</div>
                        <table class="table table-striped table-bordered table-hover" id="dataTables-admin">
                        			<input value="${totalElements}" name="totalElements"
										type="hidden" />
									<input value="${isFirst}" name="isFirst" type="hidden" />
									<input value="${totalPage}" name="totalPage" type="hidden" />
									<input value="${count}" name="count" type="hidden" />
									<input value="${page}" name="page" type="hidden" />
									<input value="${status}" name="status" type="hidden" />
									<input value="${groupId}" name="groupId" type="hidden" />
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
                            				<a href="javascript:flushTableByGroupId('${group.id}')">订单管理</a>
                            				<a href="/manager/partakes?group_id=${group.id}">查看组团情况</a>
                            		
                            				<a href="javascript:derelect('${group.id}');">废弃</a>
                            				
                            			</td>
                            		</tr>
                            		
                            	</c:forEach>
                            </tbody>
                        </table>
                        
                        <div class="row">
									<div class="col-sm-5">
										<div class="dataTables_info" id="dataTables-admin_info"
											role="status" aria-live="polite">
											显示第 ${count*(page-1)+1} 条到第 ${count*page}条记录,一共 ${totalElements}条记录
										</div>
									</div>
									<div class="col-sm-6">
										<div class="dataTables_paginate paging_simple_numbers"
											id="dataTables-admin_paginate">
											<ul class="pagination">
												<c:if test="${totalPage<=5}">
													<c:if test="${page!=1}">
														<li class="paginate_button previous"
															aria-controls="dataTables-admin" tabindex="0"
															id="dataTables-admin_previous"><a href="#"
															id="previousButton">上一页</a></li>
													</c:if>
														<c:forEach begin="1" end="${totalPage}" var="val">

																<li class="paginate_button "
																	aria-controls="dataTables-admin" tabindex="0"><a
																	href="javascript:flushTable(${val})">${val}</a></li>
				
														</c:forEach>
													<c:if test="${totalPage!=page}">
														<li class="paginate_button next"
															aria-controls="dataTables-admin" tabindex="0"
															id="dataTables-admin_next"><a href="#"
															id="nextButton">下一页</a></li>
													</c:if>
												</c:if>
												<c:if test="${totalPage>5}">
													<c:if test="${page!=1}">
														<li class="paginate_button previous"
															aria-controls="dataTables-admin" tabindex="0"
															id="dataTables-admin_previous"><a href="#"
															id="previousButton">上一页</a></li>
													</c:if>
													<c:if test="${totalPage>1}">
														<li class="paginate_button active"
															aria-controls="dataTables-admin" tabindex="0"><a
															href="javascript:flushTable(1)">1</a></li>
														<c:if test="${page>=5}">
															<li class="paginate_button active"
																aria-controls="dataTables-admin" tabindex="0"><a
																href="">...</a></li>
														</c:if>
													</c:if>
													<c:if test="${totalPage==1}">
														<li class="paginate_button active"
															aria-controls="dataTables-admin" tabindex="0"><a
															href="javascript:flushTable(1)">1</a></li>
													</c:if>
													<c:if test="${page<=4}">
														<c:forEach begin="2" end="5" var="val">
															<c:if test="${val!='1'&&val<totalPage}">
																<li class="paginate_button "
																	aria-controls="dataTables-admin" tabindex="0"><a
																	href="javascript:flushTable(${val})">${val}</a></li>
															</c:if>
														</c:forEach>
													</c:if>
													<c:if test="${page>4}">
														<c:forEach begin="${page-2}" end="${page+2}" var="val">
															<c:if test="${val<totalPage}">
																<li class="paginate_button "
																	aria-controls="dataTables-admin" tabindex="0"><a
																	href="javascript:flushTable(${val})">${val}</a></li>
															</c:if>
														</c:forEach>
													</c:if>

													<c:if test="${totalPage>1}">
														<c:if test="${(page+3)<totalPage}">
															<li class="paginate_button active"
																aria-controls="dataTables-admin" tabindex="0"><a
																href="">...</a></li>
														</c:if>
														<li class="paginate_button "
															aria-controls="dataTables-admin" tabindex="0"><a
															href="javascript:flushTable(${totalPage})">${totalPage}</a></li>
													</c:if>
													<c:if test="${totalPage!=page}">
														<li class="paginate_button next"
															aria-controls="dataTables-admin" tabindex="0"
															id="dataTables-admin_next"><a href="#"
															id="nextButton">下一页</a></li>
													</c:if>
												</c:if>
											</ul>
										</div>
									</div>
								</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    

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
                                    <th>订单类别</th>
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
                            		<tr class="odd gradeX" id ="${order.groupPartakeId}" >
                            			<td><img src="${order.goodHeadImgUrl}" style="width: 50px;height:50px;"></img></td>
                            			<td>${order.goodName}</td>
                            			<td>${order.goodTypeName}</td>
                            			<td>${order.type}</td>
                            			<td id = "pay_status_${order.groupPartakeId}">
                            				<c:if test="${order.payStatus==0}">未付款</c:if>
                            				<c:if test="${order.payStatus==1}">已付款</c:if>
                            				<c:if test="${order.payStatus==2}">申请退款</c:if>
                            				<c:if test="${order.payStatus==3}">已退款</c:if>
                            			
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
                            					<c:if test="${order.deliverStatus==0&&order.payStatus==1}">
                            						<a href="javascript:deviceClick('${order.groupPartakeId}')">发货</a>
                            					</c:if>
                            					<c:if test="${order.deliverStatus==2}">
                            						<br/>
                            						<a href="javascript:statementApply('${order.groupPartakeId}')">申请结算</a>
                            					</c:if>
                            					
                            				</c:if>
                            				
                            				<c:if test="${order.payStatus==1}">
                            					<br/>
                            					<a id="refund_a_${order.groupPartakeId}" href="javascript:refundPayment('${order.groupPartakeId}')">退款</a>
                            				</c:if>
                            				<br/>
                            				<c:if test="${order.deliverStatus==2||order.payStatus==3}">
                            					<a href="javascript:delOrder('${order.groupPartakeId}')">删除</a>
                            				</c:if>
                            				<shiro:hasRole name="god">
	                            				<c:if test="${order.status==2}">
	                            					<br/>
	                            					<a href="javascript:statementDo('${order.groupPartakeId}')">结算处理</a>
	                            				</c:if>
                            				</shiro:hasRole>
                            				<a href="/manager/chat_view?admin_id=${adminId}&customer_id=${order.customerId}">发送消息</a>
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


function refundPayment(groupPartakeId){
	var a=confirm("是否确定退款");
	if(a){
		$.ajax({
			url:"/manager/api/refund?group_partake_id="+groupPartakeId,
			success:function(resp){
				if(resp.id){
					alert("退款成功");
					$("#refund_a_"+groupPartakeId).remove();
					$("#pay_status_"+groupPartakeId).html("已退款");
				}
			}
		});
	}
}

function deviceClick(groupPartakeId){
	$(document).ready(function() {
		$("input[name=group_partake_id]").val(groupPartakeId);
		$('#deviceCommitId').slideDown(200);
	});
}

function flushTableByGroupId(groupId){
	var page = $("input[name=page]").val();
	flushTable(page,groupId);
}
function flushTable(page,groupId){
	 var total = $("input[name=totalPage]").val();
	 var groupId = $("input[name=groupId]").val();
	 if(parseInt(page)>parseInt(total))
	 {
		 page = total;
	 }
	 var menu = $("select[name=dataTables-admin_length]");
	 var menuTableStatus = $("select[name=menu_table_status]");
	 if(page==0){
		 page=1;
	 }
	 if(!groupId){
		 window.location.href="<c:url value='/manager/groups?status="+menuTableStatus.val()+"&size="+menu.val()+"&page="+page+"'/>"

	 }else{
		 window.location.href="<c:url value='/manager/groups?group_id="+groupId+"&status="+menuTableStatus.val()+"&size="+menu.val()+"&page="+page+"'/>"
	 }
}
function init(){
	var paginateDiv = $("#dataTables-admin_paginate");
	var hrefs = paginateDiv.find("a");
	var page = $("input[name=page]").val();
	hrefs.each(function(){
		if($(this).text()==page)
		{
			$(this).parent().addClass("active");
		}
		else
		{
			$(this).parent().removeClass("active");
		}
	});
}


function derelect(groupId){
	var page = $("input[name=page]").val();
	var total = $("input[name=totalPage]").val();
	 if(parseInt(page)>parseInt(total))
	 {
		 page = total;
	 }
	 var menu = $("select[name=dataTables-admin_length]");
	 var menuTableStatus = $("select[name=menu_table_status]");
	 if(page==0){
		 page=1;
	 }
	$(document).ready(function() {
		var a=confirm("是否真的废弃该团并该团所有的订单？");
		if(a){
			window.location.href = "<c:url value='/manager/derelect_group?group_id="+groupId+"&status="+menuTableStatus.val()+"&size="+menu.val()+"&page="+page+"'/>"
		}
	});
}

$(document).ready(function() {
	var totalElements = $("input[name=totalElements]").val();
	var isFirst = $("input[name=isFirst]").val();
	var totalPage = $("input[name=totalPage]").val();
	var count = $("input[name=count]").val();
	var menu = $("select[name=dataTables-admin_length]");
	var menuTableStatus = $("select[name=menu_table_status]");
	var page = $("input[name=page]").val();
	var status = $("input[name=status]").val();
	init();
	 var table = $('#dataTables-admin').dataTable({
		 	"iDisplayLength":count,
		 	"bPaginate":false,
		 	"aLengthMenu":false,
		 	"bSearchable":false,
		 	"bLengthChange":false,
		 	"bInfo":false,
		 	"bFilter":false
	        });
	 
	$("select[name=dataTables-admin_length]").find("option[value="+count+"]").attr("selected",true);
	$("select[name=menu_table_status]").find("option[value="+status+"]").attr("selected",true);
    var menu = $("select[name=dataTables-admin_length]");
    menuTableStatus.change(function(){
    	flushTable(page);
    });
    menu.change(function(){
    	flushTable(1);
    });
     var previousButton = $("#previousButton");
     var nextButton = $("#nextButton");
     previousButton.bind("click",function(){
    	var page = $("input[name=page]").val();
    	if(parseInt(page)>1)
        {
    		flushTable(parseInt(page)-1);
        }
     });
     nextButton.bind("click",function(){
    	 var page = $("input[name=page]").val();
    	 var totalPage=$("input[name=totalPage]").val();
    	 if(parseInt(page)<parseInt(totalPage))
         {
    		 flushTable(parseInt(page)+1);
         }
     });
	 var addButton = $("button[name=addButton]");
	    addButton.bind("click",function(){
	    	window.location="<c:url value='/admin/video/add'/>";
	     });
	
	
	/* var table = $('#dataTables-admin').dataTable({
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
	*/
});

</script>
</tiles:putAttribute>
</tiles:insertDefinition>
