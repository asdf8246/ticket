<%@ page import="ticket.model.dto.EventDto"%>
<%@ page import="ticket.model.dto.SeatCategoriesDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${ eventDto.eventName }</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div class="pure-form" style="padding: 15px;">
			<h2>${ eventDto.eventName }</h2>
			開始時間: ${ eventDto.eventDate }<p />
			活動地點: ${ eventDto.venue }<p />
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h3>活動介紹</h3>
			${ eventDto.description }
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h3>活動票券</h3>
			<fieldset>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>票種</th><th>票價</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="seatCategoriesDto" items="${ seatCategoriesDto }">
						<tr>
							<td>${ seatCategoriesDto.categoryName }</td>
							<td>${ seatCategoriesDto.seatPrice }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</fieldset>
			<a href="/ticket/order/buy?eventId=${ eventDto.eventId }" class="button-secondary pure-button">前往購買</a>
		</div>
	</body>
</html>