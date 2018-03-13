<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>goodsInput</title>
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
<br/><br/>
<a class="return" href=”“>返回上一页</a>
<br/><br/>
		
	<form:form action="${pageContext.request.contextPath }/goods" method="POST" modelAttribute="goods" >
		<c:if test="${goods.id == null }">
			name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         :<form:input path="name"/>
		         <form:errors path="name"></form:errors><br/><br/>
		</c:if>
		<c:if test="${goods.id != null }">
			<form:hidden path="id"/>
			<!-- form:hidden没有value,所以不用form，而用input -->
			<input type="hidden" name="_method" value="PUT"/>
		</c:if>
		price&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         :<form:input path="price"/>
		          <form:errors path="price"></form:errors><br/><br/>
         memo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="memo"/>
		           <form:errors path="memo"></form:errors><br/><br/>
         image&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          :<form:input path="image"/>
		           <form:errors path="image"></form:errors><br/><br/>
		<br/>
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value="submit"/>
		<br/><br/>
	</form:form>
</body>
</html>