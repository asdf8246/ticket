<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${ orderDto.eventName } 訂單</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
	</head>
	<body>
	<!-- menu bar include -->
	<%@include file="/WEB-INF/view/menu.jspf" %>
	
	<div class="pure-form" style="padding: 15px;">
		<h2>${ orderDto.eventName }</h2>
		訂單成立: ${ orderDto.orderDate }<p />
	</div>
	<div class="pure-form" style="padding: 15px;">
		<fieldset>
			<legend>訂單內容</legend>
			<table class="pure-table pure-table-bordered">
				<thead>
					<tr>
						<th>票種</th><th>序號</th><th>票價</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="orderSeatsDto" items="${ orderSeatsDto }">
					<tr>
						<td>${ orderSeatsDto.categoryName }</td>
						<td>#${ orderSeatsDto.seatNumber }</td>
						<td><f:formatNumber value="${ orderSeatsDto.seatPrice }" type="currency" maxFractionDigits="0" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</fieldset>
		票價合計: <f:formatNumber value="${ orderDto.orderPrice }" type="currency" maxFractionDigits="0" />
	</div>
	</body>
</html>