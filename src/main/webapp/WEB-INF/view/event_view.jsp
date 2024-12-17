<%@ page import="ticket.model.dto.EventDto"%>
<%@ page import="ticket.model.dto.SeatCategoriesDto"%>
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
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/ticket/css/buttons.css">
		<link rel="stylesheet" href="/ticket/css/layout.css">
		
		<script src="https://cdn.jsdelivr.net/npm/date-fns@4.1.0/cdn.min.js"></script>
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/event_menu.jspf" %>
		
		<div>
			<img src="<c:url value='/image?id=${eventDto.eventId}&timestamp=${System.currentTimeMillis()}' />" alt="Event Image" class="large-image" />
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h2>${ eventDto.eventName }</h2>
			開賣時間: ${ eventDto.sellDate } / 剩餘: <span id="countdown"></span><p />
			活動日期: ${ eventDto.eventDate }<p />
			活動地點: ${ eventDto.venue } / ${ eventDto.address }<p />
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h3>活動介紹</h3>
			${ eventDto.description }
		</div>
		<div class="pure-form" style="padding: 15px;">
			<h3>活動票券</h3>
			<fieldset>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>票種</th><th>票價</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="seatCategoriesDto" items="${ seatCategoriesDto }">
						<tr>
							<td>${ seatCategoriesDto.categoryName }</td>
							<td><f:formatNumber value="${ seatCategoriesDto.seatPrice }" type="currency" maxFractionDigits="0" /></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</fieldset>
			<c:if test="${ eventDto.eventStatus == '準備中' }">
				<button class="pure-button">尚未開賣</button>
			</c:if>
			<c:if test="${ eventDto.eventStatus == '開賣中' }">
				<a href="/ticket/order/buy?eventId=${ eventDto.eventId }" class="button-secondary pure-button">前往購買</a>
			</c:if>
			<c:if test="${ eventDto.eventStatus == '已結束' }">
				<button class="pure-button">已結束</button>
			</c:if>
		</div>
		
		<script>
			// 从后端获取的时间字符串
			const sellDate = "${ eventDto.sellDate }"  // 示例数据（可以替换成从数据库获得的时间）
			// 使用 date-fns 解析日期字符串，并转换为 JavaScript Date 对象
			const endTime = dateFns.parse(sellDate, 'yyyy-MM-dd HH:mm:ss', new Date());
			//console.log(endTime);  // 打印解析后的日期
			let endtimems = endTime.getTime();  // 获取毫秒数
		    //console.log(endtimems);
			
		    //倒數計時
		    function calcTime(){
		      let now = new Date();
		      //console.log(now);
		      let nowms = now.getTime();
		      //console.log(nowms);
		      let offsetTime = (endtimems - nowms) /1000;
		      //console.log(offsetTime);
			  
		      if (offsetTime <= 0) {
		    	  let timeup = '票券已開賣!';
		          document.querySelector('#countdown').innerHTML = timeup;
		          clearInterval(timerId);  // 使用定时器 ID 清除定时器
		          return;
		      }
		      
		      let sec = Math.floor(offsetTime % 60);         //秒
		      let min = Math.floor(offsetTime /60 % 60);     //分
		      let hr  = Math.floor(offsetTime /60 /60 % 24); //時
		      let day = Math.floor(offsetTime /60 /60 / 24); //天

		      if (sec < 10) { sec = '0' + sec;}
		      if (min < 10) { min = '0' + min;}
		      if (hr  < 10) { hr  = '0' + hr ;}
		      if (day < 10) { day = '0' + day;}

		      let result = day + '天' + hr + '時' + min + '分' + sec + '秒';

		      document.querySelector('#countdown').innerHTML = result;
		    }
			
		 	// 使用 setInterval，每秒钟调用 calcTime
		    let timerId = setInterval(calcTime, 1000);
		</script>
	</body>
</html>