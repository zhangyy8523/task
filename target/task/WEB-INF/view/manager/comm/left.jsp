<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<div class="c-left">
	<div class="panel-group">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a href="${webroot}/sysUser/f_view/main.shtml">个人中心</a>
				</h4>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapse2">任务管理 <span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapse2" class="panel-collapse collapse <c:if test="${param.first == 'project'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'servInfoManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/servInfo/f_view/manager.shtml">服务管理</a>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'servInfoChart'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/servInfo/f_view/chart.shtml">服务图表</a>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'projectManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/taskProject/f_view/manager.shtml">项目管理</a>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'projectChart'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/taskProject/f_view/chart.shtml">项目图表</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapse1">系统管理 <span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapse1" class="panel-collapse collapse <c:if test="${param.first == 'sys'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'userManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block"
							href="${webroot}/sysUser/f_view/manager.shtml">用户管理</a>
					</div>
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'configManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block"
							href="${webroot}/sysConfig/f_view/manager.shtml">系统配置</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>