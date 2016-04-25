<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>

<tiles:insertDefinition name="adminLayout">
<tiles:putAttribute name="admin_active" cascade="true">active</tiles:putAttribute>
<tiles:putAttribute name="title">商品图片管理</tiles:putAttribute>
<tiles:putAttribute name="body">
<!-- Page Content -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">商品图片管理</h1>
        </div>
     
    </div>
    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
            
                <div class="panel-body">
                
                 		<div><a href="javascript:document.getElementById('addImgFile').click();">添加图片</a></div>
                 		<form action="/manager/add_good_img" enctype ="multipart/form-data" method="post">
                 			<input type="text" style="display: none;" name="good_id" value="${good.id}"></input>
                 			<input type="file" id="addImgFile" style="display:none;" name="img"/>
                 		</form>
                    	<c:forEach items="${imgs}" var="img">
                 			<div style="float: left;">
	                   			<img src="${img.imgUrl}" style="width:200px;height:200px;"></img>
	                   			<div>
	                   				<a href="/manager/delete_img?good_id=${good.id}&img_id=${img.imgId}">删除</a>
	                   			</div>
                   			</div>
                		 </c:forEach>
                   		
                       
                </div>
            </div>
        </div>
    </div>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="footerJavascript">
<script>


$(document).ready(function() {
	$("#addImgFile").change(function(){
		$("form").submit();
	});
});
</script>
</tiles:putAttribute>
</tiles:insertDefinition>
