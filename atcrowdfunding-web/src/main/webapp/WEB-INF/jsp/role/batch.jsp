<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
</head>
<body>

   	<%@ include file="/WEB-INF/jsp/command/navinfo.jsp" %>

    <div class="container-fluid">
      <div class="row">
		
		<%@include file="/WEB-INF/jsp/command/tree.jsp" %> 
		
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			<div class="panel-body">
		<button type="button" id="addBtn" class="btn btn-primary" style="float:lift;margin-left:10px;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
		<br>
 		<hr style="clear:both;">
          <div class="table-responsive">
          <form id="roleForm" action="${APP_PATH}/user/batchInsert" method="POST">
            <table class="table  table-bordered">
              <thead>
                <tr>
                  <th>角色名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody id="roleTbody">

              </tbody>
            </table>
            </form>
            <button id="saveBtn" type="button" class="btn btn-success" style="float:lift;margin-left:10px;"><i class="glyphicon glyphicon-plus"></i>保存</button>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
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
			    
				var count = 0;
			    //新增一行表格
			    $("#addBtn").click(function(){
			    	var content = "";
			    	content += '<tr>';
             		content += '	<td><input type="text" class="form-control" name="roles['+ count +'].rolename"/></td>';
             		content += '	<td><button onclick="deleteRow(this)" type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button></td>';
             		content += '</tr>';
             		
             		$("#roleTbody").append(content);
             		count ++;
			    });
			    
			    //提交表单
			    $("#saveBtn").click(function(){
			    	$("#roleForm").submit();
			    });
			    
            });
        
            function deleteRow(obj){
            	var trObj = $(obj).parent().parent();
            	trObj.remove();
            }
        </script>
  </body>
</html>
