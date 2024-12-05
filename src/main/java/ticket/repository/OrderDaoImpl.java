package ticket.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ticket.model.entity.Order;

public class OrderDaoImpl extends BaseDao implements OrderDao {

	@Override
	public List<Order> findAllOrders(Integer userId) {
		List<Order> orders = new ArrayList<Order>();
		String sql = "select * from orders where user_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			try(ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setUserId(rs.getInt("user_id"));
					order.setEventName(rs.getString("event_name"));
					order.setOrderPrice(rs.getInt("order_price"));
					order.setOrderDate(rs.getString("order_date"));
					order.setOrderStatus(rs.getString("ticket_status"));
					
					orders.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	@Override
	public Order getOrder(Integer orderId) {
		String sql = "select * from orders where order_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setUserId(rs.getInt("user_id"));
					order.setEventName(rs.getString("event_name"));
					order.setOrderPrice(rs.getInt("order_price"));
					order.setOrderDate(rs.getString("order_date"));
					return order;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrderSeats(Integer orderId) {
		List<Order> orderSeats = new ArrayList<Order>();
		String sql = "select * from orders_seats where order_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			try(ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setSeatId(rs.getInt("seat_id"));
					order.setCategoryName(rs.getString("category_name"));
					order.setSeatNumber(rs.getInt("seat_number"));
					
					orderSeats.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderSeats;
	}

	
	@Override
	public Integer addOrder(Order order) {
		String sql = "insert into orders(user_id, event_name, order_price, order_date) value(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, order.getUserId());
			pstmt.setString(2, order.getEventName());
			pstmt.setInt(3, order.getOrderPrice());
			pstmt.setString(4, order.getOrderDate());
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("新增失敗");
			}
			
			try(ResultSet rs = pstmt.getGeneratedKeys()){
				if (rs.next()) {
					Integer orderId = rs.getInt(1);
					return orderId;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addOrderSeats(List<Order> orders) {
		String sql = "insert into orders_seats(order_id, seat_id, category_name, seat_number) value(?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.clearBatch();
			for (Order order: orders) {
				pstmt.setInt(1, order.getOrderId());
				pstmt.setInt(2, order.getSeatId());
				pstmt.setString(3, order.getCategoryName());
				pstmt.setInt(4, order.getSeatNumber());
				
				pstmt.addBatch();
			}		
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateOrderStatus(Integer orderId, String orderStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(Integer orderId) {
		String sql = "delete from orders where order_id = ?";
try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, orderId);
			
			int rowcount = pstmt.executeUpdate();
			if (rowcount != 1) {
				throw new RuntimeException("刪除失敗 Id:" + orderId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
}
