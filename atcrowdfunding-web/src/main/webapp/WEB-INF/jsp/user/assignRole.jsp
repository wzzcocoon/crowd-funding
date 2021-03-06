<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/main.css">
	<link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

   	<%@ include file="/WEB-INF/jsp/command/navinfo.jsp" %>
   
    <div class="container-fluid">
      <div class="row">
        
		<%@ include file="/WEB-INF/jsp/command/tree.jsp" %>
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form role="form" class="form-inline">
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select id="leftList" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                        
                        <c:forEach items="${leftRoles}" var="role" >
	                        <option value="${role.id }">${role.rolename }</option>
                        </c:forEach>

                    </select>
				  </div>
				  <div class="form-group">
                        <ul>
                            <li id="left2RightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="right2LeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select id="rightList" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
						
                        <c:forEach items="${rightRoles}" var="role" >
	                        <option value="${role.id }">${role.rolename }</option>
                        </c:forEach>
						                    
                    </select>
				  </div>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
    <script src="${APP_PATH }/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    //从左到右的功能
			    $("#left2RightBtn").click(function(){
			    	//获取左边列表选中的多个元素
			    	var selOptions = $("#leftList :selected");
			    	if(selOptions.length == 0){
				    	layer.msg("请选择需要分配的角色！", {time:2000, icon:5, shift:6});
			    	}
			    	
			    	var jsonData = {"userid" : "${user.id}"};
			    	$.each(selOptions,function (i,opt){
			    		jsonData["ids["+i+"]"] = opt.value;
			    	});
			    	$.ajax({
			    		type:"post",
			    		url:"${APP_PATH}/user/assign",
			    		data:jsonData,
			    		success:function(result){
			    			if(result.success){
						    	//将选中的元素移动到右边（数据库中添加）
						    	$("#rightList").append(selOptions);
			    			}else {
						    	layer.msg("分配角色失败！", {time:2000, icon:5, shift:6});
			    			}
			    		}
			    	});
			    });

			    //从右到左的功能
			    $("#right2LeftBtn").click(function(){
			    	//获取右边列表选中的多个元素
			    	var selOptions = $("#rightList :selected");
			    	if(selOptions.length == 0){
				    	layer.msg("请选择需要取消分配的角色！", {time:2000, icon:5, shift:6});
			    	}
			    	
			    	var jsonData = {"userid" : "${user.id}"};
			    	$.each(selOptions,function (i,opt){
			    		jsonData["ids["+i+"]"] = opt.value;
			    	});
			    	$.ajax({
			    		type:"post",
			    		url:"${APP_PATH}/user/unassign",
			    		data:jsonData,
			    		success:function(result){
			    			if(result.success){
						    	//将选中的元素移动到左边（从数据库中删除）
			    				$("#leftList").append(selOptions);
			    			}else {
						    	layer.msg("取消分配角色失败！", {time:2000, icon:5, shift:6});
			    			}
			    		}
			    	});
			    });
			    
            });
        </script>
  </body>
</html>
