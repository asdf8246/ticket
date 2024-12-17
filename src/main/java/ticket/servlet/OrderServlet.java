package ticket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.gson.JsonObject;

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
			req.setAttribute("orderDto", orderDto);
			req.setAttribute("orderSeatsDto", orderSeatsDto);
			req.getRequestDispatcher("/WEB-INF/view/order_pay.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/delete")) {
			String orderId = req.getParameter("orderId");
			
			if (!checkUser.checkOrderUser(orderId, userId) || !checkUser.checkUserRole(userId)) {
				req.setAttribute("message", "執行錯誤操作!!!");
				req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				return;
			}
			String eventId = req.getParameter("eventId");
			List<OrderDto> orderSeatsDto = orderService.getOrderSeats(orderId);
			String seatStatus = "available";
			seatsService.updateSeatsStatus(orderSeatsDto, seatStatus);
			orderService.deleteOrder(orderId);
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
			
			if (!checkUser.checkOrderUser(orderId, userId) || !checkUser.checkUserRole(userId)) {
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
			
			if (seats == null) {
				// 构建消息和跳转 URL
		        String message = "票券已完售!";
		        String redirectUrl = "/ticket/event/view?eventId=" + eventId;  // 替换为目标页面的 URL

		        // 创建响应对象（将消息和跳转 URL 传递到前端）
		        JsonObject jsonResponse = new JsonObject();
		        jsonResponse.addProperty("message", message);
		        jsonResponse.addProperty("redirectUrl", redirectUrl);

		        // 输出 JSON 响应
		        PrintWriter out = resp.getWriter();
		        out.print(jsonResponse.toString());
		        out.flush();
		        return;
			}
			
			orderService.addOrderSeats(orderId, seats);
			resp.sendRedirect("/ticket/order/pay?orderId=" + orderId);
			return;
		}
	}
	
}
