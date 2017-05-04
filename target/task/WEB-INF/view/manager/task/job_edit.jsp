<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-编辑任务</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="id" value="${taskJob.id}">
		<input type="hidden" id="projectid" value="${param.projectid}">
  		<div class="form-group">
			<input type="text" class="form-control" id="name" placeholder="名称" value="${taskJob.name}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="描叙" value="${taskJob.remark}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="link" placeholder="调用链接" value="${taskJob.link}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="cron" placeholder="任务规则" value="${taskJob.cron}">
			<div class="text-right">
				<small>
					<a href="javascript:;" onclick="$('#cron').val('0/3 * * * * ?')" title="每3秒执行">每3秒</a> |
					<a href="javascript:;" onclick="$('#cron').val('25 0/1 * * * ?')" title="每分钟25秒执行">每分钟</a> |
					<a href="javascript:;" onclick="$('#cron').val('0 0 0/1 * * ?')" title="每小时0点0分执行">每小时</a> |
					<a href="javascript:;" onclick="$('#cron').val('0 30 8,9 ? * MON')" title="每周一的8:30和9:30执行">每周一</a>
				</small>
			</div>
		</div>
		<div class="form-group">任务执行状态：<my:radio id="status" name="status" dictcode="job_status" value="${taskJob.status}" defvalue="0" /></div>
  		<div class="form-group">
		<c:choose>
		<c:when test="${taskJob == null}">失败邮件通知：<my:radio id="isfailmail" name="isfailmail" dictcode="boolean" value="${taskProject.isrecemail}" defvalue="0"/></c:when>
		<c:otherwise>失败邮件通知：<my:radio id="isfailmail" name="isfailmail" dictcode="boolean" value="${taskJob.isfailmail}" defvalue="0"/></c:otherwise>
		</c:choose>
  		</div>
  		<div class="form-group">
 			<div class="btn-group">
				<button type="button" id="saveBtn" class="btn btn-success enter-fn">保存</button>
			</div>
			<span id="saveMsg" class="label label-danger"></span>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _link = $('#link');
			if(JUtil.isEmpty(_link.val())) {
				_saveMsg.append('请输入调用链接');
				_link.focus();
				return;
			}
			var _cron = $('#cron');
			if(JUtil.isEmpty(_cron.val())) {
				_saveMsg.append('请输入任务规则');
				_cron.focus();
				return;
			}
			var _status = $('input[name="status"]:checked').val();
			if(JUtil.isEmpty(_status)) {
				_saveMsg.append('请选择任务执行状态');
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/taskJob/f_json/save.shtml',
				data : {
					id: $('#id').val(),
					projectid: $('#projectid').val(),
					name: _name.val(),
					remark: $('#remark').val(),
					link: _link.val(),
					cron: _cron.val(),
					status: _status,
					isfailmail: $('input[name="isfailmail"]:checked').val()
				},
				success : function(json) {
					if (json.result === 'success') {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						setTimeout(function() {
							parent.info.loadInfo(1);
							parent.dialog.close();
						}, 800);
					}
					else if (json.result === 'error')
						_saveMsg.append(JUtil.msg.ajaxErr);
					else
						_saveMsg.append(json.msg);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>