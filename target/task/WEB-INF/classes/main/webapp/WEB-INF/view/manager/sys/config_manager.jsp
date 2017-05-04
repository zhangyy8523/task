<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-用户管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="sys"/>
			<jsp:param name="second" value="configManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">系统管理 / <b>系统配置</b></div>
				<div class="panel-body">
				  	<div class="row">
						<div class="col-sm-6">
						</div>
						<div class="col-sm-6 text-right">
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
				                         '<th>编码</th>',
				                         '<th>名称</th>',
				                         '<th>值</th>',
				                         '<th>备注</th>',
				                         '<th width="100">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/sysConfig/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							return ['<tr>',
							    	'<td>',obj.id,'</td>',
							    	'<td>',obj.code,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td>',obj.value,'</td>',
							    	'<td>',obj.remark,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.id,')" title="修改"></a></td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑配置
		edit : function(id) {
			dialog({
				title: '编辑配置',
				url: webroot + '/sysConfig/f_view/edit.shtml?id='+(id?id:''),
				type: 'iframe',
				width: 350,
				height: 315
			});
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>