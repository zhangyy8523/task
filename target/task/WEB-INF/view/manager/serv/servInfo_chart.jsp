<%@page import="com.task.schedule.comm.enums.Boolean"%>
<%@page import="com.task.schedule.comm.enums.ServInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-服务图表</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="project"/>
			<jsp:param name="second" value="servInfoChart"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">任务管理 / <b>服务图表</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<!-- <span class="enter-panel">
								<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="projectName" placeholder="项目名称">
					  			<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span> -->
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel">
						<div id="servJobPanel" style="width: 100%;height:400px;margin-top: 20px;"></div>
					</div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/chart.jsp"></jsp:include>
<script type="text/javascript">
var info = {
		servInfosJson: ${servInfosJson},
		servJob : function() {
			//基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('servJobPanel'));
			var _xAxisData = [];
			var _seriesData = [];
			var _seriesData2 = [];
			$.each(info.servInfosJson, function(i, obj) {
				//设置坐标的名称
				_xAxisData.push(obj.servid.substring(0, 4) + '...' + obj.servid.substring(obj.servid.lastIndexOf('-') - 2) + (obj.isleader==1?'(Leader)':''));
				//设置值
				_seriesData.push(obj.jobnum);
				_seriesData2.push((obj.isleader==1?obj.jobnum:0));
			});
			// 指定图表的配置项和数据
			var option = {
			    title: { text: '服务任务情况' },
			    tooltip: {},
			    legend: {
			        data:['服务任务数']
			    },
			    xAxis: {
			    	/*设置倾斜 axisLabel: {
			    		rotate: 60,
			    	}, */
			    	data: _xAxisData
			        //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
			    },
			    yAxis: {},
			    series: [{
			        name: '服务任务数',
			        type: 'bar',
			        //data: [5, 20, 36, 10, 10, 20],
			        data: _seriesData,
			        itemStyle: {
			            normal: {
			                barBorderColor: '#449d44',
			                color: '#449d44'
			            },
			            emphasis: {
			                barBorderColor: '#5cb85c',
			                color: '#5cb85c'
			            }
			        }
			    }
			    ]
			};

			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		}
};
$(function() {
	info.servJob();
});
</script>
</body>
</html>