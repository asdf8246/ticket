<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登入</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		<link rel="stylesheet" href="/ticket/css/layout.css">

		<style>
		</style>
	</head>
	<body style="padding: 15px">
		<div class="profile">
			<form class="pure-form profile-form" method="post" action="/ticket/login">
				<fieldset>
					<legend>登入</legend>
					<c:if test="${not empty error}">
						<p style="color:red">${error}</p>
					</c:if>
					<p>手機: <input type="tel" name="phonenumber" pattern="09\d{8}" placeholder="例如：0912345678" autofocus required /><p />
					<p>密碼: <input type="password" name="password" placeholder="請輸入密碼" required /><p />
					<p>輸入驗證碼: <img src="<c:url value='/captcha?timestamp=${System.currentTimeMillis()}' />" alt="Captcha Image"><p />
					<input type="text" id="captcha" name="captcha" style="margin-left: 43px;" required>
					<div class="buttons-container">
						<a href="/ticket/register" class="pure-button">註冊</a>
						<button type="submit" class="pure-button pure-button-primary">登入</button>
					</div>
				</fieldset>
			</form>
		</div>
	</body>
</html>





























