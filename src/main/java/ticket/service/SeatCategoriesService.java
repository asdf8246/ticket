package ticket.service;

import ticket.model.dto.SeatCategoriesDto;
import ticket.model.entity.SeatCategories;
import ticket.repository.SeatCategoriesDao;
import ticket.repository.SeatCategoriesDaoImpl;

public class SeatCategoriesService {
	private SeatCategoriesDao seatCategoriesDao = new SeatCategoriesDaoImpl();
	
	//新增
	public void appendSeatCategory(String eventId, String categoryName, String seatPrice, String numSeats) {
		SeatCategories seatCategory = new SeatCategories();
		seatCategory.setEventId(Integer.parseInt(eventId));
		seatCategory.setCategoryName(categoryName);
		seatCategory.setSeatPrice(Integer.parseInt(seatPrice));
		seatCategory.setNumSeats(Integer.parseInt(numSeats));
		seatCategoriesDao.addSeatCategories(seatCategory);
	}
		
	// 刪除
	public void deleteEvent(String eventId) {
		seatCategoriesDao.deleteSeatCategories(Integer.parseInt(eventId));
	}
		
	// 取得
	public SeatCategoriesDto getSeatCategories(String eventId) {
		SeatCategories seatCategory = seatCategoriesDao.getSeatCategories(Integer.parseInt(eventId));
		if (seatCategory == null) {
			return null;
		}
		SeatCategoriesDto seatCategoriesDto = new SeatCategoriesDto();
		seatCategoriesDto.setSeatCategoryId(seatCategory.getSeatCategoryId());
		seatCategoriesDto.setEventId(seatCategory.getEventId());
		seatCategoriesDto.setCategoryName(seatCategory.getCategoryName());
		seatCategoriesDto.setSeatPrice(seatCategory.getSeatPrice());
		seatCategoriesDto.setNumSeats(seatCategory.getNumSeats());
		
		return seatCategoriesDto;
	}
	
	//修改
	public void updateSeatCategory(String seatCategoryId, String categoryName, String seatPrice, String numSeats) {
		if (!categoryName.isEmpty()) {
			seatCategoriesDao.updateCategoryName(Integer.parseInt(seatCategoryId), categoryName);
		}
		if (!seatPrice.isEmpty()) {
			seatCategoriesDao.updateSeatPrice(Integer.parseInt(seatCategoryId), Integer.parseInt(seatPrice));
		}
		if (!numSeats.isEmpty()) {
			seatCategoriesDao.updateNumSeats(Integer.parseInt(seatCategoryId), Integer.parseInt(numSeats));
		}
	}
}
