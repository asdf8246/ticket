package ticket.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;

import ticket.model.dto.EventDto;
import ticket.model.entity.Events;
import ticket.repository.EventDao;
import ticket.repository.EventDaoImpl;

public class EventService {
	private EventDao eventDao = new EventDaoImpl();
	
	// 所有
	public List<EventDto> findAllEvents() {
		
		List<EventDto> eventDtos = new ArrayList<>();
		List<Events> events = eventDao.findAllEvents();
		for (Events event : events) {
			EventDto eventDto = new EventDto();
			eventDto.setEventId(event.getEventId());
			eventDto.setEventName(event.getEventName());
			eventDto.setEventDate(event.getEventDate());
			eventDto.setVenue(event.getVenue());
			eventDto.setDescription(event.getDescription());
			
			eventDtos.add(eventDto);
		}
		return eventDtos;
	}
	
	//新增
	public Integer appendEvent(String eventName, String eventDate, String venue, String description) {
		Events event = new Events();
		event.setEventName(eventName);
		event.setEventDate(eventDate);
		event.setVenue(venue);
		event.setDescription(description);
		Integer eventId  = eventDao.addEvent(event);
		return eventId;
	}
	
	// 刪除
	public void deleteEvent(String eventId) {
		eventDao.deleteEvent(Integer.parseInt(eventId));
	}
	
	// 取得指定
	public EventDto getEvent(String eventId) {
		Events event = eventDao.getEvent(Integer.parseInt(eventId));
		if (event==null) {
			return null;
		}
		
		EventDto eventDto = new EventDto();
		eventDto.setEventId(event.getEventId());
		eventDto.setEventName(event.getEventName());
		eventDto.setEventDate(event.getEventDate());
		eventDto.setVenue(event.getVenue());
		eventDto.setDescription(event.getDescription());
		
		return eventDto;
	}
	
	//修改
	public void updateEvent(String eventId, String eventName, String eventDate, String venue, String description) {
		if (!eventName.isEmpty()) {
			eventDao.updateEventName(Integer.parseInt(eventId), eventName);
		}
		if (!eventDate.isEmpty()) {
			eventDao.updateEventDate(Integer.parseInt(eventId), eventDate);
		}
		if (!venue.isEmpty()) {
			eventDao.updateVenue(Integer.parseInt(eventId), venue);
		}
		if (!description.isEmpty()) {
			eventDao.updateDescription(Integer.parseInt(eventId), description);
		}
	}
}
