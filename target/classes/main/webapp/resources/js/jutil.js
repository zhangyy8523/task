//给Array添加remove方法
Array.prototype.remove=function(obj) {
	if(obj===undefined || obj===null) return false;
	for(var i = 0, n = 0; i < this.length; i++) {
		if(this[i]!=obj) {
			this[n++]=this[i];
		}
	}
	this.length-=1;
};

var JUtil = {
		//判断值是否为空[为空的内容undefined/null/'']
		isEmpty : function(value) {
			if(value === undefined || value === null || value === '')
				return true;
			return false;
		},
		//判断值不为空[值不为undefined/null/'']
		isNotEmpty : function(value) {
			return !JUtil.isEmpty(value);
		}
};

JUtil.msg = {
		ajaxErr : '访问系统出现问题了，稍后访问或联系客服吧'
};

JUtil.tabs = {
		//放tabs组 比如["test1,test2,test3", "abc1,abc2"]
		arr : []
};
$(function() {
	//$('ul.nav li.dropdown').hover(function() {
	$('ul.nav li.dropdown').hover(function() {
	  	$(this).find('.dropdown-menu').stop(true, true).fadeIn(200);
	}, function() {
	  	$(this).find('.dropdown-menu').stop(true, true).fadeOut(200);
	});
	
	//激活tooltip插件
	//$('a[data-toggle=tooltip]').mouseover(function() { $(this).tooltip('show'); });
	$('[data-toggle=tooltip]').mouseover(function() { $(this).tooltip('show'); });
	//自定义的tabs
	$('.jutil-tabs').each(function(i, obj) {
		var _tabs = $(obj);
		var _tabArr = [];
		_tabs.find('li').each(function(j, tab) {
			var _tab = $(tab);
			var _tabPanel = _tab.attr('data-tab');
			var _tabCls = _tab.attr('class');
			if(_tabCls && _tabCls.indexOf('select') != -1) {
				//选中对应的面板
				$('#'+_tabPanel).css('display', 'block');
			}
			_tab.click(function() {
				$.each(JUtil.tabs.arr, function(key, obj) {
					if(obj.value.indexOf(_tabPanel)!=-1) {
						obj.key.find('li').each(function(r, tab) {
							if($(tab).attr('data-tab') == _tabPanel) {
								$(tab).addClass('select');
							} else {
								$(tab).removeClass('select');
							}
						});
						$.each(obj.value.split(','), function(h, val) {
							$('#'+val).css('display', 'none');
						});
						$('#'+_tabPanel).fadeIn(300);
					}
				});
			});
			_tabArr.push(_tabPanel);
		});
		JUtil.tabs.arr.push({key:_tabs,value:_tabArr.join(',')});
	});
	//按钮组点击样式
	$('.jutil-btns').find('.btn').each(function(i, _this) {
		var _selCls = 'btn-' + $(_this).parent().attr('data-select');
		var _defCls = 'btn-' + $(_this).parent().attr('data-def');
		$(_this).click(function() {
			$(_this).parent().find('.btn').each(function(i, obj) {
				$(obj).removeClass(_selCls);
				$(obj).addClass(_defCls);
			});
			$(_this).removeClass(_selCls);
			$(_this).removeClass(_defCls);
			$(_this).addClass(_selCls);
		});
	});
});

//采用的popover形式
JUtil.window = {
		//关闭
		close : function(id) { $('#'+id).popover('toggle'); },
		//打开页面 attr:{url,title,width,height,scroll, placement}
		openIframe : function(id, attr) {
			if($('#'+id).attr('data-show')==='1') return;
			if(!attr.initStatus) attr.initStatus = 'toggle';
			if(!attr.scroll) attr.scroll = 'auto';
			var _cont = ['<iframe src="',attr.url,'" width="',(attr.width===undefined?'500':attr.width),
			             'px" height="',(attr.height===undefined?'300':attr.height),'px" scrolling="',attr.scroll,'" frameborder="0"></iframe>'];
			var _popover = $('#'+id).attr('data-show','1').popover({
				html:true,
				title:attr.title,
				trigger:attr.trigger,
				//title:'<span class="left">'+attr.title+'</span> <a href="javascript:;" class="close" onclick="$(\'#'+id+'\').popover(\'toggle\')">&times;</a>',
				content:_cont.join(''),
				container: attr.container,
				selector: attr.selector,
				placement:attr.placement
			});
			if(attr.initStatus!='hide') _popover.popover(attr.initStatus);
		},
		//打开Div页面 attr:{content,title,width,height}
		openDiv : function(id, attr) {
			if($('#'+id).attr('data-show')==='1') return;
			if(!attr.initStatus) attr.initStatus = 'toggle';
			var _id = 'windowOpendiv_' + JUtil.date.getTime() + parseInt(Math.random()*100);
			var _cont = ['<div id="',_id,'" style="width: ',(attr.width===undefined?'500':attr.width),'px;',(attr.height===undefined?'':'height:'+attr.height+'px;'),'font-size: 12px;overflow: auto;">',attr.content,'</div>'];
			var _popover = $('#'+id).attr('data-show','1').popover({
				html:true,
				title:attr.title,
				trigger:attr.trigger,
				//title:'<span class="left">'+attr.title+'</span> <a href="javascript:;" class="close" onclick="$(\'#'+id+'\').popover(\'toggle\')">&times;</a>',
				content:_cont.join(''),
				container: attr.container,
				selector: attr.selector,
				placement:attr.placement
			});
			if(attr.initStatus!='hide') _popover.popover(attr.initStatus);
		}
};

JUtil.ajax = function(attr) {
	if(attr.type===undefined) attr.type = 'post';
	if(attr.dataType===undefined) attr.dataType = 'json';
	if(attr.async===undefined) attr.async = true;
	//if(attr.bfsMsg===undefined) attr.bfsMsg = '提交信息中...';
	//if(attr.errMsg===undefined) attr.errMsg = '处理信息出错了';
	if(attr.showSucMsg===undefined) attr.showSucMsg = true;
	if(attr.sucMsg===undefined) attr.sucMsg = '处理信息成功了';
	jQuery.ajax({
		url : attr.url,
		data : attr.data,
		type : attr.type,
		async : attr.async,
		dataType : attr.dataType,
		beforeSend: function(){ if(attr.bfsMsg) loading(attr.bfsMsg); else if(attr.beforeSend) attr.beforeSend(); },
		error : function(json){ if(attr.errMsg) alert(attr.errMsg); else if(attr.error) attr.error(); unloading(); },
		success : function(json){
			if(attr.success) {
				attr.success(json);
			} else {
				if(json.result === 'success') { if(attr.showSucMsg) alert(attr.sucMsg); if(attr.succCallback) attr.succCallback(json); }
				else if(json.result === 'error') alert(JUtil.msg.ajaxErr);
				else alert(json.msg);
			}
			unloading();
		}
	});
};
//Date的操作方法
JUtil.date = {
		//获取当前时间
		getDate : function() { return new Date(); },
		//获取时间戳 [date: 代表传入的日期]
		getTime : function(date) { if(date === undefined) { return JUtil.date.getDate().valueOf(); } return date.valueOf(); },
		//根据指定格式获取日期[默认格式为: yyyy-MM-dd HH:mm:ss] [date  : 日期 format: 日期格式]
		formatStr : function(date, format) {
			try {
				if(date===undefined || date==='') return '';
				if(typeof(date)==='string') return date;
				if(typeof(date)==='number') date = new Date(date);
				if(format === undefined) { format = "yyyy-MM-dd HH:mm:ss"; }
				var z = { y:date.getFullYear(), M:date.getMonth() + 1, d:date.getDate(), H:date.getHours(), m:date.getMinutes(), s:date.getSeconds() };
				return format.replace(/(y+|M+|d+|H+|m+|s+)/g, function(v) {
					return ((v.length > 1 ? "0" : "") + eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2));
				});
			} catch(e) {
				return date;
			}
		},
		//将字符串时间转为Date [dateStr : 字符串时间 format  : 字符串格式[目前支持(yyyy-MM-dd HH:mm:ss)] ]
		formatDate : function(dateStr, format) {
			try {
				dateStr = Date.parse(dateStr.replace(/-/g, "/"));
				var _date = new Date(dateStr);
				return _date;
			} catch(e) {
				return dateStr;
			}
		},
		//比较时间大小[-1: date1 < date2 / 0: date1 = date2 / 1: date1 > date2]
		compareDate : function(date1, date2) {
			var _datetime1 = date1.getTime();
			var _datetime2 = date2.getTime();
			if(_datetime1 < _datetime2) { return -1; }
			else if(_datetime1 === _datetime2) { return 0; }
			else if(_datetime1 > _datetime2) { return 1; }
		},
		//获取指定时间加上指定的月数 [date:日期 month:加上的月数]
		getDateAddMonth : function(date, month) {
			if(date === undefined || date === '') { date = JUtil.date.getDate(); }
			if(month === undefined || month === '') { month = 0; }
			date.setMonth(date.getMonth() + month);
			return _date;
		},
		//获取指定时间加上指定的小时数 [date:日期 hour:加上的小时]
		getDateAddHour : function(date, hour) {
			if(date === undefined || date === '') { date = dateUtil.getDate(); }
			if(hour === undefined || hour === '') { hour = 0; }
			date.setHours(date.getHours() + hour);
			return date;
		},
		//获取指定时间加上指定的分钟数 [date:日期 min:加上的分钟]
		getDateAddMin : function(date, min) {
			if(date === undefined || date === '') { date = dateUtil.getDate(); }
			if(min === undefined || min === '') { min = 0; }
			date.setMinutes(date.getMinutes() + min);
			return date;
		}
};

function alert(msg, attr) {
	if(attr===undefined) attr = {};
	if(attr.title===undefined) attr.title = '友情提示';
	var _id = 'alert_'+JUtil.date.getTime();
	var _cont = ['<div class="modal fade" id="',_id,'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">',
	             '<div class="modal-dialog modal-sm">',
	             '<div class="modal-content">',
	             '<div class="modal-header">',
	             '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>',
	             '<h4 class="modal-title">',attr.title,'</h4>',
	             '</div>',
	             '<div class="modal-body">',msg,'</div>',
	             '<div class="modal-footer">',
	             '<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>',
	             '</div></div></div></div>'];
	parent.$('body').append(_cont.join(''));
	var _windowAlert = parent.$('#'+_id);
	_windowAlert.modal('show');
	_windowAlert.on('hidden.bs.modal', function (e) {
		//移除modal
		_windowAlert.remove();
	});
}
function message(msg, attr) {
	if(attr===undefined) attr = {};
	if(!attr.closeTime) attr.closeTime = 2;
	var _id = 'message_'+JUtil.date.getTime();
	var _cont = ['<div class="modal fade" id="',_id,'" role="dialog" tabindex="-1" aria-labelledby="" data-backdrop="false" aria-hidden="true">',
	             '<div class="modal-dialog modal-sm">',
	             '<div class="modal-content">',
	             '<div class="modal-body"><p class="text-success">',msg,'</p>',
	             '<div style="text-align:center;font-size: 12px;color: #666666;padding-top: 10px;"><span id="time',_id,'">',attr.closeTime,'</span>秒后 <a href="javascript:dialog.close(\'',_id,'\')">关闭</a></div>',
	             '</div></div></div></div>'];
	parent.$('body').append(_cont.join(''));
	var _windowMessage = parent.$('#'+_id);
	_windowMessage.modal('show');
	_windowMessage.on('hidden.bs.modal', function (e) {
		//移除modal
		_windowMessage.remove();
		if(attr.isRefresh) location.reload();
	});
	var _interval = setInterval(closeFn, 1000);
	function closeFn() {
		attr.closeTime --;
		$('#time'+_id).html(attr.closeTime);
		if(attr.closeTime <= 0) {
			clearInterval(_interval);
			dialog.close(_id);
		}
	}
}
function loading(msg) {
	//var _loading = $('.loading');
	//if(_loading) _loading.remove();
	//$('body').append(['<div class="loading progress progress-striped active"><div class="bar" style="width: 100%;">',msg,'</div></div>'].join(''));
}
function unloading() {
	//$('.loading').remove();
}
//弹出窗体
dialog.id = undefined;
function dialog(attr) {
	if(attr===undefined) attr = {};
	if(attr.title===undefined) attr.title = '友情提示';
	if(!attr.scroll) attr.scroll = 'auto';
	//移除之前打开的modal
	$('.modal').remove();
	var _content = undefined;
	var _id = 'dialog_'+JUtil.date.getTime();
	if(attr.content)
		_content = '<div>'+attr.content+'</div>';
	else if(attr.url) {
		var _param = '';
		if(attr.url.indexOf('?')!=-1)
			_param = '&dialogId='+_id;
		else
			_param = '?dialogId='+_id;
		_content = '<iframe src="'+attr.url+_param+'" width="100%" height="'+(attr.height-60)+'px" scrolling="'+attr.scroll+'" frameborder="0"></iframe>';
	}
	var _cont = ['<div class="modal fade text-center" id="',_id,'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">',
	             '<div class="modal-dialog" style="width: ',attr.width,'px;height: ',(attr.height-60),'px;display: inline-block;">',
	             '<div class="modal-content">',
	             '<div class="modal-header">',
	             '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>',
	             '<h4 class="modal-title" id="myModalLabel">',attr.title,'</h4>',
	             '</div>',
	             '<div class="modal-body" style="text-align: left;">',_content,'</div>'];
	if(attr.btns) {
		_cont.push('<div class="modal-footer">');
		if(attr.btns.close) {
			if(!attr.btns.close.click) attr.btns.close.click = '';
			_cont.push('<button type="button" class="btn btn-default btn-sm" onclick="',attr.btns.close.click,'" data-dismiss="modal">关闭</button>');
		}
		if(attr.btns.save) {
			if(!attr.btns.save.click) attr.btns.save.click = '';
			_cont.push('<button type="button" class="btn btn-success btn-sm" onclick="',attr.btns.save.click,'">保存</button>');
		}
		_cont.push('</div>');
	}
	_cont.push('</div></div></div>');
	
	parent.$('body').append(_cont.join(''));
	var _windowDialog = parent.$('#'+_id);
	_windowDialog.modal('show');
	_windowDialog.on('hidden.bs.modal', function (e) {
		//移除modal
		_windowDialog.remove();
	});
	dialog.id = _id;
}
//关闭弹出
dialog.close = function(id) {
	if(id===undefined) id = dialog.id;
	if(id) {
		$('#'+id).modal('hide');
	}
};

//面板的展示与隐藏
$.fn.panelToggle = function(g) {
	$(this).toggle(300);
	var _input = $(this).find('input[type="text"]');
	if(_input.length > 0) {
		_input[0].focus();
	}
};

//绑定按回车执行指定的事件
$(function() {
	window.setTimeout(function() {
		$('.enter-panel').each(function(i, obj) {
			var _fn = $(obj).find('.enter-fn');
			$(obj).find('input[type="text"]').each(function(j, input) {
				if(j === 0) {
					$(input).focus();
				}
				$(input).keydown(function(e) {
			        var key = e.which;
			        if (key == 13) _fn.click();
			    });
			});
			$(obj).find('input[type="password"]').each(function(j, input) {
				$(input).keydown(function(e) {
			        var key = e.which;
			        if (key == 13) _fn.click();
			    });
			});
		});
	}, 500);
});