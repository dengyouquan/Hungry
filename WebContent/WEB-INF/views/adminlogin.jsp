<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>adminlogin</title>
</head>
<body>
<br/><br/>
<a href="${pageContext.request.contextPath }/<%=request.getAttribute("id") %>/return">返回上一页</a>
<br/><br/>

	<a href="users">显示用户</a><br/><br/>
	<a href="user">新加用户</a><br/><br/>
	<a href="orders">显示订单</a><br/><br/>
	<a href="order">新加订单</a><br/><br/>
	<a href="merchants">显示商家</a><br/><br/>
	<a href="merchant">新加商家</a><br/><br/>
	<a href="goodss">显示商品</a><br/><br/>
	<a href="goods">新加商品</a><br/><br/>
	<a href="${pageContext.request.contextPath }/<%=request.getAttribute("id") %>/return">return</a>
	<br/><br/>
</body>
</html>