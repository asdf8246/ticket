<%@ page import="ticket.model.dto.EventDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${ eventDto.eventName }</title>
		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		<link rel="stylesheet" href="/ticket/css/layout.css">
		
		 <style>

	    </style>
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div>
			<img src="<c:url value='/image?id=${eventDto.eventId}&timestamp=${System.currentTimeMillis()}' />" alt="Event Image" class="large-image" />
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h2>${ eventDto.eventName }</h2>
			活動日期: ${ eventDto.eventDate }<p />
			活動地點: ${ eventDto.venue } / ${ eventDto.address }<p />
		</div>
		<form id="orderTicket" class="pure-form" method="post" action="/ticket/order/buy">
		<input type="hidden" name="eventId" value="${eventDto.eventId}">
		<input type="hidden" name="eventName" value="${eventDto.eventName}">
		<div class="pure-form" style="padding: 15px;">
			<h3>活動票券</h3>
			<fieldset class="order">
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>票種</th><th>票價</th><th>數量</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="seatCategoriesDto" items="${ seatCategoriesDto }">
						<tr>
							<td>${ seatCategoriesDto.categoryName }<input type="hidden" name="seatCategoryIds" value="${ seatCategoriesDto.seatCategoryId }"></td>
							<td><f:formatNumber value="${ seatCategoriesDto.seatPrice }" type="currency" maxFractionDigits="0" /><input type="hidden" name="seatPrices" value="${ seatCategoriesDto.seatPrice }"></td>
							<td><input type="number" name="numSeatss" style="width: 75px" min="0" max="2" value="0" required></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</fieldset>
			<button type="submit" class="button-secondary pure-button">下一步</button>
		</div>
		</form>
		
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
		<script>
	    // 當表單提交時檢查 input 是否都為 0
	    function checkInputs(event) {
	      // 獲取所有 input 元素
	      const inputs = document.querySelectorAll('input[type="number"]');
	      
	      // 檢查是否所有 input 的值都為 0
	      let allZero = true;
	      inputs.forEach(input => {
	        if (input.value !== '0') {
	          allZero = false;
	        }
	      });
	
	      // 如果所有 input 的數值都是 0，顯示提示訊息並阻止表單提交
	      if (allZero) {
	        alert('尚未選取票券！');
	        event.preventDefault();  // 阻止表單提交
	      }
	    }
	
	    // 等待頁面加載完成後設置事件監聽器
	    window.onload = function() {
	      // 假設表單 ID 是 "myForm"
	      const form = document.getElementById('orderTicket');
	      form.addEventListener('submit', checkInputs);
	    };
	   
	 	</script>
	</body>
</html>