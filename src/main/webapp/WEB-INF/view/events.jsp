<%@ page import="ticket.model.dto.EventDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Event</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/menu.jspf" %>
		
		<div class="pure-form">
				<fieldset>
					<legend>Event 列表</legend>
					<table class="pure-table pure-table-bordered">
						<thead>
							<tr>
								<th>活動ID</th><th>活動名稱</th><th>活動日期</th><th>活動地點</th><th>活動簡介</th>
								<th>修改</th><th>刪除</th>
							</tr>
						</thead>
						<c:forEach var="eventDto" items="${ eventDtos }">
							<tr>
								<td>${ eventDto.eventId }</td>
								<td>${ eventDto.eventName}</td>
								<td>${ eventDto.eventDate }</td>
								<td>${ eventDto.venue }</td>
								<td>${ eventDto.description }</td>
								<td><a href="/ticket/user/get?eventId=${ eventDto.eventId }" class="button-secondary pure-button">修改</a></td>
								<td><a href="/ticket/user/delete?userId=${ eventDto.eventId }" class="button-error pure-button">刪除</a></td>
							</tr>
						</c:forEach>
					</table>
				</fieldset>
			</div>
	</body>
</html>