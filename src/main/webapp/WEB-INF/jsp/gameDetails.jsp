<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!-- <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css"> -->
<title>Game Details</title>
<style type="text/css">
</style>
</head>
<body>

	<jsp:include page="home1.jsp"></jsp:include>
	<br>
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
	<%--<br>
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
	</table> --%>
	<br>
	
	<div class="container" >
	<div class="row">
	<c:forEach items="${model.suggestion}" var="suggestion">
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6" style="margin-top:10px;">
						<img class="card-img-top" src="${suggestion.imageUrl}" alt="Card image cap">
						<div class="card-block">
							<h4 class="card-title">${suggestion.gameName}</h4>
							<p class="card-text">${suggestion.gameCost}</p>
							<a href="${suggestion.gameUrl}" class="btn btn-primary">Go get Game</a>
						</div>
			</div>
			</c:forEach>
	</div>
	</div>

</body>
</html>