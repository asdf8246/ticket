package ticket.model.dto;

import lombok.Data;

@Data
public class EventDto {
	private Integer eventId;
	private String eventName;
	private String eventDate;
	private String venue;
	private String description;
}
