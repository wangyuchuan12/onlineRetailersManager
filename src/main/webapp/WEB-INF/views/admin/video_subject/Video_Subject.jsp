<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<tiles:insertDefinition name="adminLayout">
	<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
	<tiles:putAttribute name="title">视频明细管理</tiles:putAttribute>
	<style type="text/css">
	tbody{
	text-align: center;
            font-family: Verdana, Geneva, Arial, Helvetica, sans-serif ;
            font-weight: normal;
            font-size: 11px;
            color: #fff;
            background-color: #666;
            border: 0px;
            border-collapse: collapse;
            border-spacing: 0px
            
	}
	tbody td{
	
	        background-color: #666;
            color: #fff;
            padding: 4px;
            text-align: left;
            border-bottom: 2px #fff solid;
            font-size: 12px;
            font-weight: bold;
	}
	</style>
	<tiles:putAttribute name="body">
		<!-- Page Content -->
		<div id="page-wrapper">

			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">视频明细管理</h1>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">

						<div class="panel-body">


							<div class="table-responsive">

								<table class="table table-striped table-bordered table-hover"
									id="dataTables-admin">

									<input value="${totalElements}" name="totalElements"
										type="hidden" />
									<input value="${isFirst}" name="isFirst" type="hidden" />
									<input value="${totalPage}" name="totalPage" type="hidden" />
									<input value="${count}" name="count" type="hidden" />
									<input value="${page}" name="page" type="hidden" />
									<div class="row">
										<div class="col-sm-6">
											<div class="dataTables_length" id="dataTables-admin_length">
												<label>每页显示 <select name="dataTables-admin_length"
													aria-controls="dataTables-admin" class="input-sm">
														<option value="10">10</option>
														<option value="25">25</option>
														<option value="50">50</option>
														<option value="100">100</option>
												</select> 条记录
												</label>
											</div>
										</div>
										<div class="col-sm-6">
											<div id="dataTables-admin_filter" class="dataTables_filter">
												<label><button name="addButton"
														style="color: green; width: 100px; height: 28px">新增</button></label>
											</div>
										</div>
									</div>
									<thead>
										<tr>
											 <th>视频名字</th>
											
											 
											 <th>视频资源</th>
											 <th>视频主要成员</th>
											 <th>视频标签</th>
											 <th>创建时间</th>
											 <th>更新时间</th>
											 <th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vedio_subject}" var="vedio_subject" varStatus="">
											<tr >
												<td>${vedio_subject.title}</td>
												
												<td>${vedio_subject.sourceName}</td>
												<td>${vedio_subject.matedata}</td>
												<td>${vedio_subject.tags}</td>
												<td><joda:format value="${vedio_subject.createdat}"
														pattern="yyyy-MM-dd HH:mm" /></td>
												<td><joda:format value="${vedio_subject.updatedat}"
														pattern="yyyy-MM-dd HH:mm" /></td>
												<td class="center"><a
													href="<c:url value="/admin/video_subject/update?video_subjectid=${vedio_subject.id}"/>">详情+修改</a>
													<a
													href="<c:url value="/admin/video_subject/delete?vedio_subjectid=${vedio_subject.id}" />">删除</a>
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
											<c:if test="${page>1}">
												<li class="paginate_button previous"
													aria-controls="dataTables-admin" tabindex="0"
													id="dataTables-admin_previous"><a href="#"><button
															id="previousButton">上一页</button> </a></li>
									        </c:if>
													<li class="paginate_button active"
														aria-controls="dataTables-admin" tabindex="0"><a
														href="javascript:flushTable(1)">1</a></li>
												<c:if test="${totalPage>4}">
													<li class="paginate_button active"
														aria-controls="dataTables-admin" tabindex="0"><a
														href="">...</a></li>
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
												
                                              <c:if test="${totalPage>5}">
													<li class="paginate_button active"
														aria-controls="dataTables-admin" tabindex="0"><a
														href="">...</a></li>
											  </c:if>
											  <c:if test="${totalPage>1}">
													<li class="paginate_button "
														aria-controls="dataTables-admin" tabindex="0"><a
														href="javascript:flushTable(${totalPage})">${totalPage}</a></li>
							                  </c:if>
												<li class="paginate_button next"
													aria-controls="dataTables-admin" tabindex="0"
													id="dataTables-admin_next"><a href="#"><button
															id="nextButton">下一页</button> </a></li>
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
	 window.location.href="<c:url value='/admin/vedio_subject/list_page?size="+menu.val()+"&page="+page+"'/>"
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
$(document).ready(function() {
	var totalElements = $("input[name=totalElements]").val();
	var isFirst = $("input[name=isFirst]").val();
	var totalPage = $("input[name=totalPage]").val();
	var count = $("input[name=count]").val();
	var menu = $("select[name=dataTables-admin_length]");
	var page = $("input[name=page]").val();
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
	 
	$("select").find("option[value="+count+"]").attr("selected",true);
    var menu = $("select[name=dataTables-admin_length]");
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
	    	window.location="<c:url value='/admin/video_subject/add'/>";
	     });
});
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
