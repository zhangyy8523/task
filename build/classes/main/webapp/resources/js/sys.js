JUtil.sys = {
		//跳转到指定地址
		location : function(url, islogin, isback) {
			if(islogin && user.uname==='') { JUtil.open.login(); return; }
			if(isback) {
				if(url.indexOf('?')!=-1) url += '&';
				else url += '?';
				url += 'back='+location;
			}
			location = url;
		},
		//初始化系统资料
		init : function() {
			if(config.initHmId)
				JUtil.sys.initHeader(config.initHmId);
		},
		//初始化header的选中
		initHeader : function(id) {
			$('#headerMenu>ul>li').each(function(i, obj) {
				if($(obj).attr('id') == id) {
					$(obj).addClass('active');
					return true;
				}
			});
		}
};

JUtil.open = {
		login : function() {
			parent.dialog({
				title: '任务系统-登录',
				url: webroot+'/view/login.jsp',
				width: 350,
				height: 220
			});
		}
};