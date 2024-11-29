<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ticket.model.dto.EventDto" %>
<%@page import="ticket.model.dto.SeatCategoriesDto" %>
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
				<div>
					<fieldset>
						<legend>Event 修改</legend>
						活動名稱: <input type="text" name="eventName" value="${ eventDto.eventName }" required /><p />
						<label for="datetime">活動日期: </label><input type="text" id="datetime" name="eventDate" value="${ eventDto.eventDate }" required /><p /> 
						活動地點: <input type="text" name="venue" value="${ eventDto.venue }" required /><p /> 
						活動簡介: <textarea rows="5" cols="22" name="description"></textarea><p />
						<input type="hidden" name="eventId" value="${ eventDto.eventId }">
					</fieldset>
				</div>
				<div>
					<h2>座位設定</h2>
					<button onclick="addSeat()" class="button-secondary pure-button">新增欄位</button><p />
					<fieldset>
						<table id="seat-table">
							<thead>
								<tr>
									<th>座位等級</th><th>座位價額</th><th>座位數量</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="seatCategoriesDto" items="${ seatCategoriesDto }">
								<tr>
									<td><input type="text" name="categoryName" value="${ seatCategoriesDto.categoryName }" required></td>
									<td><input type="number" name="seatPrice" style="width: 75px" min="0" value="${ seatCategoriesDto.seatPrice }" required></td>
									<td><input type="number" name="numSeats" style="width: 75px" min="1" value="${ seatCategoriesDto.numSeats }" required></td>
									<td><a href="/ticket/SeatCategory/delete?eventId=${ seatCategoriesDto.eventId }&seatCategoryId=${ seatCategoriesDto.seatCategoryId }" id="deleteLink" class="button-error pure-button">刪除</a></td>
									<td><input type="hidden" name="seatCategoryIds" value="${ seatCategoriesDto.seatCategoryId }"></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</fieldset>
				</div>
				<button type="submit" class="button-secondary pure-button" >Update</button>
			</form>
		</div>
		
		
		<script>
   			flatpickr("#datetime", {
        		enableTime: true,  // 启用时间选择
        		dateFormat: "Y-m-d H:i",  // 设置日期时间格式
   	 		});
   			
   			document.getElementById('deleteLink').onclick = function(event) {
   			    // 阻止 <a> 預設的跳轉行為
   			    event.preventDefault();

   			    // 彈出確認對話框，詢問是否確定刪除
   			    const confirmed = confirm("確定要刪除此項目嗎？");

   			    // 如果用戶點擊「確定」，則執行刪除操作
   			    if (confirmed) {
   			        // 如果你希望按確定後跳轉到某個頁面，可以使用 window.location
   			        window.location.href = event.target.href;  // 這樣會跳轉到 <a> 的 href 地址
   			    }
   			};
   			
   			function addSeat() {
   			    // 取得表格中的 <tbody> 參照
   			    const tableBody = document.querySelector('#seat-table tbody');
   			    
   			    // 複製表格中的最後一行
   			    const lastRow = tableBody.rows[tableBody.rows.length - 1];
   			    const newRow = lastRow.cloneNode(true);  // 複製最後一行

   			    // 清空新複製行中的輸入框內容（這步可選）
   			    const inputs = newRow.querySelectorAll('input');
   			    inputs.forEach(input => input.value = '');
   			    
   				// 清空新複製行中的第4個 <td> 內容（完全清除所有內部元素）
   			    const fourthTd = newRow.cells[3]; // 取得第4個 <td>（即第3個索引）
   			    fourthTd.innerHTML = '';  // 清空第4個 <td> 內的所有內容（包括 <input> 和 <button>）
				
   				// 更改新複製行中的第5個 <td> 內容（如果它是 input 元素）
   			    const fifthTd = newRow.cells[4]; // 取得第5個 <td>（即第4個索引）
   			    const inputInfifthTd = fifthTd.querySelector('input'); // 取得其中的 <input> 元素
   			    if (inputInfifthTd) {
   			        inputInfifthTd.value = '0';  // 更改第5個 <td> 中的 input 欄位內容
   			    }
   			    
   			    // 創建刪除按鈕並加到新的行
   			    const deleteButton = document.createElement('button');
   			    deleteButton.textContent = '刪除';
   			    deleteButton.classList.add('delete-btn');
   			    deleteButton.classList.add('button-error', 'pure-button');
   			    deleteButton.onclick = function () {
   			        if (confirm('確定刪除此欄位？')) {
   			            tableBody.removeChild(newRow);  // 刪除新增的行
   			        }
   			    };

   			    // 把刪除按鈕加到新行中的最後一個 <td>
   			    newRow.cells[3].appendChild(deleteButton);

   			    // 將新複製的行添加到表格中
   			    tableBody.appendChild(newRow);
   			}
		</script>
	</body>
</html>