<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>orderInput</title>
</head>
<body>
	<form:form action="${pageContext.request.contextPath }/order" method="POST" modelAttribute="order" >
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
		<a href=”“>return</a><br/><br/>
	</form:form>
</body>
</html>