package ticket.repository;

import ticket.model.entity.SeatCategories;

public interface SeatCategoriesDao {
	
	// 查詢該活動設定之座位等級
	SeatCategories getSeatCategories(Integer eventId);
	
	// 新增
	void addSeatCategories(SeatCategories seatCategories);
	
	// 修改
	void updateCategoryName(Integer seatCategoryId, String categoryName);
	
	void updateSeatPrice(Integer seatCategoryId, Integer seatPrice);
	
	void updateNumSeats(Integer seatCategoryId, Integer numSeats);
	
	// 刪除
	void deleteSeatCategories(Integer eventId);
}
