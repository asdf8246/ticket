package ticket.model.dto;

import lombok.Data;

@Data
public class SeatsDto {
	private Integer seatId;
	private Integer eventId;
	private Integer seatCategoryId;
	private Integer seatNumber;
	private String seatStatus;
	
	
	//外加欄位
	private Integer numSeats; //所需座位數量
}
