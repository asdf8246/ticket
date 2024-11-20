package ticket.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ticket.model.dto.EventDto;
import ticket.service.EventService;

/**
 查詢所有: GET /events
 查詢單筆: GET /event/get?eventId=1
 新增單筆: POST /event/add
 修改單筆: POST /event/update?eventId=1
 刪除單筆: GET /event/delete?eventId=1 
 */

@WebServlet(urlPatterns = {"/event/*"})
public class EventServlet extends HttpServlet {
	private EventService eventService = new EventService();
	
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
			req.setAttribute("eventDto", eventDto);
			req.getRequestDispatcher("/WEB-INF/view/event_update.jsp").forward(req, resp);
			return;
		}
		if (pathInfo.equals("/delete")) {
			String eventId = req.getParameter("eventId");
			eventService.deleteEvent(eventId);
			resp.sendRedirect("/ticket/event");
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
		
		switch (pathInfo) {
		case "/add" :
			eventService.appendEvent(eventName, eventDate, venue, description);
			resp.sendRedirect("/ticket/index.html");
			break;
		case "/update":
			eventService.updateEvent(eventId, eventName, eventDate, venue, description);
			resp.sendRedirect("/ticket/event");
			break;
		}
	}
	
	
	
}
