<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/favorite.css">
		<title>Favorite</title>
	</head>
	<body>
	<%
		if(request.getParameter("id") != null){
			request.setAttribute("id", request.getParameter("id"));
		}
		
		if(request.getParameter("favorite") != null){
			request.setAttribute("favorite", request.getParameter("favorite"));
		}
		
		if(request.getParameter("css") != null){
			request.setAttribute("css", request.getParameter("css"));
		}
		
	%>
	
	<form action="Favorite" method="post">
		<input name="shop_id" type="hidden" value="${id}">
		<input name="fav_flg" type="hidden" value="${favorite}">
		<input name="action" type="hidden" value="favorite">
		<input class="fav_button ${css}" type="submit" value="">
	</form>
	</body>
</html>