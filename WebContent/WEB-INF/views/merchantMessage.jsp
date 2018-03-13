<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<title>merchantMessage</title>

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
<script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">
	
</script>
<style type="text/css">
	.main{
		/* color: #000000; */
		color: green;
		font-size: large;
		font-family: cursive;
	}
</style>
</head>
<body>
	<div id="pagewrap">
		<header id="header">
			<hgroup>
				<h1 id="site-logo"><a href="#">外卖Demo</a></h1>
				<h2 id="site-description">Site Description：this is a take-out order web site for some people</h2>
			</hgroup>
	
			<div class="login">
				<a href="login">登录</a>
				<a href="register">注册</a>
			</div>
	
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
			<a href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/return">返回上一页</a>
			<br/><br/>
				<c:if test="${empty requestScope.merchant }">
					没有商家信息！
				</c:if>
				<c:if test="${!empty requestScope.merchant }">
					<div class="main">
						<p>id:${requestScope.merchant.id }</p>
						<p>name:${requestScope.merchant.name }</p>
						<p>account:${requestScope.merchant.account }</p>
						<p>loginTime:${requestScope.merchant.loginTime }</p>
						<p>address:${requestScope.merchant.address }</p>
						<p>tel:${requestScope.merchant.tel }</p>
						<p>memo:${requestScope.merchant.memo }</p>
					</div>
					<%-- <a href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/return">return</a>
					<br/> --%>
				</c:if>
				<br/>
				<a href="${requestScope.merchant.id }/goodss">show all goods</a>
				<br/><br/>
				<a href="${requestScope.merchant.id }/goods">add a goods</a>
				<br/>
				<!-- <a href="">update a goods</a>
				<br/>
				<a href="">delete a goods</a>
				<br/> -->
	
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

</body>
</html>