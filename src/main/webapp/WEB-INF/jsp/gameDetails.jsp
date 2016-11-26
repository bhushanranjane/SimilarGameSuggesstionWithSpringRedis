<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game Details</title>
<style type="text/css">
    <%@include file="/WEB-INF/playStore.css" %>
    </style>
</head>
<body>
<h1>Game Details</h1>
<table border="1" align="center">

	<tr>
			<td>Package Id</td>
			<td>Game Name</td>
			<td>Package Name</td>
			<td>Game Category</td>
			<td>Game Url</td>
			<td>Game Cost</td>
		</tr>
	<c:forEach items="${model.game}" var="game">
		<tr>
			<td>${game.packageId}</td>	
			<td>${game.gameName}</td>	
			<td>${game.packageName}</td>
			<td>${game.gameCategory}</td>
			<td>${game.gameURL}</td>
			<td>${game.paid}</td>
		</tr>
		</c:forEach>
	</table>
	<br>
	<h2>Similar Games</h2>
	<table border="1" align="center">
	<tr>
			
			<td>Game Name</td>
			<td>Package Name</td>
			<td>Image Url</td>
			<td>Game Rating</td>
			<td>Game Cost</td>
		</tr>
		<c:forEach items="${model.suggestion}" var="suggestion">
		<tr>
			<td>${suggestion.gameName}</td>	
			<td>${suggestion.packageName}</td>
			<td>${suggestion.imageUrl}</td>
			<td>${suggestion.gameRating}</td>
			<td>${suggestion.gameCost}</td>
		</tr>
		</c:forEach>
	</table>
	<br>
	
	
</body>
</html>