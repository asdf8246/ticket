<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
				<h2>新增活動</h2>
				<div>
					<fieldset>
						活動名稱: <input type="text" name="eventName" placeholder="請輸入活動名稱" required /><p />
						<label for="datetime">活動日期: </label><input type="text" id="datetime" name="eventDate" required /><p /> 
						活動地點: <input type="text" name="venue" placeholder="請輸入活動地點" required /><p /> 
						活動簡介: <textarea rows="5" cols="22" name="description"></textarea><p /> 	  
					</fieldset>
				</div>
				<p />
				<h2>座位設定</h2>
				<button onclick="addSeat()" class="button-secondary pure-button">新增欄位</button><p />
				<div>
					<fieldset>
						<table id="seat-table">
    						<thead>
        						<tr>
						           <th>座位等級</th>
						           <th>座位價額</th>
						           <th>座位數量</th>
						           <th></th>
        						</tr>
   							 </thead>
    						 <tbody>
       							 <tr>
						            <td><input type="text" name="categoryName" style="width: 175px" placeholder="請輸入座位分級/區" required></td>
						            <td><input type="number" name="seatPrice" style="width: 75px" min="0" required></td>
						            <td><input type="number" name="numSeats" style="width: 75px" min="1" required></td>
						            <td></td>
        						</tr>
    						</tbody>
						</table>
					</fieldset>
				</div>
				<button type="reset" class="button-warning pure-button">Reset</button>
				<button type="submit" class="button-success pure-button">Submit</button>
			</form>
		</div>
		
		
		<script>
   			flatpickr("#datetime", {
        		enableTime: true,  // 启用时间选择
        		dateFormat: "Y-m-d H:i",  // 设置日期时间格式
   	 		});
   			
   			
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
   			    const thirdTd = newRow.cells[3]; // 取得第4個 <td>（即第3個索引）
   			    thirdTd.innerHTML = '';  // 清空第4個 <td> 內的所有內容（包括 <input> 和 <button>）

   			    // 創建刪除按鈕並加到新的行
   			    const deleteButton = document.createElement('button');
   			    deleteButton.textContent = '刪除';
   			    deleteButton.classList.add('delete-btn');
   			    deleteButton.classList.add('button-error', 'pure-button');
   			    deleteButton.onclick = function () {
   			        if (confirm('確定刪除此組座位設定？')) {
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