<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>userInput</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-3.1.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".return").click(function(){
			history.back();
		});
	});
</script>
</head>
<body>
	<!-- 为什么使用form
	1.更快速的开发表单页面
	2.更方便的进行表单值的回显 
	
	通过modelAttribute指定绑定的模型属性，
	不指定，默认从command中读取，不存在，报错
	-->
	<%-- <form:form action="emp" method="POST" modelAttribute="employee" >
	edit时有错，页面会跳转到http://localhost:8080/SpringMVC-2/emp/emp
	绝对路径与相对路径的问题
	 --%>
 <br/><br/>
<a class="return" href=”“>返回上一页</a>
<br/><br/>
	 
	<form:form action="${pageContext.request.contextPath }/user" method="POST" modelAttribute="user" >
		<%-- <!-- 显示错误消息
		path="*"显示所有路径错误消息 -->
		<form:errors path="*"></form:errors><br/> --%>
		<!-- id&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: -->
		<%-- <form:hidden path="id"/><br/><br/> --%>
		<c:if test="${user.id == null }">
			name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         :<form:input path="name"/>
		         <form:errors path="name"></form:errors><br/><br/>
		</c:if>
		<c:if test="${user.id != null }">
			<form:hidden path="id"/>
			<!-- form:hidden没有value,所以不用form，而用input -->
			<input type="hidden" name="_method" value="PUT"/>
		</c:if>
		account&nbsp;&nbsp;&nbsp;&nbsp;
		         :<form:input path="account"/>
		          <form:errors path="account"></form:errors><br/><br/>
		password&nbsp;&nbsp;&nbsp;
		          :<form:input path="password"/>
		           <form:errors path="password"></form:errors><br/><br/>
        age&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="age"/>
		           <form:errors path="age"></form:errors><br/><br/>
	    address&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="address"/>
		           <form:errors path="address"></form:errors><br/><br/>
         tel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="tel"/>
		           <form:errors path="tel"></form:errors><br/><br/>
         memo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="memo"/>
		           <form:errors path="memo"></form:errors><br/><br/>
		<%-- create_time:<form:input path="createTime" /><br/><br/> --%>
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value="submit"/>
		<br/><br/>
	</form:form>
</body>
</html>