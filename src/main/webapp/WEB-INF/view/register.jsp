<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>註冊</title>
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
			.buttons-container {
				display: flex;
				justify-content: space-between; /* 在父容器中将按钮分别对齐到左右 */
			}
		</style>
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" action="/ticket/register">
			<fieldset>
				<legend>註冊</legend>
				姓名: <input type="text" name="username" placeholder="請輸入姓名" required /><p />
				手機: <input type="tel" name="phonenumber" pattern="09\d{8}" placeholder="例如：0912345678" required><p /> 
				密碼: <input type="text" name="password" placeholder="請輸入 password" required /><p /> 
				電郵: <input type="email" name="email" placeholder="請輸入 email" required /><p />
				<div class="buttons-container">
					<button type="reset" class="pure-button">重置</button>
					<button type="submit" class="pure-button pure-button-primary">註冊</button>
				</div>
			</fieldset>
		</form>
	</body>
</html>
