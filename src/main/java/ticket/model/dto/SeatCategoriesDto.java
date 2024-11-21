package ticket.model.dto;

import lombok.Data;

@Data
public class SeatCategoriesDto {
	private Integer seatCategoryId;
	private Integer eventId;
	private String categoryName;
	private Double price;
	private Integer numSeats;
}
