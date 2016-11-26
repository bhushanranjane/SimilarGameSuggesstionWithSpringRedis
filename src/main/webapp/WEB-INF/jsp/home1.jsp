<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to PlayStore</title>
<body>
	<form:form action="homepage" commandName="validate" method="post">
		<h3>Google PlayStore</h3>
		Search Game<input type="text" name="gameName"> <input type="submit" value="Search"/>
		<form:errors path="gameName" />
		<br>
		<!-- Image:<input type="file" name="image" size="50" /> <br /> 
		<br> -->
		</form:form>
</body>