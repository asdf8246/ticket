package ticket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ticket.model.entity.Seats;

public class SeatsDaoImpl extends BaseDao implements SeatsDao {

	@Override
	public List<Seats> buySeat(List<Seats> seats) {
		List<Seats> orderSeats = new ArrayList<Seats>();
		
		String updateSQL = """
							update seats set seat_status = 'reserved' 
							where event_id = ? and seat_category_id = ? and seat_status = 'available' 
							order by seat_number limit ?
							""".trim();
		
		String selectSQL = """
							select seats.seat_id, event_id, seat_category_id, seats.seat_number, seats.seat_status, seat_categories.category_name
							from seats, seat_categories where event_id = ? and seat_category_id = ? and seat_status = 'reserved'
							order by seat_number limit ?
							""".trim();

		try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
			 PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
			
			// 開始事務
		    conn.setAutoCommit(false);
		    
		    // 2. 更新座位狀態為 'reserved'
		    updateStmt.clearBatch();
		    for (Seats seat : seats) {
		    	updateStmt.setInt(1, seat.getEventId());
		    	updateStmt.setInt(2, seat.getSeatCategoryId());
		    	updateStmt.setInt(3, seat.getNumSeats());
		    	
		    	updateStmt.addBatch();
			}
		    updateStmt.executeBatch();
		    
		    // 3. 查詢更新後的資料
		    for (Seats seat : seats) {
			    selectStmt.setInt(1, seat.getEventId());
			    selectStmt.setInt(2, seat.getSeatCategoryId());
			    selectStmt.setInt(3, seat.getNumSeats());
				
				try (ResultSet rs = selectStmt.executeQuery()) {
					
					while(rs.next()) {
						Seats seat2 = new Seats();
						seat.setSeatId(rs.getInt("seat_id"));
						seat.setEventId(rs.getInt("event_id"));
						seat.setSeatCategoryId(rs.getInt("seat_category_id"));
						seat.setSeatNumber(rs.getInt("seat_number"));
						seat.setSeatStatus(rs.getString("seat_status"));
						
						orderSeats.add(seat2);
					}
				}
		    }
		    // 提交事務
		    conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();  // 發生錯誤時回滾
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return orderSeats;
	}

	@Override
	public void updateSeatsStatus(List<Integer> seatIds, String seatStatus) {
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
