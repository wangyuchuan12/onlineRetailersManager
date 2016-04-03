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
			<h1 class="page-header">新增商品</h1>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">商品管理</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<form role="form"
								action="<c:url value="/manager/good_add_do?id=${good.id}"/>" method="post" enctype ="multipart/form-data"
								onsubmit="return addSubmit();">
								<div class="form-group">
                    					<label>商品标题图片</label>
                    					<img style="display: block;" class="img-thumbnail" src="${good.headImgUrl}">
                    					<input type="file" name="headImg">
                    					<p class="help-block">*请选择文件</p>
                    					<p class="help-block">请选择图片</p>
                					</div>
                					
                					
								<div class="form-group" name="nameDiv">
									<label>商品名称</label> <input class="form-control" name="name" value="${good.name}">
									<p class="help-block">*输入该商品的名称</p>
									<p class="help-block" hidden="true">输入的商品名称格式不合法，不能包含特殊字符或者为空</p>
								</div>
								<div class="form-group" name="titleDiv" >
									<label>商品标题</label> <input class="form-control" name="title" value="${good.title}">
									<p class="help-block">*请输入商品标题</p>
									<p class="help-block" hidden="true">输入的标题格式不合法，不能包含特殊字符或者为空</p>
								</div>
								
								<div class="form-group" name="noticeDiv">
									<label>通知</label> <input class="form-control"  name="notice" value="${good.notice}">
									<p class="help-block">请输入商品通知信息(一般情况下不填，除非真的有需要)</p>
									<p class="help-block" hidden="true">输入的商品通知信息格式不合法，不能包含特殊字符或者为空</p>
								</div>
								
								<div class="form-group" name="instructionDiv">
									<label>简介</label> <input class="form-control" name="instruction" value="${good.instruction}">
									<p class="help-block">*请输入商品简介</p>
									<p class="help-block" hidden="true">输入的商品简介信息格式不合法，不能包含特殊字符或者为空</p>
								</div>
								<div class="form-group" name="goodTypeDiv">
			                                <label>商品类别</label>
			                                <select class="form-control" name="goodType">
			                                	<c:forEach items="${goodTypes}" var="goodType">
			                                		<option   <c:if test="${goodType.id==good.goodType}">selected="selected"</c:if>value="${goodType.id}">${goodType.name}</option>
			                                	</c:forEach>
			                                </select>
			                                <p class="help-block">*请选择商品类别</p>
			                                
                            			</div>
								<div class="form-group" name="statusDiv">
									<label>状态</label>
									<div>
										<div style="display: inline-block;"><input checked="checked" class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="0"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">下架</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">售完</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">正常</label></div>
									
									</div>
									<p class="help-block">*选择商品状态</p>
								</div>
								
								<div class="form-group" name="isDisplayDiv">
									<label>是否展示</label>
									<div>
										<div style="display: inline-block;"><input checked="checked" class="form-control" type="radio"  name="isDisplay"  style="width:20px;display: inline-block;" value="true"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">是</label></div>
										<div style="display: inline-block;"><input class="form-control" type="radio"  name="isDisplay"  style="width:20px;display: inline-block;" value="false"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">否</label></div>
									
									</div>
									<p class="help-block">*选择该商品是否展示</p>
								</div>
								
								<div class="form-group" name="rankDiv">
									<label>优先级</label> <input class="form-control"  name="rank" value="${good.rank}" type="number" onkeyup="checkNum(this,1,0,50)">
									<p class="help-block">*请输入该商品排列的优先级</p>
									<p class="help-block" hidden="true">输入的优先级不能为空</p>
								</div>
								
								<div class="form-group" name="timeLongDiv">
									<label>组团时长</label> <input class="form-control" name="timeLong" value="${good.timeLong}" type="number" onkeyup="checkNum(this,1,0,24)">
									<p class="help-block">*请输入该商品组团时长</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="groupNumDiv">
									<label>组团数量</label> <input class="form-control"  name="groupNum" value="${good.groupNum}" type="number" onkeyup="checkNum(this,1,0,50)">
									<p class="help-block">*请输入该商品组团需要的人数</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="aloneOriginalCostDiv">
									<label>单卖原价</label> <input class="form-control" name="aloneOriginalCost" value="${good.aloneOriginalCost}" type="number" onkeyup="checkNum(this,2,0,1000)">
									<p class="help-block">*请输入该商品单卖的原价</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="aloneDiscountDiv">
									<label>单卖折扣</label> <input class="form-control" name="aloneDiscount" value="${good.aloneDiscount}" type="number" onkeyup="checkNum(this,3,0,1)">
									<p class="help-block">*请输入该商品单卖折扣,填1表示十折，0.1表示一折</p>
									<p class="help-block">输入的格式不合法</p>
								</div>
								
								
								<div class="form-group" name="groupOriginalCostDiv">
									<label>组团原价</label> <input class="form-control" name="groupOriginalCost" value="${good.groupOriginalCost}" type="number" onkeyup="checkNum(this,2,0,1000)">
									<p class="help-block">*请输入该商品组团的原价</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="groupDiscountDiv">
									<label>组团折扣</label> <input class="form-control" name="groupDiscount" value="${good.groupDiscount}" type="number" onkeyup="checkNum(this,3,0,1)">
									<p class="help-block">*请输入该商品组团折扣,填1表示十折，0.1表示一折</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="marketPriceDiv">
									<label>市场价</label> <input class="form-control" name="marketPrice" value="${good.marketPrice}" type="number" onkeyup="checkNum(this,2,0,1000)">
									<p class="help-block">*请输入该商品市场价格</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="flowPriceDiv">
									<label>物流费用</label> <input class="form-control" name="flowPrice" value="0" type="number" readonly="readonly">
									<p class="help-block">*请输入该商品物流费用</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="stockDiv">
									<label>库存数</label> <input class="form-control" name="stock" value="${good.stock}" type="number" onkeyup="checkNum(this,1,0,9999)">
									<p class="help-block">*请输入该商品库存数量</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
								</div>
								
								<div class="form-group" name="salesVolumeDiv">
									<label>已销售</label> <input class="form-control" name="salesVolume" value="${good.salesVolume}" disabled="disabled" type="number">
									<p class="help-block">请输入该商品已销售数量</p>
									<p class="help-block" hidden="true">输入的格式不合法</p>
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
	$(document).ready(function() {
	//	check('usernameDiv');
		$("input").each(function(){
			$(this).parent().find("p").eq(1).hide();
		});
		$("input").blur(function(outThis){
			var divObject = $(this).parent();
			checkDiv(divObject);
		});
	});
	
	function checkDiv(divObject){
		var inputObject = divObject.find("input");
		var attrName = inputObject.attr("name");
		if(attrName&&attrName!="status"&&attrName!="isDisplay"&&attrName!="salesVolume"){
			if(!checkStr(inputObject.val())){
				divObject.addClass("has-error");
				divObject.find("p").eq(0).hide();
				divObject.find("p").eq(1).show();
				return false;
			}else{
				divObject.removeClass("has-error");
				divObject.find("p").eq(0).show();
				divObject.find("p").eq(1).hide();
				return true;
			}
		}
	}
	function addSubmit() {
		var b = true;
		$("input").each(function(){
			if($(this).attr("name")!="notice"&&$(this).attr("name")&&$(this).attr("name")!="status"&&$(this).attr("name")!="isDisplay"&&$(this).attr("name")!="salesVolume"){
				if(!checkDiv($(this).parent())){
					b = false;
				}
			}
			
		});
		if(!b){
			alert("请输入正确的格式");
		}
		return b;
	}
	
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
	
	function checkStr(str){
		if(!str){
			return false;
		}else{
			return true;
		}
		
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>