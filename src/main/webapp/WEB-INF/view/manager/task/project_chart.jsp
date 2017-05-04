<%@page import="com.task.schedule.comm.enums.JobStatus"%>
<%@page import="com.task.schedule.comm.enums.Boolean"%>
<%@page import="com.task.schedule.comm.enums.ServInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-项目图表</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="project"/>
			<jsp:param name="second" value="projectChart"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">任务管理 / <b>项目图表</b></div>
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
						<div id="projectJobPanel" style="width: 100%;height:400px;margin-top: 20px;"></div>
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
		projectsJson: ${projectsJson},
		projectJob : function() {
			//基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('projectJobPanel'));
			var _xAxisData = [];

			var _seriesTotalData = [];
			var _seriesNormalData = [];
			var _seriesStopData = [];
			//得到项目
			var _projects = [];
			$.each(info.projectsJson, function(i, obj) {
				if(_projects.indexOf(obj.projectname) === -1) {
					_projects.push(obj.projectname);
					//设置坐标的名称
					_xAxisData.push(obj.projectname);
				}
			});
			
			$.each(_projects, function(i, projectname) {
				var _totalNum = 0;
				var _normalNum = 0;
				var _stopNum = 0;
				$.each(info.projectsJson, function(i, prj) {
					if(projectname == prj.projectname) {
						_totalNum += prj.total;
						if(prj.status === <%=JobStatus.NORMAL.getCode() %>) {
							_normalNum = prj.total;
							//代表continue
							return true;
						} else if(prj.status === <%=JobStatus.STOP.getCode() %>) {
							_stopNum = prj.total;
							return true;
						}
					}
				});
				//设置总任务数值
				_seriesTotalData.push(_totalNum);
				//设置正常值
				_seriesNormalData.push(_normalNum);
				//设置停止值
				_seriesStopData.push(_stopNum);
			});
			
			// 指定图表的配置项和数据
			var option = {
			    title: { text: '项目任务情况' },
			    tooltip: {
			    	trigger: 'axis',
                    textStyle: { fontSize: '30px' }
			    },
			    legend: {
			        data:['总任务数', '正常任务数', '停止任务数']
			    },
			    xAxis: {
			    	/*设置倾斜 axisLabel: {
			    		rotate: 60,
			    	}, */
			    	data: _xAxisData
			        //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
			    },
			    yAxis: {},
			    series: [
					{
				        name: '总任务数',
				        type: 'bar',
				        //data: [5, 20, 36, 10, 10, 20],
				        data: _seriesTotalData,
				        itemStyle: {
				            normal: {
				                barBorderColor: '#adadad',
				                color: '#adadad'
				            },
				            emphasis: {
				                barBorderColor: '#cccccc',
				                color: '#cccccc'
				            }
				        }
				    }, {
				        name: '正常任务数',
				        type: 'bar',
				        //data: [5, 20, 36, 10, 10, 20],
				        data: _seriesNormalData,
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
				    }, {
				        name: '停止任务数',
				        type: 'bar',
				        //data: [5, 20, 36, 10, 10, 20],
				        data: _seriesStopData,
				        itemStyle: {
				            normal: {
				                barBorderColor: '#c9302c',
				                color: '#c9302c'
				            },
				            emphasis: {
				                barBorderColor: '#d9534f',
				                color: '#d9534f'
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
	info.projectJob();
});
</script>
</body>
</html>