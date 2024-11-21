package ticket.model.dto;

import lombok.Data;

@Data
public class SeatCategoriesDto {
	private Integer seatCategoryId;
	private Integer eventId;
	private String categoryName;
	private Integer seatPrice;
	private Integer numSeats;
}
