<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>orderList</title>
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
	<c:if test="${empty requestScope.orders }">
		没有订单信息！
	</c:if>
	<c:if test="${!empty requestScope.orders }">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>id</th>
				<th>number</th>
				<th>allPrice</th>
				<th>startTime</th>
				<th>status</th>
				<th>memo</th>
				<th>delete</th>
				<th>edit</th>
			</tr>
			
			<c:forEach items="${requestScope.orders }" var="order" >
				<tr>
					<td>${order.id }</td>
					<td>${order.number }</td>
					<td>${order.allPrice }</td>
					<td>${order.startTime }</td>
					<td>${order.status }</td>
					<td>${order.memo }</td>
					<td>
							<a class="delete" href="order/${order.id }" >delete</a>
					</td>
					<td>
							<a href="order/${order.id }" >edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br/>
	<a href="order">add new  order</a>
	<br/>
	<br/><br/>
	<%-- ${requestScope.uid }
	<p>${requestScope.oid }</p>
	${requestScope.mid }
	${sessionScope.uid }
	<p>${sessionScope.oid }</p>
	${sessionScope.mid } --%>
	<%-- ${uid }
	<p>${oid }</p>
	${mid } --%>
		<a href="${pageContext.request.contextPath }/userMessage/${uid }/merchantMessage/${mid }/orders/${oid }/return">return</a><br/><br/>
</body>
</html>