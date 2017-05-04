/**
 * 获取Page对象
 * _page		  : 创建的Page对象的名称
 * _callback	  : 查询的回调函数
 * _contentId     : 显示数据的内容ID
 * _pageFootId    : 显示page的操作ID
 */
var Page = function(_page, _callback, _contentId, _pageFootId) {
	this.pageName = _page;
	this.callback = _callback;
	this.contentId = _contentId;
	this.pageFootId = _pageFootId;
};

//样式集合[值于样式文件的名称一致]
Page.prototype.style = {
		def : 'default',
		concise : 'concise',
		conciseBoot : 'conciseBoot'
};
//上一页 下一页等的显示
Page.prototype.showContent = { up : "上一页", down : "下一页" };
//当前页[默认为第一页]
Page.prototype.page = 1;
//总页数大小
Page.prototype.countPage;
//每页大小[默认为10条]
Page.prototype.size = 10;
//总记录数
Page.prototype.countSize;
//page间距大小[默认为2,显示格式为..45 6 78..]
Page.prototype.diffNum = 2;
//开始的字符串
Page.prototype.beginString;
//结束字符串
Page.prototype.endString;
//每页大小切换的值[默认为: 10 20 30]
Page.prototype.sizeArr = [10, 20, 30];

/**
 * 给控件赋值
 * @param _name : 控件名称
 * @param _val  : 赋值内容
 */
Page.prototype.setWidget = function(_name, _val) {
	if(_name != undefined && _name != "") {
		var _obj = document.getElementById(_name);
		if(_obj != undefined) {
			_obj.innerHTML = _val;
		}
	}
};

/**
 * 添加请求中方法
 * @param message : 传入的加载消息
 */
Page.prototype.beforeSend = function(message) {
	if(message === undefined)
		message = '加载信息中...';
	if(message.indexOf('</')===-1)
		message = '<span class="label label-info">' + message + '</span>';
	this.setWidget(this.contentId, message);
	this.setWidget(this.pageFootId, '');
};

/**
 * 添加失败处理结果方法
 * @param message : 传入的异常消息
 */
Page.prototype.error = function(message) {
	if(message === undefined)
		message = '加载信息异常, 请稍候再试';
	if(message.indexOf('</')===-1)
		message = '<span class="label label-danger">' + message + '</span>';
	this.setWidget(this.contentId, message);
};

/**
 * 生成分页标签的信息[上一页 下一页 尾页 等信息...]
 * @param data
 * @param attr
 * 			resultFn      : 存在数据处理的函数
 * 			dataNull      : 不存在数据的提示
 * 			footStyle 	: pageFoot的样式[default: 默认的]
 * @returns {String}
 */
Page.prototype.operate = function(json, attr) {
	var result = '';
	//处理数据
	var _bool = false;
	if(json != undefined) {
		if(json.rows != undefined && json.rows.length > 0) {
			if(this.beginString != undefined) {
				result = this.beginString;
			}
			$.each(json.rows, function(i, obj) {
				result += attr.resultFn(obj, i);
			});
			if(this.endString != undefined) {
				result += this.endString;
			}
		} else {
			if(attr.dataNull===undefined) {
				result += '<span class="label label-success">暂无记录</span>';
			} else {
				if(attr.dataNull.indexOf('</')===-1)
					result += '<span class="label label-success">' + attr.dataNull + '<span>';
				else
					result += attr.dataNull;
			}
			_bool = true;
		}
	} else {
		if(attr.dataNull===undefined) {
			result += '<span class="label label-success">暂无记录</span>';
		} else {
			if(attr.dataNull.indexOf('</')===-1)
				result += '<span class="label label-success">' + attr.dataNull + '<span>';
			else
				result += attr.dataNull;
		}
		_bool = true;
	}
	this.setWidget(this.contentId, result);
	if(_bool && this.page === 1)
		return;

	//设置总页数信息
	this.countPage = json.totalPage;
	//设置总记录数
	this.countSize = json.total;

	//根据样式生成pageFoot
	if(attr.footStyle === undefined) {
		attr.footStyle = this.style.def;
	}
	switch (attr.footStyle) {
	case this.style.def:
		//默认样式
		result = this.defaultFn();
		break;
	case this.style.concise:
		//简洁样式[上一页 下一页]
		result = this.conciseFn();
		break;
	case this.style.conciseBoot:
		//简洁样式Bootstrap风格[上一页 下一页]
		result = this.conciseBootFn();
		break;
	default:
		//默认样式
		result = this.defaultFn();
	break;
	}
	//设置pageFoot信息
	this.setWidget(this.pageFootId, result);
};

/**
 * 分页的信息[上一页 下一页 尾页 等信息...]
 * @param type
 * @param num
 */
Page.prototype.pageFun = function(type, num) {
	this.pageType = type;
	switch (type) {
	case 0:
		//首页
		this.page = 1;
		break;
	case 1:
		//上一页
		if(this.page > 1)
			this.page --;
		break;
	case 2:
		//下一页
		if(this.page < this.countPage)
			this.page ++;
		break;
	case 3:
		//末页
		this.page = this.countPage;
		break;
	case 4:
		//自定义页码
		this.page = num;
		break;
	case 5:
		//自定义每页的大小
		this.size = num;
		this.page = 1;
		break;
	}
	this.callback();
};

/**
 * 默认的样式
 * @param countPage : 总页数[单独调用需要传入]
 */
Page.prototype.defaultFn = function(countPage) {
	//处理总页数
	if(countPage != undefined) {
		this.countPage = countPage;
	}
	//设置pageFoot容器的样式
	var result = '<div class="row"><div class="col-sm-8"><ul class="pagination pagination-sm">';
	var _page = this.page;

	if(this.page > 1)
		result += '<li><a href="javascript: ' + this.pageName + '.pageFun(1)">&laquo;</a></li>';

	var startNum;
	//当前页减去距离页大于1
	if( (_page - this.diffNum) > 1 ) {
		result += '<li><a href="javascript: ' + this.pageName + '.pageFun(0)">1</a></li>';
		startNum = (_page - this.diffNum);
	} else {
		if( (_page + this.diffNum) > this.countPage )
			_page = _page + this.diffNum;
		startNum = 1;
	}
	//当前页加上距离页小于总页数
	var endResult = undefined;
	if( (_page + this.diffNum) < this.countPage ) {
		endResult = '<li><a href="javascript: ' + this.pageName + '.pageFun(3)">' + this.countPage + '</a></li>';
		if( (_page + this.diffNum) < this.countPage )
			endNum = (_page + this.diffNum);
		else
			endNum = this.countPage;
	} else {
		endNum = this.countPage;
		if( (_page + this.diffNum) < this.countPage )
			startNum = endNum - (2 * this.diffNum + 1);
	}
	//处理中间的数字
	for(var i = startNum; i <= endNum; i ++) {
		if(i != this.page) {
			result += '<li><a href="javascript: ' + this.pageName + '.pageFun(4, ' + i + ')">' + i + '</a></li>';
		} else {
			result += '<li class="active"><a href="javascript:;">' + i + '</a></li>';
		}
	}
	if(endResult != undefined)
		result += endResult;
	if(this.page < this.countPage)
		result += '<li><a href="javascript: ' + this.pageName + '.pageFun(2)">&raquo;</a></li>';
	result += '</ul></div>';
	result += '<div class="col-sm-4 text-right"><small class="text-muted">共' + this.countSize + '条记录</small></div>';
	result += '</div>';
	return result;
};

/**
 * 简洁样式[上一页 下一页]
 * @param countSize : 总记录数[单独调用需要传入]
 */
Page.prototype.conciseFn = function(countSize) {
	//处理总页数
	if(countSize != undefined) {
		this.countSize = countSize;
	}
	var result = '';
	if(this.page > 1)
		result += '<a href="javascript: ' + this.pageName + '.pageFun(1)">' + this.showContent.up + '</a>';
	//result += ' <span class="current">' + this.page + '</span> ';
	//if(this.page < this.countPage)
	var _curNum = this.size * this.page;
	if(this.countSize <= this.size) {
		_curNum = this.size;
		this.countPage = this.page + 1;
	}
	if(_curNum <= this.countSize)
		result += '<a href="javascript: ' + this.pageName + '.pageFun(2)">' + this.showContent.down + '</a>';
	return result;
};
/**
 * 简洁样式Bootstrap风格[上一页 下一页]
 * @param countSize : 总记录数[单独调用需要传入]
 */
Page.prototype.conciseBootFn = function(countSize) {
	//处理总页数
	if(countSize != undefined) {
		this.countSize = countSize;
	}
	var result = '<ul class="pager">';
	if(this.page > 1)
		result += '<li class="previous"><a class="btn btn-default btn-xs" href="javascript: ' + this.pageName + '.pageFun(1)">' + this.showContent.up + '</a></li>';
	//result += ' <span class="current">' + this.page + '</span> ';
	//if(this.page < this.countPage)
	var _curNum = this.size * this.page;
	if(this.countSize <= this.size) {
		_curNum = this.size;
		this.countPage = this.page + 1;
	}
	if(_curNum <= this.countSize)
		result += '<li class="next"><a class="btn btn-default btn-xs" href="javascript: ' + this.pageName + '.pageFun(2)">' + this.showContent.down + '</a></li>';
	result += '</ul>';
	return result;
};