<%@page import="com.task.schedule.comm.enums.Boolean"%>
<%@page import="com.task.schedule.comm.enums.ServInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-服务管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="project"/>
			<jsp:param name="second" value="servInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">任务管理 / <b>服务管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<!-- <span class="enter-panel">
								<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="projectName" placeholder="项目名称">
					  			<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span> -->
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
							  	<a href="${webroot}/servInfo/f_view/chart.shtml" class="btn btn-success btn-sm">服务图表</a>
						  	</div>
						  	<div class="btn-group">
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
				                         '<th>服务编号</th>',
				                         '<th>ip地址</th>',
				                         '<th>状态</th>',
				                         '<th>更新时间</th>',
				                         '<th>运行时间</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/servInfo/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, name:$('#projectName').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							var _cls = 'label-default';
							if(obj.status=='<%=ServInfoStatus.NORMAL.getCode()%>') {
								_cls = 'label-success';
							}
							var _leader = [];
							if(obj.status=='<%=ServInfoStatus.NORMAL.getCode()%>' && obj.isleader == '<%=Boolean.TRUE.getCode()%>') {
								_leader.push(' <span class="label label-success">Leader</span>');
							}
							var _runtime = obj.runminute;
							if(_runtime != -1) {
								var _daynum = parseInt( obj.runminute / (60 * 24) );
								if(_daynum > 0) {
									_runtime = '已运行 ' + _daynum + '天';
								} else {
									var _hournum = parseInt( obj.runminute / 60 );
									if(_hournum > 0) {
										_runtime = '已运行 ' + _hournum + '小时';
									} else {
										_runtime = '已运行 ' + _runtime + '分钟';
									}
								}
								_runtime = '<span class="text-success">' + _runtime + '</span>';
							} else {
								_runtime = obj.statusname;
							}
							if(obj)
							return ['<tr>',
							    	'<td>',obj.servid,'</td>',
							    	'<td>',obj.ip,_leader.join(''),'</td>',
							    	'<td><span class="label ',_cls,'">',obj.statusname,'</span></td>',
							    	'<td>',obj.updatetime,'</td>',
							    	'<td>',_runtime,'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>