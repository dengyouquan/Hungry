<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>userList</title>
<!-- springMVC处理静态资源
<mvc:default-servlet-handler/> -->
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
	});
</script>
</head>
<body>
<br/><br/>
<a class="return" href=”“>返回上一页</a>
<br/><br/>


	<form action="" method="post">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>
	<c:if test="${empty requestScope.users }">
		没有员工信息！
	</c:if>
	<c:if test="${!empty requestScope.users }">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>id</th>
				<th>name</th>
				<th>account</th>
				<th>password</th>
				<th>age</th>
				<th>create_time</th>
				<th>address</th>
				<th>tel</th>
				<th>memo</th>
				<th>delete</th>
				<th>edit</th>
			</tr>
			
			<c:forEach items="${requestScope.users }" var="user" >
				<tr>
					<td>${user.id }</td>
					<td>${user.name }</td>
					<td>${user.account }</td>
					<td>${user.password }</td>
					<td>${user.age }</td>
					<td>${user.createTime }</td>
					<td>${user.address }</td>
					<td>${user.tel }</td>
					<td>${user.memo }</td>
					<td>
							<!-- 删除需要   js   把超链接的Get请求转为POST请求
							在用web.xml中的HiddenHttpMethodFilter将post转为delete请求
							EmployeeHandler类中的delete方法delete -->
							<a class="delete" href="user/${user.id }" >delete</a>
					</td>
					<td>
							<a href="user/${user.id }" >edit</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br/>
	<a href="user">add new  User</a>
	<br/>
</body>
</html>