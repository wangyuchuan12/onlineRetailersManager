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
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>

function flushTable(page){
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
	 window.location.href="<c:url value='/manager/groups?status="+menuTableStatus.val()+"&size="+menu.val()+"&page="+page+"'/>"
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
	$(document).ready(function() {
		var a=confirm("是否真的废弃该团并该团所有的订单？");
		if(a){
			window.location.href = "/manager/derelect_orders_by_group_id?group_id="+groupId;
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
