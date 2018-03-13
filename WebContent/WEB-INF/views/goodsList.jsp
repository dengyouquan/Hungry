<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>goodsList</title>
<script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action",href).submit();
			return false;
		});
	});
</script>
</head>
<body>
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
				<th>img</th>
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
					<td>${goods.image }</td>
					<td>
							<a class="delete" href="goods/${goods.id }" >delete</a>
					</td>
					<td>
							<a href="goods/${goods.id }" >edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br/>
	<a href="goods">add new  goods</a>
	<br/>
	<br/><br/>
		<a href=”“>return</a><br/><br/>
</body>
</html>