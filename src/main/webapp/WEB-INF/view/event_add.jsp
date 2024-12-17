<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增活動</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		
		<!-- 引入 flatpickr 样式 -->
   	 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    	<!-- 引入 flatpickr 脚本 -->
    	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    	
    	<!-- 台灣縣市二聯式選單 -->
    	<script src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.1/dist/tw-city-selector.min.js"></script>
    	
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div style="padding: 15px">
			<form id="eventForm" class="pure-form" method="post" enctype="multipart/form-data" action="/ticket/event/add">
				<h2>新增活動</h2>
				<div>
					<label for="file">
						活動圖片: <input type="file" name="file" id="file" multiple class="file d-none"
               			accept=".jpg, .jpeg, .png, .gif" onchange="chkfile(this)" required>
					</label><p />
					
					<div id="img_errmsg" class="text-danger text-center tw-bold"></div>
            		<div id="img_area" class="text-center" style="margin-left: 73px;"></div>
				</div>
				<div>
					<fieldset>
						活動名稱: <input type="text" name="eventName" placeholder="請輸入活動名稱" required /><p />
						<label for="datetime">售票日期: </label><input type="text" class="datetime" id="sellDate" name="sellDate" required /><p />
						<label for="datetime">活動日期: </label><input type="text" class="datetime" id="eventDate" name="eventDate" required /><p /> 
						活動地點: <input type="text" name="venue" placeholder="請輸入建物名稱" required /><p /> 
						<div role="tw-city-selector">活動地址: </div>
						<input type="text" name="address" style="margin-left: 73px;" placeholder="請輸入詳細地址" required><p />
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
						            <td><input type="text" name="categoryName" style="width: 150px" placeholder="請輸入座位分級/區" required></td>
						            <td><input type="number" name="seatPrice" style="width: 100px" min="0" required></td>
						            <td><input type="number" name="numSeats" style="width: 100px" min="1" required></td>
						            <td></td>
        						</tr>
    						</tbody>
						</table>
					</fieldset>
				</div>
				<button type="reset" class="button-warning pure-button">重置</button>
				<button type="submit" class="button-success pure-button">新增活動</button>
			</form>
		</div>
		
		
		<script>
			// 生成台灣縣市二聯式選單
			new TwCitySelector() + '<p />';
		
			//圖片物件及條件
		    let file =document.querySelector('#file');
		    let imgErrMsgArea = document.querySelector('#img_errmsg');
		    let imgPreArea = document.querySelector('#img_area');
		    let maxSize = 1024 * 1024;
		    let minWidth = 1000;
		    let imgstr = '';
		    let imgErrMsg = '';
		    
		  	//負責檢查上傳檔案
		    function chkfile(obj){
		    issubmit = true;
		    imgErrMsgArea.innerHTML = '';
		    imgPreArea.innerHTML = '';
		    imgstr = '';
		    imgErrMsg = '';

		    let filesLength = obj.files.length;
		    console.log(filesLength);

		    if (filesLength > 1){
		     	alert('只能選一張圖片!');
		     	issubmit = false;
		      } else {
		       Object.values(obj.files).forEach(chkfile2);
		      }
		    }
		  	
		    function chkfile2(item, index){
		        let reader = new FileReader();

		        reader.onload = function (e) {
		          //(1)預覽影像
		          let data = e.target.result;
		          imgstr += '<img src="' + data + '"height="100" class="p-1">';
		          imgPreArea.innerHTML = imgstr;

		          //(2)檢查檔案容量
		          let fileSize = item['size'];
		          if (fileSize > maxSize){
		            issubmit = false;
		            imgErrMsg += '<br>' + item['name'] + '檔案容量太大';
		            imgErrMsgArea.innerHTML = imgErrMsg;
		          }

		          //(3)檢查圖片寬度
		          let image = new Image();

		          image.onload = function (){
		            let fileWidth = image.width;
		            if(fileWidth<minWidth){
		              issubmit = false;
		              imgErrMsg =+ '<br>' + item['name'] + '檔案寬度太小';
		              imgErrMsgArea.innerHTML = imgErrMsg;
		            }
		          }
		          image.src = data;
		        }
		        reader.readAsDataURL(item);
		      }
		  	
				
   			flatpickr(".datetime", {
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
   			
   			 // 當表單提交時進行檢查
   		    document.getElementById("eventForm").addEventListener("submit", function(event) {
   		        // 取得活動日期和售票日期的值
   		        const eventDate = new Date(document.getElementById("eventDate").value);
   		        const sellDate = new Date(document.getElementById("sellDate").value);

   		        // 檢查活動日期是否大於售票日期
   		        if (eventDate <= sellDate) {
   		            alert("活動日期必須大於售票日期！");
   		            event.preventDefault(); // 阻止表單提交
   		        }
   		    });
   			 
	   		document.getElementById("eventForm").onsubmit = function() {
	   		    var fileInput = document.getElementById("file");
	   		    if (fileInput.files.length === 0) {
	   		        alert("請選擇一個檔案！");
	   		        return false;  // 阻止表單提交
	   		    }
	   		};
		</script>
	</body>
</html>