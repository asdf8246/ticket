package ticket.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
			eventDto.setSellDate(event.getSellDate());
			eventDto.setVenue(event.getVenue());
			eventDto.setAddress(event.getAddress());
			eventDto.setDescription(event.getDescription());
			eventDto.setEventStatus(event.getEventStatus());
			eventDto.setEventImage(event.getEventImage());
			
			eventDtos.add(eventDto);
		}
		return eventDtos;
	}
	
	//新增
	public Integer appendEvent(String eventName, String eventDate, String sellDate, String venue, String address, String description, InputStream eventImage) {
		Events event = new Events();
		event.setEventName(eventName);
		event.setEventDate(eventDate);
		event.setSellDate(sellDate);
		event.setVenue(venue);
		event.setAddress(address);
		event.setDescription(description);
		event.setEventImage(eventImage);
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
		eventDto.setSellDate(event.getSellDate());
		eventDto.setVenue(event.getVenue());
		eventDto.setAddress(event.getAddress());
		eventDto.setDescription(event.getDescription());
		eventDto.setEventStatus(event.getEventStatus());
		eventDto.setEventImage(event.getEventImage());
		
		return eventDto;
	}
	
	//修改
	public void updateEvent(String eventId, String eventName, String eventDate, String sellDate, String venue, String address, String description, InputStream eventImage) {
		if (!eventName.isEmpty()) {
			eventDao.updateEventName(Integer.parseInt(eventId), eventName);
		}
		if (!eventDate.isEmpty()) {
			eventDao.updateEventDate(Integer.parseInt(eventId), eventDate);
		}
		if (!sellDate.isEmpty()) {
			eventDao.updateSellDate(Integer.parseInt(eventId), sellDate);
		}
		if (!venue.isEmpty()) {
			eventDao.updateVenue(Integer.parseInt(eventId), venue);
		}
		if (!address.isEmpty()) {
			eventDao.updateAddress(Integer.parseInt(eventId), address);
		}
		if (!description.isEmpty()) {
			eventDao.updateDescription(Integer.parseInt(eventId), description);
		}
		if (eventImage != null) {
			eventDao.updateEventImage(Integer.parseInt(eventId), eventImage);
		}
	}
	
	// 搜尋
	public List<EventDto> getSearchEvents(String search){
		List<Events> events = eventDao.getSearchEvents(search);
		List<EventDto> searchEventDtos = new ArrayList<>();
		for (Events event : events) {
			EventDto eventDto = new EventDto();
			eventDto.setEventId(event.getEventId());
			eventDto.setEventName(event.getEventName());
			eventDto.setEventDate(event.getEventDate());
			eventDto.setSellDate(event.getSellDate());
			eventDto.setVenue(event.getVenue());
			eventDto.setAddress(event.getAddress());
			eventDto.setDescription(event.getDescription());
			eventDto.setEventStatus(event.getEventStatus());
			
			searchEventDtos.add(eventDto);
		}
		return searchEventDtos;
	}
}
