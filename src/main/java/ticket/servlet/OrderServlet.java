package ticket.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ticket.model.dto.EventDto;
import ticket.model.dto.OrderDto;
import ticket.model.dto.SeatCategoriesDto;
import ticket.model.dto.UserCert;
import ticket.model.entity.Seats;
import ticket.service.EventService;
import ticket.service.OrderService;
import ticket.service.SeatCategoriesService;
import ticket.service.SeatsService;
import ticket.utils.CheckUser;

@WebServlet(urlPatterns = {"/order/*"})
public class OrderServlet extends HttpServlet{
	private OrderService orderService = new OrderService();
	private EventService eventService = new EventService();
	private SeatCategoriesService seatCategoriesService = new SeatCategoriesService();
	private SeatsService seatsService = new SeatsService();
	private CheckUser checkUser = new CheckUser();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		HttpSession session = req.getSession();
		UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
		Integer userId = userCert.getUserId();
		String userRole = userCert.getRole();
		if (pathInfo.equals("/buy")) {
			String eventId = req.getParameter("eventId");
			List<SeatCategoriesDto> seatCategoriesDto = seatCategoriesService.getSeatCategories(eventId);
			EventDto eventDto = eventService.getEvent(eventId);
			req.setAttribute("eventDto", eventDto);
			req.setAttribute("seatCategoriesDto", seatCategoriesDto);
			req.getRequestDispatcher("/WEB-INF/view/order_buy.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/pay")) {
			String orderId = req.getParameter("orderId");
			OrderDto orderDto = orderService.getOrder(orderId);
			List<OrderDto> orderSeatsDto = orderService.getOrderSeats(orderId);
			
			if (orderDto == null) {
				resp.setContentType("text/html;charset=UTF-8");
	            resp.getWriter().write("<script type='text/javascript'>");
	            resp.getWriter().write("alert('訂單已取消!');");
	            resp.getWriter().write("window.location.href = '/ticket/user/order';"); // 重新導向回表單頁面
	            resp.getWriter().write("</script>");
		        return;
			}
			
			if (!checkUser.checkOrderUser(orderId, userId)) {
				req.setAttribute("message", "執行錯誤操作!!!");
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				return;
			}
			String eventId = orderDto.getEventId().toString();
			EventDto eventDto = eventService.getEvent(eventId);
			req.setAttribute("eventDto", eventDto);
			req.setAttribute("orderDto", orderDto);
			req.setAttribute("orderSeatsDto", orderSeatsDto);
			req.getRequestDispatcher("/WEB-INF/view/order_pay.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/delete")) {
			String orderId = req.getParameter("orderId");
			
			if (!checkUser.checkOrderUser(orderId, userId) && !checkUser.checkUserRole(userId, userRole)) {
				req.setAttribute("message", "執行錯誤操作!!!");
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				return;
			}
			String eventId = req.getParameter("eventId");
			List<OrderDto> orderSeatsDto = orderService.getOrderSeats(orderId);
			String seatStatus = "available";
			seatsService.updateSeatsStatus(orderSeatsDto, seatStatus);
			orderService.deleteOrder(orderId);
			
			if (eventId == null) {
				resp.sendRedirect("/ticket/user/order");
				return;
			}
			
			resp.sendRedirect("/ticket/event/view?eventId=" + eventId);
			return;
		}
		if (pathInfo.equals("/finish")) {
			String orderId = req.getParameter("orderId");
			
			if (!checkUser.checkOrderUser(orderId, userId)) {
				req.setAttribute("message", "執行錯誤操作!!!");
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				return;
			}
			
			List<OrderDto> orderSeatsDto = orderService.getOrderSeats(orderId);
			String seatStatus = "sold";
			seatsService.updateSeatsStatus(orderSeatsDto, seatStatus);
			String orderStatus = "paid";
			orderService.updateOrderStatus(orderId, orderStatus);
			resp.sendRedirect("/ticket/user/order");
			return;
		}
		if (pathInfo.equals("/cancel")) {
			String orderId = req.getParameter("orderId");
			
			if (!checkUser.checkOrderUser(orderId, userId) && !checkUser.checkUserRole(userId, userRole)) {
				req.setAttribute("message", "執行錯誤操作!!!");
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				return;
			}
			
			List<OrderDto> orderSeatsDto = orderService.getOrderSeats(orderId);
			String seatStatus = "available";
			seatsService.updateSeatsStatus(orderSeatsDto, seatStatus);
			String orderStatus = "canceled";
			orderService.updateOrderStatus(orderId, orderStatus);
			resp.sendRedirect("/ticket/user/order");
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String pathInfo = req.getPathInfo();
        
		HttpSession session = req.getSession();
		UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
		Integer userId = userCert.getUserId();
		
        String eventId = req.getParameter("eventId");
		String eventName = req.getParameter("eventName");
		
		String[] numSeatss = req.getParameterValues("numSeatss");
		String[] seatPrices = req.getParameterValues("seatPrices");
		String[] seatCategoryIds = req.getParameterValues("seatCategoryIds");
		
		if (pathInfo.equals("/buy")) {
			// 獲取伺服器當前時間
	        String orderDate = LocalDateTime.now().format(dtf); // 例如：2024-12-02T15:30:00
			Integer orderId = orderService.addOrder(userId, eventId, eventName, seatPrices, numSeatss, orderDate);
			List<Seats> seats = seatsService.buySeats(eventId, seatCategoryIds, numSeatss);
			
			if (seats.isEmpty()) {
				orderService.deleteOrder(orderId.toString());
				
				// 如果處理失敗，顯示錯誤訊息
	            resp.setContentType("text/html;charset=UTF-8");
	            resp.getWriter().write("<script type='text/javascript'>");
	            resp.getWriter().write("alert('票券已完售!');");
	            resp.getWriter().write("window.location.href = '/ticket/event/view?eventId=" + eventId + "';"); // 重新導向回表單頁面
	            resp.getWriter().write("</script>");
		        return;
			}
			
			orderService.addOrderSeats(orderId, seats);
			resp.sendRedirect("/ticket/order/pay?orderId=" + orderId);
			return;
		}
	}
	
}
