<%@ page import="ticket.model.dto.EventDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Event Add</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		
		<!-- 引入 flatpickr 样式 -->
   	 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    	<!-- 引入 flatpickr 脚本 -->
    	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div style="padding: 15px">
			<form class="pure-form" method="post" action="/ticket/event/add">
				<fieldset>
					<legend>Event 新增</legend>
					活動名稱: <input type="text" name="eventName" placeholder="請輸入活動名稱" required /><p />
					<label for="datetime">活動日期: </label><input type="text" id="datetime" name="eventDate" required /><p /> 
					活動地點: <input type="text" name="venue" placeholder="請輸入活動地點" required /><p /> 
					活動簡介: <textarea rows="5" cols="20" name="description"></textarea><p /> 
					<button type="reset" class="button-warning pure-button">Reset</button>
					<button type="submit" class="button-success pure-button">Submit</button>	  
				</fieldset>
			</form>
		</div>
		
		
		<script>
   			flatpickr("#datetime", {
        		enableTime: true,  // 启用时间选择
        		dateFormat: "Y-m-d H:i",  // 设置日期时间格式
   	 		});
		</script>
	</body>
</html>