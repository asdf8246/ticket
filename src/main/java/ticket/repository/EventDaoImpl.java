package ticket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ticket.model.entity.Events;

public class EventDaoImpl extends BaseDao implements EventDao {

	@Override
	public List<Events> findAllEvents() {
		List<Events> events = new ArrayList<Events>();
		String sql = "select event_id, event_name, event_date, venue, description from events";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//逐筆尋訪
			while (rs.next()) {
				//建立 event 物件並將資料配置進去
				Events event = new Events();
				event.setEventId(rs.getInt("event_id"));
				event.setEventName(rs.getString("event_name"));
				event.setEventDate(rs.getString("event_date"));
				event.setVenue(rs.getString("venue"));
				event.setDescription(rs.getString("description"));
				// 將 event 物件放到 events 集合中保存
				events.add(event);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events; // 回傳有 event 物件的集合
	}

	@Override
	public Events getEvent(Integer eventId) {
		String sql = "select event_id, event_name, event_date, venue, description from events where event_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, eventId);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					Events event = new Events();
					event.setEventId(rs.getInt("event_id"));
					event.setEventName(rs.getString("event_name"));
					event.setEventDate(rs.getString("event_date"));
					event.setVenue(rs.getString("venue"));
					event.setDescription(rs.getString("description"));
					return event;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addEvent(Events event) {
		String sql = "insert into events(event_name, event_date, venue, description) value(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, event.getEventName());
			pstmt.setString(2, event.getEventDate());
			pstmt.setString(3, event.getVenue());
			pstmt.setString(4, event.getDescription());
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("新增失敗");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateEventName(Integer eventId, String eventName) {
		String sql = "update events set event_name = ? where event_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, eventName);
			pstmt.setInt(2, eventId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + eventId + "name:" + eventName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateEventDate(Integer eventId, String eventDate) {
		String sql = "update events set event_date = ? where event_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, eventDate);
			pstmt.setInt(2, eventId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + eventId + "name:" + eventDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateVenue(Integer eventId, String venue) {
		String sql = "update events set venue = ? where event_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, venue);
			pstmt.setInt(2, eventId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + eventId + "name:" + venue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateDescription(Integer eventId, String description) {
		String sql = "update events set description = ? where event_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, description);
			pstmt.setInt(2, eventId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("修改失敗 Id:" + eventId + "name:" + description);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEvent(Integer eventId) {
		String sql = "delete from events where event_id = ?";
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
