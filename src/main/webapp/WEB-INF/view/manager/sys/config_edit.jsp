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
		<input type="hidden" id="id" value="${sysConfig.id}">
		<div class="form-group">
			<input type="text" class="form-control" id="code" readonly="readonly" value="${sysConfig.code}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="name" readonly="readonly" value="${sysConfig.name}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="value" placeholder="值" value="${sysConfig.value}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="备注" value="${sysConfig.remark}">
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
			var _value = $('#value');
			if(JUtil.isEmpty(_value.val())) {
				_saveMsg.append('请输入值');
				_value.focus();
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/sysConfig/f_json/save.shtml',
				data : {
					id: _id,
					code: $('#code').val(),
					value: _value.val(),
					remark: $('#remark').val()
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