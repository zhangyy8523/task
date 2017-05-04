<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-任务调用日志</title>
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
				<div class="panel-heading">任务管理 / <b>任务调用记录</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<span class="enter-panel">
								<my:select id="jobLogStatus" name="jobLogStatus" headerKey="" headerValue="请选择状态" dictcode="job_log_status" cssCls="form-control input-sm" exp="style=\"width: 120px;display: inline;\"" />
					  			<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span>
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  		<a href="${webroot}/taskJob/f_view/manager.shtml?projectid=${param.projectid}" class="btn btn-default btn-sm">返回</a>
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
				                         '<th>调度时间</th>',
				                         '<th>服务编号</th>',
				                         '<th>状态</th>',
				                         '<th width="120">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/taskJobLog/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, jobid: '${param.jobid}', status: $('#jobLogStatus').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							var _statusname = '<span class="label label-' + (obj.status === 0 ? 'success':'danger') + '">' + obj.statusname + '</span>';
							return ['<tr>',
							    	'<td>',obj.id,'</td>',
							    	'<td>',obj.addtime,'</td>',
							    	'<td>',obj.servicecode,'</td>',
							    	'<td>',_statusname,'</td>',
							    	'<td><a class="btn btn-link btn-xs" href="javascript:info.look(',obj.id,')">查看</a></td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//查看
		look : function(id) {
			/* dialog({
				title: '查看调度信息',
				url: webroot + '/taskJobLog/f_view/look.shtml?id='+id?id:'',
				type: 'iframe',
				width: 600,
				height: 365
			}); */
			location = webroot + '/taskJobLog/f_view/look.shtml?id='+(id?id:'');
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>