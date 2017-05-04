<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-查看调度信息</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="project"/>
			<jsp:param name="second" value="projectManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">任务管理 / <b>${taskJob.name}-调用记录</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
								<a href="${webroot}/taskJobLog/f_view/manager.shtml?projectid=${taskJob.projectid}&jobid=${taskJob.id}" class="btn btn-default btn-sm">返回</a>
							  	<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
					<hr />
			  		<div class="row">
			  			<div class="col-sm-2 text-right">执行状态</div>
			  			<div class="col-sm-10"><span class="label label-success"><my:text id="${taskJobLog.status}" dictcode="job_log_status"/></span></div>
			  		</div>
			  		<hr />
			  		<div class="row">
			  			<div class="col-sm-2 text-right">调度时间</div>
			  			<div class="col-sm-10"><my:date value="${taskJobLog.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			  		</div>
			  		<hr />
			  		<div class="row">
			  			<div class="col-sm-2 text-right">调用链接</div>
			  			<div class="col-sm-10">${taskJobLog.link}</div>
			  		</div>
			  		<hr />
			  		<div class="row">
			  			<div class="col-sm-2 text-right">响应内容</div>
			  			<div class="col-sm-10">${taskJobLog.result}</div>
			  		</div>
		  		</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
</body>
</html>