package ticket.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
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

@WebServlet(urlPatterns = {"/order/*"})
public class OrderServlet extends HttpServlet{
	private OrderService orderService = new OrderService();
	private EventService eventService = new EventService();
	private SeatCategoriesService seatCategoriesService = new SeatCategoriesService();
	private SeatsService seatsService = new SeatsService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
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
			req.setAttribute("orderDto", orderDto);
			req.getRequestDispatcher("/WEB-INF/view/order_pay.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo();
        
        String eventId = req.getParameter("eventId");
		String eventName = req.getParameter("eventName");
		
		String[] numSeatss = req.getParameterValues("numSeatss");
		String[] seatPrices = req.getParameterValues("seatPrices");
		String[] seatCategoryIds = req.getParameterValues("seatCategoryIds");
		
		if (pathInfo.equals("/buy")) {
			// 獲取伺服器當前時間
	        String orderDate = LocalDateTime.now().toString(); // 例如：2024-12-02T15:30:00
			HttpSession session = req.getSession();
			UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
			Integer userId = userCert.getUserId();
			Integer orderId = orderService.addOrder(userId, eventName, seatPrices, numSeatss, orderDate);
			List<Seats> seats = seatsService.buySeats(eventId, seatCategoryIds, numSeatss);
			orderService.addOrderSeats(orderId, seats);
			resp.sendRedirect("/order/pay?orderId=" + orderId);
			return;
		}
	}
	
}
