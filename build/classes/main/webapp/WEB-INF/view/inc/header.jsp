<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${webroot}/index.jsp">任务管理系统</a>
	</div>
	<div id="headerMenu" class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<li id="hmIndex"><a href="${webroot}/index.jsp">首页</a></li>
			<li id="hmHelp"><a href="${webroot}/help.jsp">帮助</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:choose>
			<c:when test="${user!=null}">
			<li id="hmMain" class="dropdown">
				<a href="${webroot}/sysUser/f_view/main.shtml">${user.nickname}</a>
			</li>
			<li id="hmExit"><a href="${webroot}/sysUser/f_view/exit.shtml">退出</a></li>
			</c:when>
			<c:otherwise>
			<li id="hmLogin"><a href="${webroot}/index.jsp">登录</a></li>
			</c:otherwise>
			</c:choose>
		</ul>
	</div>
	</div>
</div>