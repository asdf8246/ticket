package ticket.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ticket.model.dto.EventDto;
import ticket.model.dto.UserCert;
import ticket.service.EventService;
import ticket.service.UserService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private EventService eventService = new EventService();
	private UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 取得 session
		HttpSession session = req.getSession();
		Integer login = 0;
		String userName = null;
		// 判斷是否登入
		if (session.getAttribute("userCert")!=null) {
			login = 1;
			UserCert userCert = (UserCert)session.getAttribute("userCert");
			userName = userService.getUser(userCert.getUserId()).getUsername();
		}
		
		
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
		List<Integer> randomEventIds = new ArrayList<>(eventIds);// 複製一份原始列表
		Collections.shuffle(randomEventIds, random);  // 隨機打亂列表
		// 選擇不重複的 3 個隨機數字
		Integer mainEventId = randomEventIds.get(0);  // 第一個選中的數字
		randomEventIds = randomEventIds.subList(1, 3);  // 取後兩個隨機數字

		req.setAttribute("login", login);
		req.setAttribute("userName", userName);
	    req.setAttribute("mainEventId", mainEventId);
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
