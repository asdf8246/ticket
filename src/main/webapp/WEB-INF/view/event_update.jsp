<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ticket.model.dto.EventDto" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Event 修改</title>
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
	
		<div style="padding: 15px;">
			<form class="pure-form" method="post" action="/ticket/event/update">
				<fieldset>
					<legend>Event 修改</legend>
					活動名稱: <input type="text" name="eventName" value="${ eventDto.eventName }" required /><p />
					<label for="datetime">活動日期: </label><input type="text" id="datetime" name="eventDate" value="${ eventDto.eventDate }" required /><p /> 
					活動地點: <input type="text" name="venue" value="${ eventDto.venue }" required /><p /> 
					活動簡介: <textarea rows="5" cols="22" name="description"></textarea><p />
					<input type="hidden" name="eventId" value="${ eventDto.eventId }">
					<button type="submit" class="button-secondary pure-button" >Update</button>
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