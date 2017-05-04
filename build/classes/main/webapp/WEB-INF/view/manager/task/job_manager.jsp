<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-任务管理</title>
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
				<div class="panel-heading">任务管理 / <b>任务管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-8">
							<span class="enter-panel">
								<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="jobName" placeholder="任务名称">
								<select id="servid" style="width: 120px;display: inline;"  class="form-control input-sm">
									<option value="">请选择服务</option>
									<c:forEach items="${servInfos}" var="info"><option value="${info.servid}">${info.servid}</option></c:forEach>
								</select>
						  		<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span>
						</div>
						<div class="col-sm-4 text-right">
						  	<a class="btn btn-link btn-sm" href="${webroot}/help.jsp?id=helpJob" target="_blank">帮助</a>
						  	<div class="btn-group">
							  	<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增任务</a>
						  	</div>
						  	<div class="btn-group">
						  		<a href="${webroot}/taskProject/f_view/manager.shtml" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel"></div>
					<div id="infoPage"></div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/page.jsp"></jsp:include>
<script type="text/javascript">
var infoPage = undefined;
var info = {
		//获取用户信息
		loadInfo : function(page) {
			if(!infoPage) {
				infoPage = new Page('infoPage', info.loadInfo, 'infoPanel', 'infoPage');
				infoPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th>编号</th>',
				                         '<th>任务名称</th>',
				                         '<th width="130">服务编号</th>',
				                         '<th width="80">状态</th>',
				                         '<th width="230">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/taskJob/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, projectid: '${param.projectid}',
					name: $('#jobName').val(), servid: $('#servid').val()
				},
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							var _operate = [];
							var _statusname = '<span class="label label-' + (obj.status === 0 ? 'success':'danger') + '">' + obj.statusname + '</span>';
							if(obj.status === 0 || obj.status === <%=com.task.schedule.comm.enums.JobStatus.WAIT.getCode()%>) {
								_operate.push(' &nbsp; <a href="javascript:info.status(',obj.id,',<%=com.task.schedule.comm.enums.JobStatus.STOP.getCode()%>)" class="glyphicon glyphicon-pause text-danger" title="暂停"></a>');
							} else {
								_operate.push(' &nbsp; <a href="javascript:info.status(',obj.id,',<%=com.task.schedule.comm.enums.JobStatus.NORMAL.getCode()%>)" class="glyphicon glyphicon-play text-success" title="启动"></a>');
							}
							_operate.push(' &nbsp; <a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.id,')" title="修改"></a>',
							    	' &nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.id,')" title="删除"></a>',
							    	' &nbsp; <a href="javascript:info.execJob(',obj.id,')">立即执行</a>',
							    	' &nbsp; <a href="',webroot,'/taskJobLog/f_view/manager.shtml?projectid=${param.projectid}&jobid=',obj.id,'">调度日志</a>');
							return ['<tr>',
							    	'<td>',obj.id,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td>',obj.servid,'</td>',
							    	'<td>',_statusname,'</td>',
							    	'<td>',_operate.join(''),'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//启动或停止任务
		status : function(id, status) {
			var _statusname = (status===0?'启动':'停止');
			if(confirm('您确定要'+_statusname+'该任务吗?')) {
				JUtil.ajax({
					url : '${webroot}/taskJob/f_json/updateStatus.shtml',
					data : { id: id, status: status },
					success : function(json) {
						if (json.result === 'success') {
							message(_statusname + '成功');
							info.loadInfo(1);
						}
						else if (json.result === 'error')
							message(JUtil.msg.ajaxErr);
						else
							message(json.msg);
					}
				});
			}
		},
		execJob : function(id) {
			if(confirm('您确定要立即执行该任务吗?')) {
				JUtil.ajax({
					url : '${webroot}/taskJob/f_json/execJob.shtml',
					data : { id: id },
					success : function(json) {
						if (json.result === 'success') {
							message('执行成功');
							info.loadInfo(1);
						}
						else if (json.result === 'error')
							message(JUtil.msg.ajaxErr);
						else
							message(json.msg);
					}
				});
			}
		},
		//编辑
		edit : function(id) {
			dialog({
				title: '编辑任务',
				url: webroot + '/taskJob/f_view/edit.shtml?projectid=${param.projectid}&id='+(id?id:''),
				type: 'iframe',
				width: 380,
				height: 420
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该任务吗?')) {
				JUtil.ajax({
					url : '${webroot}/taskJob/f_json/delete.shtml',
					data : { id: id },
					success : function(json) {
						if (json.result === 'success') {
							message('删除成功');
							info.loadInfo(1);
						}
						else if (json.result === 'error')
							message(JUtil.msg.ajaxErr);
						else
							message(json.msg);
					}
				});
			}
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>