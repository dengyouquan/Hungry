<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<title>buyMerchantList</title>

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
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action",href).submit();
			return false;
		});
		
		$(".return").click(function(){
			history.back();
		});
		
		$(".query").click(function(){
			var href = $(this).attr("href");
			//alert(href);
			//DOM查找
			//var mer = document.getElementById("in").val;
			//alert(mer);
			//var m = document.getElementById("query").parentNode.parentNode.firstChild.firstChild.val;
			//alert(m);
			var me = $(this).parent().parent().children("#first").children("input").val();
			//alert(me);
			/* js中用el表达式要用""引引起，"${requestScope.flag }" 对
			${requestScope.flag }错 */
			var flag = "${requestScope.flag }";
			//alert(flag);
			if(flag=="user"){
				//alert(href);
				//$("a").attr("href",href).submit();
				//动态输出跳转
				window.location.href=href;
			}
			if(flag=="merchant"){
				href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/merchant/${merchant.id }/goodss";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}if(flag=="visitor"){
				href="${pageContext.request.contextPath }/visitorMessage/-1/merchant/"+me+"/goodss";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}
			return false;
		});
		
		$(".a").click(function(){
			var href = $(this).attr("href");
			//alert(href);
			/* js中用el表达式要用""引引起，"${requestScope.flag }" 对
			${requestScope.flag }错 */
			var flag = "${requestScope.flag }";
			//alert(flag);
			if(flag=="user"){
				//alert(href);
				//$("a").attr("href",href).submit();
				//动态输出跳转
				window.location.href=href;
			}
			if(flag=="merchant"){
				href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/return";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}
			if(flag=="visitor"){
				href="${pageContext.request.contextPath }/visitorMessage/-1/return";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}
			return false;
		}); 
		
	});
</script>
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
			<a class="a" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/return">返回上一页</a>
			<br/><br/>
		
			<form action="" method="post">
				<input type="hidden" name="_method" value="DELETE"/>
			</form>
			<c:if test="${empty requestScope.merchants }">
				没有商家信息！
			</c:if>
			<c:if test="${!empty requestScope.merchants }">
				<table border="1" cellpadding="10" cellspacing="0">
					<tr>
						<th>id</th>
						<th>name</th>
						<th>loginTime</th>
						<th>account</th>
						<th>password</th>
						<th>address</th>
						<th>tel</th>
						<th>memo</th>
						<th>查看商品</th>
					</tr>
					
					<c:forEach items="${requestScope.merchants }" var="merchant" >
						<tr>
							<td id="first">${merchant.id }
							<input type="hidden"  id="in" value="${merchant.id }" />
							</td>
							<td>${merchant.name }</td>
							<td>${merchant.loginTime }</td>
							<td>${merchant.account }</td>
							<td>${merchant.password }</td>
							<td>${merchant.address }</td>
							<td>${merchant.tel }</td>
							<td>${merchant.memo }</td>
							<td >
									<a class="query" id="query" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/merchant/${merchant.id }/goodss" >查看商品</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<br/><br/>
				<a class="a" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/return">return</a>
				<br/><br/>
			
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