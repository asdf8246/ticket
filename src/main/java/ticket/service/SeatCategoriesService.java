package ticket.service;

import java.util.ArrayList;
import java.util.List;

import ticket.model.dto.SeatCategoriesDto;
import ticket.model.entity.SeatCategories;
import ticket.repository.SeatCategoriesDao;
import ticket.repository.SeatCategoriesDaoImpl;

public class SeatCategoriesService {
	private SeatCategoriesDao seatCategoriesDao = new SeatCategoriesDaoImpl();
	
	//新增
	public void appendSeatCategory(Integer eventId, String[] categoryNames, String[] seatPrices, String[] numSeatss) {
		List<SeatCategories> seatCategories = new ArrayList<>();
		
		for(int i=0;i<categoryNames.length;i++) {
			String categoryName = categoryNames[i];
			Integer seatPrice = Integer.parseInt(seatPrices[i]);
			Integer numSeats = Integer.parseInt(numSeatss[i]);
			
			SeatCategories seatCategory = new SeatCategories();
			seatCategory.setEventId(eventId);
			seatCategory.setCategoryName(categoryName);
			seatCategory.setSeatPrice(seatPrice);
			seatCategory.setNumSeats(numSeats);
			
			seatCategories.add(seatCategory);
		}
		
		seatCategoriesDao.addSeatCategories(seatCategories);
	}
		
	// 刪除
	public void deleteSeatCategorie(String seatCategoryId) {
		seatCategoriesDao.deleteSeatCategories(Integer.parseInt(seatCategoryId));
	}
		
	// 取得該活動所有
	public List<SeatCategoriesDto> getSeatCategories(String eventId) {
		List<SeatCategoriesDto> seatCategoriesDtos = new ArrayList<>();
		List<SeatCategories> seatCategories = seatCategoriesDao.getSeatCategories(Integer.parseInt(eventId));
		
		for(SeatCategories seatCategory : seatCategories) {
			SeatCategoriesDto seatCategoriesDto = new SeatCategoriesDto();
			seatCategoriesDto.setSeatCategoryId(seatCategory.getSeatCategoryId());
			seatCategoriesDto.setEventId(seatCategory.getEventId());
			seatCategoriesDto.setCategoryName(seatCategory.getCategoryName());
			seatCategoriesDto.setSeatPrice(seatCategory.getSeatPrice());
			seatCategoriesDto.setNumSeats(seatCategory.getNumSeats());
			
			seatCategoriesDtos.add(seatCategoriesDto);
		}
		return seatCategoriesDtos;
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
