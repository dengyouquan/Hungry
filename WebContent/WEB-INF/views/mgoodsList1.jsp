<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0">
<title>mgoodsList</title>

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
window.onload=function MyAutoRun(){
	var flag1 = "${requestScope.flag1 }";
	//alert(flag1);
	if(flag1=="user"){
		var as = document.getElementsByClassName("a");
		//alert(as);
		//alert(as[0].innerHTML);
		as[0].style.display="none";
		var b = document.getElementsByTagName("a");
		for(var i=1;i<b.length;i++){
			b[i].style.display="none";
		}
	}
	if(flag1=="merchant"){
		//alert("hidden");
		//document.getElementByClassName('a').style.display="none";
		//$(".buy").style.display="none";
	}
};
	
	$(function(){
		/* alert("start"); */
		$(".delete").click(function(){
			var href = $(this).attr("href");
			//alert(href);
			$("form").attr("action",href).submit();
			return false;
		});
		
		$(".return").click(function(){
			var flag1 = "${requestScope.flag1 }";
			//alert(flag1);
			if(flag1=="user"){
				history.back();
			}
			if(flag1=="merchant"){
				var href = $(this).attr("href");
				window.location.href=href;
			}
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
			<a class="return" href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/goods/return">返回上一页</a>
			<br/><br/>
			
				<form action="" method="post">
					<input type="hidden" name="_method" value="DELETE"/>
				</form>
				<c:if test="${empty requestScope.goodss }">
					没有商品信息！
				</c:if>
				<c:if test="${!empty requestScope.goodss }">
					<table border="1" cellpadding="10" cellspacing="0">
						<tr>
							<th>id</th>
							<th>name</th>
							<th>price</th>
							<th>putTime</th>
							<th>memo</th>
							<th>image</th>
							<th>delete</th>
							<th>edit</th>
						</tr>
						
						<c:forEach items="${requestScope.goodss }" var="goods" >
							<tr>
								<td>${goods.id }</td>
								<td>${goods.name }</td>
								<td>${goods.price }</td>
								<td>${goods.putTime }</td>
								<td>${goods.memo }</td>
								<td><img src="${pageContext.request.contextPath }/${goods.image }"/></td>
								<td>
										<a class="delete" href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/goods/${goods.id }" >delete</a>
								</td>
								<td>
										<a  class="edit" href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/goods/${goods.id }" >edit</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<br/>
				<a class="a" href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/goods">add new  goods</a>
				<br/>
				<%-- <br/><br/>
					<a href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/goods/return">return</a>
					<br/> --%>
	
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