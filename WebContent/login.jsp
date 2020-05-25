<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="css/base_style.css">
		<link rel="stylesheet" href="css/center_box.css">
		<title>Login</title>
		</head>
	<body>
		<div class="outer">
			<div class = "inner">
				<p class="box-title">WebApi</p>
				<div class="inputbox">
					<c:if test="${fn:length(error) > 0}" ><p class="error">${error}</p></c:if>
					<form action="Login" method="post" class="frm" >
						<p>ログインID</p>
						<input type="text" name="loginid" value="${id}">
						<p>パスワード</p>
						<input type="password" name="password">
						<br><br>
						<input type="submit" name="submit" value="ログイン">
					</form>
				</div>
			</div>
		</div>
	</body>
</html>