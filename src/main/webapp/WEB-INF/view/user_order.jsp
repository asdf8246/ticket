<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User Order</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
	</head>
	<body>
	<!-- menu bar include -->
	<%@include file="/WEB-INF/view/menu.jspf" %>
	
		<div class="pure-form" style="padding: 15px;">
			<fieldset>
				<legend>歷史訂單</legend>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>活動名稱</th><th>票券總價</th><th>建立日期</th><th>訂單狀態</th>
						</tr>
					</thead>
					<c:forEach var="orderDtos" items="${ orderDtos }">
						<tr>
							<td>${ orderDtos.eventName }</td>
							<td><f:formatNumber value="${ orderDtos.orderPrice }" type="currency" maxFractionDigits="0" /></td>
							<td>${ orderDtos.orderDate }</td>
							<td>${ orderDtos.orderStatus }</td>
							<td>
								<a href="/ticket/user/order/view?orderId=${ orderDtos.orderId }" class="button-secondary pure-button">查看訂單</a><p />
								<a href="/ticket/event/view?eventId=${ orderDtos.eventId }" class="button-success pure-button">活動頁面</a><p />
								<!-- 根據 orderStatus 判斷是否顯示刪除訂單按鈕 -->
                        		<c:if test="${ orderDtos.orderStatus == 'paid' }">
									<a href="/ticket/order/cancel?orderId=${ orderDtos.orderId }" id="deleteOrder"  onclick="return confirmDelete('${ orderDtos.orderId }');" class="button-error pure-button">取消訂單</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
		</div>
	<script type="text/javascript">
    // 確認刪除操作的函數
    function confirmDelete(orderId) {
        // 顯示確認對話框
        const confirmation = confirm("確定要取消這個訂單嗎？");
        
        // 如果用戶選擇「確定」，則執行刪除操作
        if (confirmation) {
            // 執行刪除操作，這裡可以根據實際情況改為表單提交或其他操作
            // 這裡的 href 是使用 JavaScript 改為刪除請求的 URL
            window.location.href = '/ticket/order/cancel?orderId=' + orderId;
        }
        
        // 如果用戶選擇「取消」，則不執行任何操作，返回 false 阻止鏈接的默認行為
        return false;
    }
	</script>
	</body>
</html>