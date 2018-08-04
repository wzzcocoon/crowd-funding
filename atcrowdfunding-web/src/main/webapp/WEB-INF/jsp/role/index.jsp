<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/pagination.css">
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
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" id="queryContent" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" onclick="deleteRoles()"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;margin-left:10px;" onclick="window.location.href='${APP_PATH}/role/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/role/addBatch'"><i class="glyphicon glyphicon-plus"></i> 批量新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" onclick="selAllBox(this)"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody id="roleTbody">
            
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div> 
					 </td>
				 </tr>
			  </tfoot>
            </table>
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
    <script src="${APP_PATH }/jquery/jquery.pagination.js"></script>
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
			    
			    //加载数据
			    var pageno = 0;
			    if("${param.pageno}" != ""){
			    	pageno = parseInt("${param.pageno}") -1;
			    }
			    pageQuery(pageno);
			    
			    //模糊查询
			    $("#queryBtn").click(function(){
			    	//判断是否为空
			    	var queryContent = $("queryContent");
			    	if( queryContent.val() != "" ){
			    		condflg = true;
			    	}
					pageQuery(0);
			    });
            });
            
            //定义一个条件
            var condflg = false;
            //使用AJAX发送异步请求 获取列表
            function pageQuery(pageIndex){
				var pageno = pageIndex + 1 ;//插件中传入的值是索引，0开始
            	var pagesize = 2; //当页显示的记录条数
            	var loadingIndex = 0;
            	var dataObj = {"pageno" : pageno,"pagesize" : pagesize};
            	if( condflg ){
            		dataObj["queryContent"] = $("#queryContent").val();
            	};
           		$.ajax({
           			type : "POST",
           			url : "${APP_PATH}/role/pageQuery",
           			data : dataObj,
           			beforeSend : function(){
                		//js中的全局变量和局部变量？只要加var的都是局部变量，没有加var，是默认加上了window. 
            			loadingIndex = layer.load(2,{time:10*1000});
            		},
            		success : function(result){
            			layer.close(loadingIndex);
            			condflg = false;
            			if(result.success){
            				//alert("需要局部刷新");
            				var rolePage = result.data;
            				var roles = rolePage.datas;
            				var content = "";
            				//遍历每一条记录
            				$.each(roles,function(index,role){
            	                content += '<tr>';
            	                content += '  <td>' +(index+1)+ '</td>';
            					content += '  <td><input type="checkbox" value="' +role.id+ '"></td>';
            	                content += '  <td>' +role.rolename+ '</td>';
            	                content += '  <td>';				
            					content += '      <button type="button" onclick="window.location.href=\'${APP_PATH}/role/assignPermission?id='+role.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            	                content += '      <button type="button" onclick="window.location.href=\'${APP_PATH}/role/edit?pageno='+pageno+'&id='+role.id+'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
            					content += '	  <button type="button" onclick="deleteRole('+role.id+', \''+role.rolename+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            					content += '  </td>';
            	                content += '</tr>';
            	                
            				});
            				$("#roleTbody").html(content);
            				//显示页码 
            				var initPagination = function() {
            					var num_entries = $("#hiddenresult div.result").length;
            					// 创建分页  //参数page_index{int整型}表示当前的索引页
            					$("#Pagination").pagination(rolePage.totalsize, {
            						num_edge_entries: 1, //边缘页数
            						num_display_entries: 2, //主体页数
            						callback: pageQuery,//回调函数的作用是显示对应分页的列表项内容，每次点击分页链接的时候执行
            						items_per_page:pagesize, //每页显示几项
            						prev_text:"上一页",
            						next_text:"下一页",
            						link_to:"javascript:;",
            						current_page:pageIndex
            					});
            				 }();
            			}else {
            				layer.msg("用户分页查询失败！", {time:2000, icon:5, shift:6});
            			}
                	}
           		});
            }
			
            //删除一个角色的功能
            function deleteRole(id,rolename){
            	layer.confirm("是否删除【"+ rolename +"】角色",  {icon: 3, title:'提示'}, function(cindex){
            		$.ajax({
            			type : "POST",
            			url : "${APP_PATH}/role/delete",
            			data : {"id" : id},
            			success : function(result){
            				if(result.success){
            					pageQuery(0);
            				}else{
            					layer.msg("用户信息删除失败！", {time:2000, icon:5, shift:6});
            				}
            			}
            		});
    			    layer.close(cindex);
    			}, function(cindex){
    			    layer.close(cindex);
    			});
            }

            
            //全选框功能
            function selAllBox(box){
            	var flg = box.checked;
            	//获取下面每个复选框,设置状态
            	var userBoxs = $("#roleTbody :checkbox");
            	$.each(userBoxs,function(index,box){
            		box.checked = flg;
            	});
            }
            
            //删除选中角色的功能
            function deleteRoles(){
            	var roleCheckedBoxs = $("#roleTbody :checked");
				if(roleCheckedBoxs.length == 0){
					layer.msg("请选择需要删除的用户信息！", {time:2000, icon:5, shift:6});
				} else {
					layer.confirm("是否删除已经选择的用户信息",  {icon: 3, title:'提示'}, function(cindex){
						
						var jsonData = {};
						$.each(roleCheckedBoxs,function(index,obj){
							jsonData["roles["+index+"].id"] = obj.value;	//obj是个DOM对象，所以不能用val()方法
						});
						
	            		$.ajax({
	            			type : "POST",
	            			url : "${APP_PATH}/role/deletes",
	            			data : jsonData,
	            			success : function(result){
	            				if(result.success){
	            					pageQuery(0);
	            				}else{
	            					layer.msg("用户信息删除失败！", {time:2000, icon:5, shift:6});
	            				}
	            			}
	            		});
	    			    layer.close(cindex);
	    			}, function(cindex){
	    			    layer.close(cindex);
	    			});
				}
            }
        </script>
  </body>
</html>
    