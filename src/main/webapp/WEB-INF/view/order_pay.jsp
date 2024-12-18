<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${ orderDto.eventName }</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		
		<style>
		/* 將body和html設置為100%高度，並讓內容居中 */
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            display: grid;
            align-items: center;      /* 水平居中 */
            background-color: #f0f0f0;
            text-align: center; /* 讓文本也居中 */
        }

        /* 表單樣式 */
        .pure-form {
            width: 100%;        /* 讓表單寬度為100% */
            padding: 20px;
            background-color: white;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        /* 表格樣式 */
        .pure-table {
            width: 100%;
            border-collapse: collapse;
        }

        .pure-table-bordered {
            border: 1px solid #ccc;
        }

        td, th {
            padding: 8px;
            border: 1px solid #ccc;
            text-align: center;
        }
		</style>
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div class="pure-form" style="padding: 15px;">
			<h2>${ orderDto.eventName }</h2>
			訂單成立: ${ orderDto.orderDate }<p />
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h3>購票總覽</h3>
			<fieldset>
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
					<input type="hidden" name="seatIds" value="${ orderSeatsDto.seatId }">
					</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</div>
		<div class="pure-form" style="padding: 15px;">
			票價合計: <f:formatNumber value="${ orderDto.orderPrice }" type="currency" maxFractionDigits="0" /><p />
			<a href="/ticket/order/delete?orderId=${ orderDto.orderId }&eventId=${ orderDto.eventId }" class="button-error pure-button">取消</a>
			<a href="/ticket/order/finish?orderId=${ orderDto.orderId }" class="button-secondary pure-button">付款</a>
		</div>
</body>
</html>