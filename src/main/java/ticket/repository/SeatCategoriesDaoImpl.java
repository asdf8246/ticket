package ticket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ticket.model.entity.SeatCategories;

public class SeatCategoriesDaoImpl extends BaseDao implements SeatCategoriesDao{

	@Override
	public SeatCategories getSeatCategories(Integer eventId) {
		String sql = "select seat_category_id, event_id, category_name, seat_price, num_seats from seat_categories where event_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, eventId);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					SeatCategories seatCategory = new SeatCategories();
					seatCategory.setSeatCategoryId(rs.getInt("seat_category_id"));
					seatCategory.setEventId(rs.getInt("event_id"));
					seatCategory.setCategoryName(rs.getString("category_name"));
					seatCategory.setSeatPrice(rs.getInt("seat_price"));
					seatCategory.setNumSeats(rs.getInt("num_seats"));
					return seatCategory;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addSeatCategories(SeatCategories seatCategories) {
		String sql = "insert into seat_categories(event_id, category_name, seat_price, num_seats) value(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, seatCategories.getEventId());
			pstmt.setString(2, seatCategories.getCategoryName());
			pstmt.setInt(3, seatCategories.getSeatPrice());
			pstmt.setInt(4, seatCategories.getNumSeats());
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("新增失敗");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCategoryName(Integer seatCategoryId, String categoryName) {
		String sql = "update seat_categories set category_name=? where seat_category_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, categoryName);
			pstmt.setInt(2, seatCategoryId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + seatCategoryId + "name:" + categoryName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateSeatPrice(Integer seatCategoryId, Integer seatPrice) {
		String sql = "update seat_categories set seat_price=? where seat_category_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, seatPrice);
			pstmt.setInt(2, seatCategoryId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + seatCategoryId + "name:" + seatPrice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateNumSeats(Integer seatCategoryId, Integer numSeats) {
		String sql = "update seat_categories set num_seats=? where seat_category_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, numSeats);
			pstmt.setInt(2, seatCategoryId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + seatCategoryId + "name:" + numSeats);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSeatCategories(Integer eventId) {
		String sql = "delete from seat_categories where event_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, eventId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("刪除失敗 Id:" + eventId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
