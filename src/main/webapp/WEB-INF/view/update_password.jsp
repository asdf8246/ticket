<%@ page import="ticket.model.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密碼</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		
		<style>
			.updatePassword{
				 display: flex;
				 justify-content: center;  /* 水平居中 */
				 align-items: center;      /* 垂直居中 */
				 height: 80vh;            /* 使 body 高度充滿整個視窗 */
				 margin: 0;                /* 移除 body 的默認邊距 */
			}
			form {
			    background-color: #f1e5e5dc;  /* 可選：背景顏色 */
			    padding: 20px;             /* 可選：內邊距 */
			    border-radius: 8px;        /* 可選：圓角邊框 */
			    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 可選：陰影 */
			}
			.buttons-container {
				display: flex;
				justify-content: space-between; /* 在父容器中将按钮分别对齐到左右 */
			}
		</style>
	</head>
	<body>
	<!-- menu bar include -->
	<%@include file="/WEB-INF/view/menu.jspf" %>
	
		<div style="padding: 15px" class="updatePassword">
			<form class="pure-form" method="post" action="/ticket/user/update/password">
				<fieldset>
					<legend>修改密碼</legend>
					手機: ${ userCert.userPhonenumber } <p /> 
					原密碼: <input type="text" name="oldPassword" placeholder="請輸入原密碼" required /><p /> 
					新密碼: <input type="text" name="newPassword" placeholder="請輸入新密碼" required /><p /> 
					<div class="buttons-container">
						<button type="reset" class="button-warning pure-button">重置</button>
						<button type="submit" class="button-success pure-button">修改</button>	
					</div>  
				</fieldset>
			</form>
		</div>
	</body>
</html>