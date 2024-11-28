package ticket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ticket.model.entity.Seats;

public class SeatsDaoImpl extends BaseDao implements SeatsDao {

	@Override
	public List<Seats> buySeat(Integer eventId, Integer seatCategoryId, Integer numSeats) {
		List<Seats> seats = new ArrayList<Seats>();
		String sql = """
				select seat_id, event_id, seat_category_id, seat_number, seat_status 
				from seats where event_id = ? and seat_category_id = ? and seat_status = 'available'
				limit ?
				""".trim();

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, eventId);
			pstmt.setInt(2, seatCategoryId);
			pstmt.setInt(3, numSeats);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				
				while(rs.next()) {
					Seats seat = new Seats();
					seat.setSeatId(rs.getInt("seat_id"));
					seat.setEventId(rs.getInt("event_id"));
					seat.setSeatCategoryId(rs.getInt("seat_category_id"));
					seat.setSeatNumber(rs.getInt("seat_number"));
					seat.setSeatStatus(rs.getString("seat_status"));
					
					seats.add(seat);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seats;
	}

	@Override
	public void UpdateSeatsStatus(List<Integer> seatIds, String seatStatus) {
		String sql = "update seats set seat_status = ? where seat_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.clearBatch();
			
			for (Integer seatId : seatIds) {
				pstmt.setString(1, seatStatus);
				pstmt.setInt(2, seatId);
				
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
