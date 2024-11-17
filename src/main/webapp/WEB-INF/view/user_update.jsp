<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ticket.model.dto.UserDto" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User 修改</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
	</head>
	<body>
	<!-- menu bar include -->
	<%@include file="/WEB-INF/view/menu.jspf" %>
	
		<div style="padding: 15px;">
			<form class="pure-form" method="post" action="/ticket/user/update">
				<fieldset>
					<legend>User 修改</legend>
					序號: <input type="text" name="userId" value="${ userDto.UserId }" readonly /><p />
					帳號: <input type="text" name="username" value="${ userDto.username }" required /><p />
					手機: <input type="tel" name="phonenumber" value="${ userDto.userPhonenumber }" readonly><p />
					電郵: <input type="email" name="email" value="${ userDto.userEmail }" required /><p />
					權限: <select name="role">
							<option value="ROLE_ADMIN" ${ userDto.userRole.equals("ROLE_ADMIN")?"selected":"" }>>ADMIN</option>
							<option value="ROLE_USER" ${ userDto.userRole.equals("ROLE_USER")?"selected":"" }>>USER</option>
						 </select><p />
					<button type="submit" class="button-secondary pure-button" >Update</button>
				</fieldset>
			</form>
		</div>
	</body>
</html>