package ticket.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ticket.model.dto.EventDto;
import ticket.service.EventService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private EventService eventService = new EventService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<EventDto> eventDtos = eventService.findAllEvents();
		List<EventDto> homeEventDtos = new ArrayList<EventDto>();
		for (EventDto eventDto : eventDtos) {
			EventDto homeEventDto = new EventDto();
			String eventStatus = eventDto.getEventStatus();
			if (eventStatus.equals("已結束")) {
				continue;
			}
			homeEventDto.setEventId(eventDto.getEventId());
			homeEventDto.setEventName(eventDto.getEventName());
			homeEventDto.setEventDate(eventDto.getEventDate());
			homeEventDto.setSellDate(eventDto.getSellDate());
			homeEventDto.setVenue(eventDto.getVenue());
			homeEventDto.setAddress(eventDto.getAddress());
			homeEventDto.setDescription(eventDto.getDescription());
			homeEventDto.setEventStatus(eventStatus);
			
			homeEventDtos.add(homeEventDto);
		}
		
		List<Integer> eventIds = new ArrayList<>();
		for (EventDto eventIdDto : homeEventDtos) {
			eventIds.add(eventIdDto.getEventId());
		}
		
		Random random = new Random();
	    List<Integer> randomEventIds = new ArrayList<>();
	    for (int i = 0; i < 3; i++) {
	        Integer randomEventId = eventIds.get(random.nextInt(eventIds.size()));
	        randomEventIds.add(randomEventId);
	    }
		
	    req.setAttribute("randomEventIds", randomEventIds);
		req.setAttribute("eventDtos", eventDtos);
		req.setAttribute("homeEventDtos", homeEventDtos);
		req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	
}
