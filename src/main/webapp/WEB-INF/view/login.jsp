<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		<style>
			html, body {
				height: 100%;
				margin: 0;
				display: flex;
				justify-content: center;
				align-items: center;
			}
			.wrapper {
				text-align: center;
			}
		</style>
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" action="/ticket/login">
			<fieldset>
				<legend>Login</legend>
				手機:<input type="tel" name="phonenumber" pattern="09\d{8}" placeholder="例如：0912345678" autofocus required /><p />
				密碼:<input type="password" name="password" placeholder="請輸入密碼" required /><p />
				<button type="reset" class="pure-button">重置</button>
				<button type="submit" class="pure-button pure-button-primary">登入</button>
			</fieldset>
		</form>
	</body>
</html>





























