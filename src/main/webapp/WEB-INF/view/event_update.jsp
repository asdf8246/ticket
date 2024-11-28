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
    	<style>
	         fieldset {
            margin-bottom: 10px;
            padding: 10px;
	        }
	        .form-group {
	            position: relative; /* 使刪除按鈕能相對定位 */
	            margin-bottom: 10px;
	        }
	        .form-group input {
	            box-sizing: border-box; /* 讓寬度包含內邊距和邊框 */
	            margin: 10px;
	        }
	        .delete-btn {
	            cursor: pointer;
	            position: absolute; /* 絕對定位 */
	            top: 50%;
	            transform: translateY(-50%); /* 垂直居中 */
	        }
	        #form-container {
	            margin-top: 20px;
	        }
    	</style>
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
					<fieldset>
						<table>
							<thead>
								<tr>
									<th>座位等級</th><th>座位價額</th><th>座位數量</th>
								</tr>
							</thead>
							<c:forEach var="seatCategoriesDto" items="${ seatCategoriesDto }">
								<tr>
									<td><input type="text" name="categoryName" value="${ seatCategoriesDto.categoryName }" required></td>
									<td><input type="number" name="seatPrice" style="width: 75px" min="0" value="${ seatCategoriesDto.seatPrice }" required></td>
									<td><input type="number" name="numSeats" style="width: 75px" min="1" value="${ seatCategoriesDto.numSeats }" required></td>
									<td><a href="/ticket/SeatCategory/delete?eventId=${ seatCategoriesDto.eventId }&seatCategoryId=${ seatCategoriesDto.seatCategoryId }" class="button-error pure-button">刪除</a></td>
								</tr>
							</c:forEach>
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
   			
   			function addSeat() {
   	            // 取得原始表單
   	            const formContainer = document.getElementById('form-container');
   	            
   	            // 複製一份現有的表單
   	            const newForm = formContainer.querySelector('fieldset').cloneNode(true);

   	            // 為新表單添加刪除按鈕
   	            const formGroups = newForm.querySelector('.form-group');
   	            
   	            const deleteButton = document.createElement('button');
   	            deleteButton.textContent = '刪除';
   	            deleteButton.classList.add('delete-btn');
   	        	deleteButton.classList.add('button-error', 'pure-button');
   	            deleteButton.onclick = function () {
   	                if (confirm('確定刪除此組座位設定？')) {
   	                	formContainer.removeChild(newForm);
   	                }
   	            };

   	            // 把刪除按鈕加到每個 form-group 的右側
   	            formGroups.appendChild(deleteButton);

   	            // 清除複製表單中的輸入內容（這步可選）
   	            const inputs = newForm.querySelectorAll('input');
   	            inputs.forEach(input => input.value = '');

   	            // 將複製的表單添加到容器中
   	            formContainer.appendChild(newForm);
   	        }
		</script>
	</body>
</html>