<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<title>morderInput</title>

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
			<a href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/orders/return">返回上一页</a>
			<br/><br/>
			
				<form:form action="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/order" method="POST" modelAttribute="order" >
					<c:if test="${order.id == null }">
						number&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					         :<form:input path="number"/>
					         <form:errors path="number"></form:errors><br/><br/>
					</c:if>
					<c:if test="${order.id != null }">
						<form:hidden path="id"/>
						<!-- form:hidden没有value,所以不用form，而用input -->
						<input type="hidden" name="_method" value="PUT"/>
					</c:if>
					allPrice&nbsp;&nbsp;&nbsp;
					         :<form:input path="allPrice"/>
					          <form:errors path="allPrice"></form:errors><br/><br/>
					<%-- startTime&nbsp;&nbsp;
					          :<form:input path="startTime"/>
					           <form:errors path="startTime"></form:errors><br/><br/> --%>
			       <%--  status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					          :<form:input path="status"/>
					           <form:errors path="status"></form:errors><br/><br/> --%>
			         memo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					          :<form:input path="memo"/>
					           <form:errors path="memo"></form:errors><br/><br/>
					<%-- create_time:<form:input path="createTime" /><br/><br/> --%>
					<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="submit"/>
					<br/><br/>
					<a href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/orders/return">return</a>
					<br/>
				</form:form>
	
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