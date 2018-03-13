<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0">
<title>buyGoodsList</title>

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
<style type="text/css">
	.mainPage{
		display: none;
	}
	.cartPage{
		display: none;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">
	window.onload=function MyAutoRun(){
		var flag = "${requestScope.flag }";
		//alert(flag);
		if(flag=="user"){
			
		}
		if(flag=="merchant"){
			var b = document.getElementsByClassName("buy");
			for(var i=0;i<b.length;i++){
				b[i].style.display="none";
			}
		}
		if(flag=="visitor"){
			var b = document.getElementsByClassName("buy");
			for(var i=0;i<b.length;i++){
				b[i].style.display="none";
			}
		}
	};
	
	$(function(){
		//不能放在里面
		window.onload=function MyAutoRun(){
			var flag = "${requestScope.flag }";
			//alert(flag);
			if(flag=="user"){
				
			}
			if(flag=="merchant"){
				alert("hidden");
				//$(".buy").style('display:none;');
			}
			if(flag=="visitor"){
				alert("hidden");
				//$(".buy").style('display:none;');
			}
		};
		
		
		//alert("start");
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action",href).submit();
			return false;
		});
		
		$(".buy").click(function(){
			var flag = "${requestScope.flag }";
			var me = $(this).parent().parent().children("#first").children("input").val();
			//alert(me);
			if(flag=="merchant"){
				alert("商人不可订购");
			}
			if(flag=="visitor"){
				alert("登录后方可订购");
			}
			if(flag=="user"){
				var href = "${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/merchant/${requestScope.merchant.id }/goods/"+me+"/buy";
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
				href="${pageContext.request.contextPath }/merchantMessage/${requestScope.merchant.id }/query/return";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}
			if(flag=="visitor"){
				href="${pageContext.request.contextPath }/visitorMessage/${requestScope.merchant.id }/query/return";
				//alert(href);
				//$("a").attr("href",href).submit();
				window.location.href=href;
			}
			return false;
		}); 
		
	function getCookie(c_name)
	{
		if (document.cookie.length>0)
		{
			c_start=document.cookie.indexOf(c_name + "=");
			if (c_start!=-1)
			{ 
				c_start=c_start + c_name.length+1;
				c_end=document.cookie.indexOf(";",c_start);
				if (c_end==-1) c_end=document.cookie.length;
				return unescape(unescape(document.cookie.substring(c_start,c_end)));
			} 
		}
		return ""
	}
	
	function setCookie(c_name,value,expiredays)
	{
		var exdate=new Date();
		exdate.setDate(exdate.getDate()+expiredays);
		document.cookie=c_name+ "=" +escape(escape(value))+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
	}

		 $(".cart").click(function(){
			var name = "${requestScope.user.id }";
			//alert(name);
			var value = getCookie(name);
			var setValue = $(".cartInput").val();
			//alert(setValue);
			if (value!=null && value!=""){
				var flag = 0;
				//alert(value);
				var arr = value.split(",");
				for(var i=0;i<arr.length;i++){
					keyvalue = arr[i].split(":");
					if(keyvalue[0]==setValue){
						flag = 1;
						var k =  parseInt(keyvalue[1]);
						//alert(keyvalue[1]);
						k+=1;
						keyvalue[1] = String(k);
						//alert(keyvalue[1]);
						arr[i] = keyvalue[0]+":"+keyvalue[1];
						//alert(arr[i]);
					}
				}
				if(flag == 0){
					value = value+","+setValue+":"+1;
					setCookie(name,value,1);
				}
				else{
					value = arr.join(",");
					setCookie(name,value,1);
				}
				
			}else {
				setCookie(name,setValue+":"+1,1);
			}
			location.reload();
			return false;
		}); 
		
		 
		 $(".clearcart").click(function(){
			 var name = "${requestScope.user.id }";
			 setCookie(name, "", -1);
			 location.reload();
			 return false;
		 });
		 
		 $(".shopcart").click(function(){
			$("#main").addClass("mainPage");
			$("#c").removeClass("cartPage");
			//var getValue =$(this).parent().parent().children("#first").children("input").val();
			//var getValue = $("#first").children("input").val();
			//alert(getValue);
			var name = "${requestScope.user.id }";
			var value = getCookie(name);
			//alert(value);
			var arr = value.split(",");
			var v = $(".num");
			for(var i=0;i<arr.length;i++){
				//alert(arr[i]);
				keyvalue = arr[i].split(":");
				//v[i].children(".num").text(keyvalue[1]);
				if(keyvalue[1]==undefined) continue;
				v[i].innerHTML=keyvalue[1];
				//v[i].text(keyvalue[1]);
				//alert(v[i].children(".num").val());
				/* for(var j=0;j<v.length;j++){
					var temp = v[j].children("#first").children("input").val();
					alert(temp);
					if(temp==keyvalue[0]){
						alert();
						v[j].children(".num").text(keyvalue[1]);
					}
				} */
				/* if(keyvalue[0]==getValue){
					
				} */
			}
			return false;
		 });
		 
		 $(".shopgoods").click(function(){
				$("#main").removeClass("mainPage");
				$("#c").addClass("cartPage");
				return false;
			 });
		 
		$(".return").click(function(){
			history.back();
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
			<a class="a" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/buy/return">返回上一页</a>
			<br/><br/>
			<div id="main">
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
							<th>订购</th>
							<th>加入购物车</th>
						</tr>
						<c:forEach items="${requestScope.goodss }" var="goods" >
							<tr>
								<td id="first">${goods.id }<input type="hidden" value="${goods.id }"></td>
								<td>${goods.name }</td>
								<td>${goods.price }</td>
								<td>${goods.putTime }</td>
								<td>${goods.memo }</td>
								<td class="buytd">
										<a class="buy" id="buy" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/merchant/${requestScope.merchant.id }/goods/${goods.id }/buy" >订购</a>
								</td>
								<td class="shopCart">
										<input type="hidden" class="cartInput" value="${goods.id }"/>
										<a class="cart" href="">加入购物车</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<br/><br/>
					<a class="shopcart" href="">购物车</a>
				<br/><br/>
					<a class="a" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/buy/return">return</a><br/><br/>
			</div>
			
			<div id="c" class="cartPage">
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
							<th>num</th>
						</tr>
						<c:forEach items="${requestScope.goodss }" var="goods" >
							<tr class="tr">
								<td id="first">${goods.id }<input type="hidden" value="${goods.id }"></td>
								<td>${goods.name }</td>
								<td>${goods.price }</td>
								<td class="num">0</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<br/><br/>
					<a class="shopgoods" href="">商品</a>
				<br/><br/>
					<a class="clearcart" href="">清空购物车</a>
				<br/><br/>
					<a class="" href="${pageContext.request.contextPath }/userMessage/${requestScope.user.id }/merchant/${requestScope.merchant.id }/goods/buys">下单</a>
				<br/><br/>
			</div>
	
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