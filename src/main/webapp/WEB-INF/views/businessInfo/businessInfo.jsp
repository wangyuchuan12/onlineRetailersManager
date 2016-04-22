<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">商家</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">我的信息</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<form role="form"
								action="<c:url value="/manager/update_business_info"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
				<div class="panel panel-default">
	                     <div class="panel-heading">
	                        	 个人信息详情
	                     </div>
	                     <div class="panel-body">
	                         <div class="form-group input-group">
	                              <span class="input-group-addon">姓名</span>
	                              <input type="text" class="form-control" name ="realname" value="${admin.realname}" readonly="readonly">
	                         </div>
	                        
	                         
	                         <div class="form-group input-group">
	                              <span class="input-group-addon">手机号码</span>
	                              <input type="text" class="form-control" name="mobile" value="${admin.mobile}" readonly="readonly">
	                         </div>
	                         
	                         <div class="form-group input-group">
	                              <span class="input-group-addon">邮箱地址</span>
	                              <input type="text" class="form-control" name="email" value="${admin.email}" readonly="readonly">
	                         </div>
	                     </div>
	                     <div class="panel-heading">
	                        	 商家信息
	                     </div>
	                     <div class="panel-body">
		                     <div class="form-group">
		                           <div>
		                           	<img alt="" src="${business.imgUrl}" style="width:200px;height:200px;">
		                           </div>
		                           <input type="file" style="display: none;" name="img">
		                     </div>
		                     <div class="form-group input-group">
		                              <span class="input-group-addon">商家名称</span>
		                              <input type="text" class="form-control" name="business_name" value="${business.name}" readonly="readonly">
		                     </div>
		                     
		                     <div class="form-group input-group">
		                              <span class="input-group-addon">介绍</span>
		                              <textarea class="form-control" name="business_instruction" readonly="readonly" >${business.instruction}</textarea>
		                     </div>
		                     
		                     <div class="form-group input-group">
		                              <span class="input-group-addon">账户余额</span>
		                              <input type="text" class="form-control"  value="${business.account}" readonly="readonly" disabled="disabled" name="account">
		                     </div>
		                     
		                     <div class="panel-footer">
							<button type="button" class="btn btn-default" id="edit_button">修改信息</button>
							<button type="submit" class="btn btn-success disabled" id="save_button">保存</button>
							<button type="button" class="btn btn-default" id="expressive_button">提现</button>
		                     </div>
	                     </div>
		          </div>
	          </form>
		</div>
	</div>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>

	function addSubmit(){
		return true;
	}
	$(document).ready(function() {
		$("#edit_button").on("click",function(){
			$("input[name='realname']").removeAttr("readonly");
			$("input[name='mobile']").removeAttr("readonly");
			$("input[name='email']").removeAttr("readonly");
			$("input[name='business_name']").removeAttr("readonly");
			$("textarea[name='business_instruction']").removeAttr("readonly");
			$("#edit_button").addClass("disabled");
			$("#save_button").removeClass("disabled");
			
			$("input[name='img']").css("display","block");
		});
		
		$("#expressive_button").on("click",function(){
			var account = $("input[name='account']").val();
			if(account<500){
				alert("对不起，余额少于500块，不能提现");
			}else{
				alert("对不起，现在系统还不支持提现功能，请联系13738139702王先生，手动转账给你");
			}
			
		});
	});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>