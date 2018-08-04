<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="col-sm-3 col-md-2 sidebar">
	<div class="tree">
		<ul style="padding-left:0px;" class="list-group">
		
			<c:forEach items="${rootPermission.children}" var="permission">
				<%-- 判断有没有子菜单(判断当前permission的子集合是否为空) --%>
				<c:if test="${empty permission.children}">
					<li class="list-group-item tree-closed" >
						<a href="${APP_PATH}${permission.url}"><i class="${permission.icon}"></i>${permission.name}</a> 
					</li>
				</c:if>
				<c:if test="${not empty permission.children}">
					<li class="list-group-item tree-closed">
						<span><i class="${permission.icon}"></i>${permission.name}<span class="badge" style="float:right">${fn:length(permission.children)}</span></span> 
						<ul style="margin-top:10px;display:none;">
							<c:forEach items="${permission.children}" var="childPermission">
								<li style="height:30px;">
									<a href="${APP_PATH}${childPermission.url}"><i class="${childPermission.icon}"></i>${childPermission.name}</a> 
								</li>
							</c:forEach>
						</ul>
					</li>
				</c:if>
			</c:forEach>
			
		</ul>
	</div>
</div>