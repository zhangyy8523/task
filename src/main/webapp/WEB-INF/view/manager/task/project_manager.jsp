<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-项目管理</title>
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
				<div class="panel-heading">任务管理 / <b>项目管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<span class="enter-panel">
								<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="projectName" placeholder="项目名称">
					  			<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span>
						</div>
						<div class="col-sm-6 text-right">
						  	<a class="btn btn-link btn-sm" href="${webroot}/help.jsp?id=helpProject" target="_blank">帮助</a>
						  	<div class="btn-group">
							  	<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增项目</a>
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
				                         '<th>编号</th>',
				                         '<th>项目名称</th>',
				                         '<th>发邮件</th>',
				                         '<th>接收邮箱</th>',
				                         '<th width="180">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/taskProject/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, name:$('#projectName').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							return ['<tr>',
							    	'<td>',obj.id,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td>',obj.isrecemailname,'</td>',
							    	'<td>',obj.recemail,'</td>',
							    	'<td><a class="btn btn-link btn-xs" href="',webroot,'/taskJob/f_view/manager.shtml?projectid=',obj.id,'">任务管理</a> ',
							    	'&nbsp; <a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.id,')" title="修改"></a> ',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.id,')" title="删除"></a></td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑项目
		edit : function(id) {
			dialog({
				title: '编辑项目',
				url: webroot + '/taskProject/f_view/edit.shtml?id='+(id?id:''),
				type: 'iframe',
				width: 420,
				height: 460
			});
		},
		del : function(id) {
			if(confirm('项目被删除后，其对应的任务都会被删除，您确定要删除该项目吗?')) {
				JUtil.ajax({
					url : '${webroot}/taskProject/f_json/delete.shtml',
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