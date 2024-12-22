<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	</head>
	<body>
	  <h2>座位銷售狀況</h2>
    <canvas id="seatChart" width="400" height="200"></canvas>

    <script>
        // 從 JSP 中取得資料並轉換成 JavaScript 可以使用的格式
        var categories = [];
        var numSeatsData = [];
        var soldSeatsData = [];

        // 使用 JSTL 來遍歷 List<SeatCategoriesDto> 並提取資料
        <c:forEach var="seatCategory" items="${seatCategoriesDto}">
            categories.push("${seatCategory.categoryName}");
            numSeatsData.push(${seatCategory.numSeats});
            soldSeatsData.push(${seatCategory.soldSeats});
        </c:forEach>

        // 使用 Chart.js 繪製柱狀圖
        var ctx = document.getElementById('seatChart').getContext('2d');
        var seatChart = new Chart(ctx, {
            type: 'bar',  // 設定圖表為柱狀圖
            data: {
                labels: categories,  // x 軸的標籤
                datasets: [{
                    label: '總座位數',
                    data: numSeatsData,  // 總座位數資料
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',  // 填充顏色
                    borderColor: 'rgba(54, 162, 235, 1)',  // 邊框顏色
                    borderWidth: 1
                }, {
                    label: '已銷售座位數',
                    data: soldSeatsData,  // 已銷售座位數資料
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',  // 填充顏色
                    borderColor: 'rgba(255, 99, 132, 1)',  // 邊框顏色
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true  // y 軸從 0 開始
                    }
                }
            }
        });
    </script>
	</body>
</html>