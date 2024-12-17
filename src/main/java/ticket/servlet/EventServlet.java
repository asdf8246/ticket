package ticket.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ticket.model.dto.EventDto;
import ticket.model.dto.SeatCategoriesDto;
import ticket.service.EventService;
import ticket.service.SeatCategoriesService;

/**
 查詢所有: GET /events
 查詢單筆: GET /event/get?eventId=1
 新增單筆: POST /event/add
 修改單筆: POST /event/update?eventId=1
 刪除單筆: GET /event/delete?eventId=1 
 */

@WebServlet(urlPatterns = {"/event/*"})
@MultipartConfig(
	    maxFileSize = 10485760,      // 10MB
	    maxRequestSize = 20971520,   // 20MB
	    fileSizeThreshold = 0        // 不將文件寫入硬碟，全部在內存中處理
)
public class EventServlet extends HttpServlet {
	private EventService eventService = new EventService();
	private SeatCategoriesService seatCategoriesService = new SeatCategoriesService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/*")) {
			List<EventDto> eventDtos = eventService.findAllEvents();
			req.setAttribute("eventDtos", eventDtos);
			req.getRequestDispatcher("/WEB-INF/view/events.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/add")) {
			req.getRequestDispatcher("/WEB-INF/view/event_add.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/get")) {
			String eventId = req.getParameter("eventId");
			EventDto eventDto = eventService.getEvent(eventId);
			List<SeatCategoriesDto> seatCategoriesDto = seatCategoriesService.getSeatCategories(eventId);
			req.setAttribute("eventDto", eventDto);
			req.setAttribute("seatCategoriesDto", seatCategoriesDto);
			req.getRequestDispatcher("/WEB-INF/view/event_update.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/delete")) {
			String eventId = req.getParameter("eventId");
			eventService.deleteEvent(eventId);
			resp.sendRedirect("/ticket/event");
			return;
		}
		if (pathInfo.equals("/view")) {
			String eventId = req.getParameter("eventId");
			EventDto eventDto = eventService.getEvent(eventId);
			List<SeatCategoriesDto> seatCategoriesDto = seatCategoriesService.getSeatCategories(eventId);
			req.setAttribute("eventDto", eventDto);
			req.setAttribute("seatCategoriesDto", seatCategoriesDto);
			req.getRequestDispatcher("/WEB-INF/view/event_view.jsp").forward(req, resp);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String eventId = req.getParameter("eventId");
		String eventName = req.getParameter("eventName");
		String eventDate = req.getParameter("eventDate");
		String venue = req.getParameter("venue");
		String description = req.getParameter("description");
		
		String sellDate = req.getParameter("sellDate");
		String county = req.getParameter("county");
		String district = req.getParameter("district");
		String address = county + district + req.getParameter("address");
		
		String[] seatCategoryIds = req.getParameterValues("seatCategoryIds");
		String[] categoryNames = req.getParameterValues("categoryName");
		String[] seatPrices = req.getParameterValues("seatPrice"); 
		String[] numSeatss = req.getParameterValues("numSeats"); 
		
		if (pathInfo.equals("/add")) {
			// 獲取檔案
	        Part filePart = req.getPart("file");
	        if (filePart == null) {
	        	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "檔案上傳失敗，未選擇檔案！");
	        	return;
	        }
	        InputStream eventImage = filePart.getInputStream();
			
			Integer seatEventId = eventService.appendEvent(eventName, eventDate, sellDate, venue, address, description, eventImage);			
			seatCategoriesService.appendSeatCategory(seatEventId, categoryNames, seatPrices, numSeatss);
			resp.sendRedirect("/ticket/event");
			return;
		}
		if (pathInfo.equals("/update")) {
			
			Part filePart = req.getPart("file");
			InputStream eventImage = null;
	        if (filePart != null) {
	        	eventImage = filePart.getInputStream();
	        }
			
			eventService.updateEvent(eventId, eventName, eventDate, sellDate, venue, address, description, eventImage);
			seatCategoriesService.updateSeatCategory(eventId, seatCategoryIds, categoryNames, seatPrices, numSeatss);
			resp.sendRedirect("/ticket/event");
			return;
		}
	}
	
	
	
}
