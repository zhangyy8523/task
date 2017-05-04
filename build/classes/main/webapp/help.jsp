<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-帮助</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<div class="c_body_l">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tab theme-none">
					<ul class="nav nav-tabs">
				  		<li class="active"><a href="#helpIndex" data-toggle="tab">定时任务系统简介</a></li>
				  		<li><a href="#helpProject" data-toggle="tab">项目配置说明</a></li>
				  		<li><a href="#helpJob" data-toggle="tab">任务配置说明</a></li>
					</ul>
				</div>

			<!-- Tab panes -->
			<div class="tab-content panel-body" style="padding: 15px 10px 0px 0px;">
			  	<div class="tab-pane active" id="helpIndex">
			  		<ul>
			  			<li>任务系统可以做什么？
				  			<ul>
					  			<li>可以整合各个系统需要定时执行的任务</li>
					  			<li>可以将任务绑定在项目下</li>
					  			<li>可以查看任务的调用日志</li>
					  			<li>任务执行失败可以邮件通知</li>
					  			<li>可以根据任务执行返回的内容格式发送邮件通知</li>
					  		</ul>
			  			</li>
			  		</ul>
			  	</div>
			  	<div class="tab-pane" id="helpProject">
			  		<ul>
			  			<li>配置项目
				  			<ul>
					  			<li>可以配置公用的渠道验证</li>
					  			<li>能配置是否接收邮件通知</li>
					  			<li>能指定接收的邮箱，可以配置多个接收邮箱</li>
					  		</ul>
			  			</li>
			  		</ul>
			  	</div>
			  	<div class="tab-pane" id="helpJob">
			  		<ul>
			  			<li>Job配置http请求返回的结果发送邮件功能
				  			<ul>
				  				<li>触发规则：isSendMail的值为true则发送邮件，接收人为项目设置的接收邮箱。</li>
					  			<li>实例：{isSendMail:"true",mailTitle:"邮件标题",mailContent:"邮件正文"}</li>
					  			<li>参数说明：</li>
					  			<li> &nbsp; isSendMail：true/false (true代表发送邮件，false代表不发送)</li>
					  			<li> &nbsp; mailTitle：发送邮件的标题</li>
					  			<li> &nbsp; mailContent：发送邮件的内容</li>
					  		</ul>
			  			</li>
			  		</ul>
			  	</div>
			</div>
			</div>
		</div>

	</div>
	<input type="hidden" id="id" value="<c:out value="${param.id}"></c:out>"/>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	$(function() {
		var _id = '#' + $('#id').val();
		$('.nav-tabs').find('li>a').each(function(i, obj) {
			if($(obj).attr('href') == _id) {
				$(obj).click();
			}
		});
	});
	</script>
</body>
</html>