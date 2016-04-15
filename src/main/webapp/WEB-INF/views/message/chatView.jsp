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
                
                 	  <div id="convo" data-from="Sonu Joshi">  
					<ul class="chat-thread">
						<c:forEach items="${dialogSessionItems}" var="dialogSessionItem">
							<c:if test="${dialogSessionItem.role==1}">
								<li class="customer">
									<b style="font-weight: normal;">${dialogSessionItem.content}</b>
									
									<b class="">
										<c:if test="${dialogSessionItem.type==0}">
											消息类型：商家
										</c:if>
										
										<c:if test="${dialogSessionItem.type==1}">
											消息类型：商品
										</c:if>
										
										<c:if test="${dialogSessionItem.type==2}">
											消息类型：订单
										</c:if>
									</b>
									<b><joda:format value="${dialogSessionItem.dateTime}" pattern="yyyy-MM-dd HH:mm"/></b>
								</li>
							</c:if>
							
							<c:if test="${dialogSessionItem.role==0}">
								<li class="admin">
									<b style="font-weight: normal;">${dialogSessionItem.content}</b>
								</li>
								
								<li class="admin">
									<b style="font-weight: normal;">${dialogSessionItem.dateTime}</b>
								</li>
							</c:if>
						</c:forEach>
						
					</ul>
				 </div>
                </div>
                
                 <div class="form-group" style="width:80%;display:inline;">
                                <textarea id="messageText" class="form-control" rows="2" style="width:70%;display:inline;"></textarea>
                 </div>
                 
                 <button onclick="javascript:sendMessage();" type="submit" class="btn btn-default" style="float: right;position: relative;right:20%;display:inline;">发送数据</button>
            </div>
        </div>
    </div>
</div>
	<style type="text/css">
		.chat-thread .admin:before {
		     right: -80px;
	  		background-image: url(${business.imgUrl});
	  		background-size:cover;
		}
		.chat-thread  .customer:before {
	  		left: -80px;
	  		background-image: url(${userInfo.headimgurl});
			background-size:cover;
		}
	</style>
</tiles:putAttribute>

<tiles:putAttribute name="footerJavascript">
<script>
function sendMessage(){
	var message = $("#messageText").val();
	 if(message){
		 var htmlobj=$.ajax({url:"/api/chat/send_message?customer_id=${customerId}&content="+message,async:false});
		 htmlobj = eval("("+htmlobj.responseText+")");
		 var dialogDiv = $("<li class='admin'><b style='font-weight: normal;'>"+htmlobj.content+"</b></li>");
		 $(".chat-thread").append(dialogDiv);
		 $(".chat-thread").animate({scrollTop: 100000}, 300);
		 $("#messageText").val("");
	 }
}

function showNotRead(){
	var responseObj=$.ajax({url:"/api/chat/select_not_read?customer_id=${customerId}",async:false});
	 if(responseObj){
		 var htmlObj = eval("("+responseObj.responseText+")");
		 if(htmlObj){
			 for(var i =0 ;i<htmlObj.notReadCount;i++){
				 var notReadItem = htmlObj.notReadItems[i];
				 var dialogDiv = $("<li></li>");
				 if(notReadItem.role==1){
						dialogDiv.addClass("customer");
				 }else{
						dialogDiv.addClass("admin");
				 }
					
				 var timeDiv = $("<b>"+notReadItem.dateTime+"</b>");
				 var chatContentSessionDiv = $("<b>"+notReadItem.content+"</b>");
				 
				
				 dialogDiv.append(chatContentSessionDiv);
				 dialogDiv.append(timeDiv);
				 $(".chat-thread").append(dialogDiv);
				 $(".chat-thread").animate({scrollTop: 100000}, 300);
			 }
		 }
	 }
}
$(document).ready(function() {
	$(".chat-thread").animate({scrollTop: 100000}, 300);
	setInterval("showNotRead()",1000);
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
