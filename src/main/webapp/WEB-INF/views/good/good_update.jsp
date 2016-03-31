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
								action="<c:url value="/manager/good_update_do"/>" method="post"
								onsubmit="return addSubmit();">
								<div class="form-group">
                    					<label>商品标题图片</label>
                    					<img class="img-thumbnail" src="${good.headImgUrl}">
                    					<input type="file" >
                					</div>
                					
                					
								<div class="form-group">
									<label>商品名称</label> <input class="form-control" name="name" value="${good.name}">
									<p class="help-block">输入该商品的名称</p>
								</div>
								<div class="form-group" >
									<label>商品标题</label> <input class="form-control" name="title" value="${good.title}">
									<p class="help-block">请输入商品标题</p>
								</div>
								
								<div class="form-group">
									<label>通知</label> <input class="form-control"  name="notice" value="${good.notice}">
									<p class="help-block">请输入商品通知信息</p>
								</div>
								
								<div class="form-group">
									<label>简介</label> <input class="form-control" name="instruction" value="${good.instruction}">
									<p class="help-block">请输入商品简介</p>
								</div>
								<div class="form-group">
			                                <label>商品类别</label>
			                                <select class="form-control">
			                                	<c:forEach items="${goodTypes}" var="goodType">
			                                		<option  <c:if test="${goodType.id==good.goodType}">selected="selected"</c:if>value="${goodType.id}">${goodType.name}</option>
			                                	</c:forEach>
			                                </select>
			                                <p class="help-block">请输入商品类别</p>
                            			</div>
								<div class="form-group" id="device_name_div">
									<label>状态</label>
									<div>
										<div style="display: inline-block;"><input <c:if test="${good.status==0}">checked="checked"</c:if> class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="0"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">下架</label></div>
										<div style="display: inline-block;"><input <c:if test="${good.status==2}">checked="checked"</c:if>class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">售完</label></div>
										<div style="display: inline-block;"><input <c:if test="${good.status==1}">checked="checked"</c:if> class="form-control" type="radio" name="status" style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">正常</label></div>
									
									</div>
									<p class="help-block">选择商品状态</p>
								</div>
								
								<div class="form-group"id="device_name_div">
									<label>是否展示</label>
									<div>
										<div style="display: inline-block;"><input <c:if test="${good.isDisplayMain==true}">checked="checked"</c:if> class="form-control" type="radio"  name="isDisplay"  style="width:20px;display: inline-block;" value="1"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">是</label></div>
										<div style="display: inline-block;"><input <c:if test="${good.isDisplayMain==false}">checked="checked"</c:if> class="form-control" type="radio"  name="isDisplay"  style="width:20px;display: inline-block;" value="0"><label style="font-size: 10px;line-height: -20px;display: inline-block;text-align: justify;">否</label></div>
									
									</div>
									<p class="help-block">选择该商品是否展示</p>
								</div>
								
								<div class="form-group">
									<label>优先级</label> <input class="form-control"  name="rank" value="${good.rank}">
									<p class="help-block">请输入该商品排列的优先级</p>
								</div>
								
								<div class="form-group">
									<label>组团时长</label> <input class="form-control" name="timeLong" value="${good.timeLong}">
									<p class="help-block">请输入该商品组团时长</p>
								</div>
								
								<div class="form-group">
									<label>组团数量</label> <input class="form-control"  name="groupNum" value="${good.groupNum}">
									<p class="help-block">请输入该商品组团需要的人数</p>
								</div>
								
								<div class="form-group">
									<label>单卖原价</label> <input class="form-control" name="aloneOriginalCost" value="${good.aloneOriginalCost}">
									<p class="help-block">请输入该商品单卖的原价</p>
								</div>
								
								<div class="form-group">
									<label>单卖折扣</label> <input class="form-control" name="aloneDiscount" value="${good.aloneDiscount}">
									<p class="help-block">请输入该商品单卖折扣,填1表示十折，0.1表示一折</p>
								</div>
								
								
								<div class="form-group">
									<label>组团原价</label> <input class="form-control" name="groupOriginalCost" value="${good.groupOriginalCost}">
									<p class="help-block">请输入该商品组团的原价</p>
								</div>
								
								<div class="form-group">
									<label>组团折扣</label> <input class="form-control" name="groupDiscount" value="${good.groupDiscount}">
									<p class="help-block">请输入该商品组团折扣,填1表示十折，0.1表示一折</p>
								</div>
								
								<div class="form-group" >
									<label>市场价</label> <input class="form-control" name="marketPrice" value="${good.marketPrice}">
									<p class="help-block">请输入该商品市场价格</p>
								</div>
								
								<div class="form-group">
									<label>物流费用</label> <input class="form-control" name="flowPrice" value="${good.flowPrice}">
									<p class="help-block">请输入该商品物流费用</p>
								</div>
								
								<div class="form-group">
									<label>库存数</label> <input class="form-control" name="stock" value="${good.stock}">
									<p class="help-block">请输入该商品库存数量</p>
								</div>
								
								<div class="form-group">
									<label>库存数</label> <input class="form-control" name="salesVolume" value="${good.salesVolume}">
									<p class="help-block">请输入该商品已销售数量</p>
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
//		check('usernameDiv');
	});
	function addSubmit() {
		return true;

	}
	function check(name) {
		var divObject = getJqueryDivObject(name);
		var inputObject = divObject.find("input");
		if (name == "passwordDiv") {
			if (!inputObject.val()) {
				divObject.find("p").text("密码不能为空");
				addErrorCss(name);
				return false;
			} else {
				divObject.find("p").text("请输入用户初始密码");
				remoteErrorCss(name);
				return true;
			}
		} else if (name == "affirm_passwordDiv") {
			var passwordInputObject = getJqueryDivObject("passwordDiv")
					.find("input");
			var oldContent = divObject.find("p").text();
			if (passwordInputObject.val() != inputObject.val()) {
				divObject.find("p").text("原始密码和确认密码不一致");
				addErrorCss(name);
				return false;
			} else {
				divObject.find("p").text("请确认密码");
				remoteErrorCss(name);
				return true;
			}
		} else if (name == "usernameDiv") {
			var oldContent = divObject.find("p").text();
			if (!inputObject.val()) {
				addErrorCss(name);
				oldContent = divObject.find("p").text("用户名不为空");
				return false;
			} else {
				var htmlobj=$.ajax({url:"<c:url value="/admin/get_admin_json"/>",async:false,data:{username:inputObject.val()}});
				if(!htmlobj.responseText||!eval("("+htmlobj.responseText+")")||!eval("("+htmlobj.responseText+")").username)
				{
					divObject.find("p").text("请输入用户名，用户名唯一，最少4字符");
					remoteErrorCss(name);
					return true;
				}
				else{
					addErrorCss(name);
					oldContent = divObject.find("p").text("用户名不能重复");
					return false;
				}
				
			}
		}
	}
	function remoteErrorCss(name) {
		var obj = getJqueryDivObject(name);
		obj.removeClass("has-error");
	}
	function addErrorCss(name) {
		var obj = getJqueryDivObject(name);
		obj.addClass("has-error");
	}
	function getJqueryDivObject(name) {
		var obj = $("div[name=" + name + "]");
		return obj;
	}
</script>
</tiles:putAttribute>
</tiles:insertDefinition>