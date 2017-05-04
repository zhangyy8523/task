<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-编辑项目</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="id" value="${taskProject.id}">
  		<div class="form-group">
			<input type="text" class="form-control" id="name" placeholder="名称" value="${taskProject.name}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="描叙" value="${taskProject.remark}">
		</div>
		<div class="form-group">
			<my:select id="sign" headerKey="" headerValue="请选择加密方式" dictcode="project_sign" value="${taskProject.sign}" cssCls="form-control" />
		</div>
  		<div class="form-group">
  			<textarea class="form-control" rows="2" id="signstring" placeholder="参数(token不会提交)">${taskProject.signstring}</textarea>
  			<small class="text-muted">theCurrentTimestamp：代表当前时间戳<br/>encryptionParameters：代表加密参数</small>
		</div>
		<div class="form-group">邮件通知：<my:radio id="isrecemail" name="isrecemail" dictcode="boolean" value="${taskProject.isrecemail}" defvalue="0"/>
			<small class="text-muted">(选"否"，代表所有任务都不发邮件)</small>
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="recemail" placeholder="接收邮箱（多个,分隔）" value="${taskProject.recemail}">
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
	var projectSigns = ${projectSigns};
	$(function() {
		$('#sign').change(function() {
			var _id = $('#sign').val();
			if(JUtil.isEmpty(_id)) {
				$('#signstring').val('');
				return;
			}
			$.each(projectSigns, function(i, obj) {
				if(obj.kcode === _id) {
					$('#signstring').val(obj.exp);
				}
			});
		});
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _remark = $('#remark');
			if(JUtil.isEmpty(_remark.val())) {
				_saveMsg.append('请输入描叙');
				_remark.focus();
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/taskProject/f_json/save.shtml',
				data : {
					id: $('#id').val(),
					name: _name.val(),
					remark: _remark.val(),
					sign: $('#sign').val(),
					signstring: $('#signstring').val(),
					isrecemail: $('input[name="isrecemail"]:checked').val(),
					recemail: $('#recemail').val()
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