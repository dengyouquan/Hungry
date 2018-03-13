<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<title>login</title>

<!-- main css -->
<link href="${pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/media-queries.css" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
<!-- html5.js for IE less than 9 -->
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".return").click(function(){
			history.back();
		});
		
		$(".login").click(function(){
			if( $(this).attr("value")=="用户登录"){
				var href = $(this).attr("value");
				$("form").attr("action","userlogin").submit();
			}
			if( $(this).attr("value")=="商家登录"){
				var href = $(this).attr("value");
				$("form").attr("action","merchantlogin").submit();
			}
			if( $(this).attr("value")=="管理员登录"){
				var href = $(this).attr("value");
				$("form").attr("action","adminlogin").submit();
			}
			return false;
		});
	});
</script>

<body>
	
	<div id="pagewrap">
		<header id="header">
			<hgroup>
				<h1 id="site-logo"><a href="#">外卖Demo</a></h1>
				<h2 id="site-description">Site Description：this is a take-out order web site for some people</h2>
			</hgroup>
	
			<nav>
				<ul id="main-nav" class="clearfix">
					<li><a href="#">Home</a></li>
					<li><a href="#">Order</a></li>
				</ul>
				<!-- /#main-nav --> 
			</nav>
	
			<form id="searchform">
				<input type="search" id="s" placeholder="Search">
			</form>
	
		</header>
		<!-- /#header -->
		
		<div id="content">
	
			<br/><br/>
			<a class="return" href="">返回上一页</a>
			<br/><br/>
	
			<h3>欢迎来到Hungry</h3>
			<%-- <p>${requestScope.login }</p> --%>
			<span>login </span>
			<%=session.getAttribute("login") %>
			<p class="p"></p>
			<form action="userlogin">
				用户名：<input name="account"/><br/><br/>
				密  码 ：<input name="password"/><br/><br/>
				&nbsp;
				<input class="login"  value="用户登录" type="submit" name="用户登录">&nbsp;&nbsp;
				<input class="login"  value="商家登录" type="submit" name="商家登录">&nbsp;&nbsp;
				<input class="login"  value="管理员登录" type="submit" name="管理员登录"><br/><br/>
			</form>
			<br/><br/>
			<a href="visitorMessage/-1/query">查看商品</a><br/><br/>
	
		</div>
		<!-- /#content --> 
		
		
		<aside id="sidebar">
	
			<section class="widget">
				<h4 class="widgettitle">推荐食品</h4>
				<ul>
					<li><a href="#">黄焖鸡</a> (33)</li>
					<li><a href="#">可乐鸭</a> (23)</li>
					<li><a href="#">排骨饭 </a>(18)</li>
				</ul>
			</section>
			<!-- /.widget -->
	
			<section class="widget clearfix">
				<h4 class="widgettitle">推荐商家</h4>
				<ul>
					<li><a href="#">味道名家</a> (321)</li>
					<li><a href="#">牙尖帮</a> (232)</li>
					<li><a href="#">肯德基 </a>(111)</li>
				</ul>
			</section>
			<!-- /.widget -->
							
		</aside>
		<!-- /#sidebar -->
	
		<footer id="footer">
					 <p>Copyright all 2015.Company name All rights reserved.</a></p>
		</footer>
		<!-- /#footer --> 
		
	</div>
	<!-- /#pagewrap -->
	<!-- <a href="user">注册</a><br/><br/>
	<a href="users">显示用户</a><br/><br/>
	<a href="user">新加用户</a><br/><br/>
	<a href="orders">显示订单</a><br/><br/>
	<a href="order">新加订单</a><br/><br/>
	<a href="merchants">显示商家</a><br/><br/>
	<a href="merchant">新加商家</a><br/><br/>
	<a href="goodss">显示商品</a><br/><br/>
	<a href="goods">新加商品</a><br/><br/> -->
</body>
</html>