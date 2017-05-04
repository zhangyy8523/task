<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-编辑用户</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="id" value="${sysUser.id}">
  		<div class="form-group">
			<input type="text" class="form-control" id="username" placeholder="用户名" value="${sysUser.username}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="password" placeholder="密码" value="">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="nickname" placeholder="昵称" value="${sysUser.nickname}">
		</div>
		<div class="form-group">
			<my:select id="status" headerKey="" headerValue="请选择状态" dictcode="general_status" value="${sysUser.status}" cssCls="form-control" />
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
			
			var _id = $('#id').val();
			var _username = $('#username');
			if(JUtil.isEmpty(_username.val())) {
				_saveMsg.append('请输入用户名');
				_username.focus();
				return;
			}
			var _password = $('#password');
			if(_id === '' && JUtil.isEmpty(_password.val())) {
				_saveMsg.append('请输入密码');
				_password.focus();
				return;
			}
			var _nickname = $('#nickname');
			if(JUtil.isEmpty(_nickname.val())) {
				_saveMsg.append('请输入昵称');
				_nickname.focus();
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/sysUser/f_json/save.shtml',
				data : {
					id: _id,
					username: _username.val(),
					password: _password.val(),
					nickname: _nickname.val(),
					status: $('#status').val()
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