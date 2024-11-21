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
				<div id="form-container">
					<fieldset>
						<div class="form-group">
							座位等級: <input type="text" name="categoryName" placeholder="請輸入座位分級/區" required>
							座位價額: <input type="number" name="seatPrice" style="width: 75px" min="0" required>
							座位數量: <input type="number" name="numSeats" style="width: 75px" min="1" required>
						</div>
					</fieldset>
				</div>
				<button onclick="addSeat()" class="button-secondary pure-button">新增欄位</button><p />
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