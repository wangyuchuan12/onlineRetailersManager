<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="dashboard_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">活动管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">活动管理</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">新增活动类管</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/manager/activity_add_do"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								<div class="form-group">
                    					<label>活动商品</label>
                    					<select class="form-control" name="activity_good" >
			                                	<c:forEach items="${goods}" var="good">
			                                		<option value="${good.id}">${good.name}</option>
			                                	</c:forEach>
			                                </select>
                    					<p class="help-block">活动商品</p>
                					</div>
                					
                					<div class="form-group">
                    					<label>活动方式</label>
                    					<select class="form-control" name="pay_handler" >
			                                	<c:forEach items="${handlers}" var="handler">
			                                		<option value="${handler.id}">${handler.name}</option>
			                                	</c:forEach>
			                                </select>
                    					<p class="help-block">活动方式</p>
                					</div>
                					
                					<div class="form-group">
                    					<label>购买方式</label>
                    					<select class="form-control" name="payType" >	
			                                	<option value="3">组团</option>
			                                </select>
                    					<p class="help-block">活动方式，表示用什么购买该活动商品才赠送开团劵</p>
                					</div>
                					
                					
                					<div class="form-group">
                    					<label>赠送开团劵商品</label>
                    					<select class="form-control" name="give_good" >
			                                	<c:forEach items="${goods}" var="good">
			                                		<option value="${good.id}">${good.name}</option>
			                                	</c:forEach>
			                                </select>
                    					<p class="help-block">活动商品</p>
                					</div>
                					
                					<div class="form-group">
                    					<label>开团劵有效时间</label>
                    					<input class="form-control"  name="time_long" value="${good.rank}" type="number" onkeyup="checkNum(this,1,0,50)" value="24">
                    					<p class="help-block">开团劵有效时间,最小值为1,最大值为50,单位为小时</p>
                    					
                    				</div>
								<button type="submit" class="btn btn-default">提交</button>
							</form>
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
	function checkNum(obj,number,minValue,maxValue) {
	    //检查是否是非数字值
	    var re = /^[0-9]+.?[0-9]*$/;
	    if(!re.test(obj.value)){
	   	 obj.value=obj.value.substr(0,obj.value.length-1);
	    }
	    if(obj.value>maxValue){
	   	 alert("最大值为"+maxValue);
	   	 obj.value="";
	    }
	    if(obj.value<minValue){
	   	 alert("最小值为"+minValue);
	   	 obj.value="";
	    }
	    if (obj != null) {
	        //检查小数点后是否对于两位http://blog.csdn.net/shanzhizi
	        var str = obj.value.toString();
	        var index = str.indexOf(".");
			if(index>0){
	       	 str = str.substr(str.indexOf(".")+1);
	       	 index = str.length;
	       	 if(index>number-1){
	       		 obj.value="";
	       	 }
			}
	    }
	    
	}
	function addSubmit(){
		return true;
	}
	$(document).ready(function() {

	});
	
</script>
</tiles:putAttribute>
</tiles:insertDefinition>