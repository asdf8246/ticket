package ticket.repository;

import java.util.List;

import ticket.model.entity.Events;

public interface EventDao {
	
	// 多筆:查詢所有
	List<Events> findAllEvents();
	
	// 查詢該筆
	Events getEvent(Integer eventId);
	
	// 新增
	void addEvent(Events events);
	
	// 修改
	void updateEventName(Integer eventId, String eventName);
	
	void updateEventDate(Integer eventId, String eventDate);
	
	void updateVenue(Integer eventId, String venue);
	
	void updateDescription(Integer eventId, String description);
	
	// 刪除
	void deleteEvent(Integer eventId);
	
	
	
}
